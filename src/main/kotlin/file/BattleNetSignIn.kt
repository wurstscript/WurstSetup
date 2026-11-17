package file

import global.Log
import java.io.IOException
import java.net.BindException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.nio.ByteBuffer
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.security.MessageDigest
import java.util.Locale
import java.util.UUID
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Renews the sign-in that Warcraft III 3.x uses for `-editor` launches, from the Battle.net desktop app.
 *
 * A client started with `-editor` (every `grill run` of a Reforged client) signs in with `EDITOR_TOKEN`
 * (`HKCU\Software\Blizzard Entertainment\Warcraft III`) and replaces it with a fresh one each time it signs in. Once
 * that token lapses (overnight is enough) every launch stops at the in-game login panel. The app's own session does
 * not reach it: a client the app starts with `-loadfile` shows the login panel too.
 *
 * Just before the app starts the game it writes a one-time `WEB_TOKEN` (`...\Battle.net\Launch Options\W3`). Stored
 * as `EDITOR_TOKEN`, that token signs the next `-editor` client in. [renew] runs [TOKEN_SCRIPT], which asks the app
 * to start the game, closes that client before it spends the token, and stores the token; the map is then launched as
 * before.
 *
 * The app, the script and the map's client hand the sign-in to each other through `HKCU`, so all three must see the
 * same registry. A process started from a packaged (MSIX) app, such as the Claude desktop app or an editor started from
 * inside one, reads and writes `HKCU` through the package's private copy, and so does everything it starts. The script
 * and the client therefore start through WMI, in the user's own registry view ([OutsideStart]), whatever view grill
 * has. A Battle.net that was itself started from inside such an app writes its token where nothing else reads it; the
 * script then sees the app start the game but no new token, and the user is told to start Battle.net from the Start
 * menu.
 *
 * WurstScript's Run Map (`BattleNetSignIn`) does the same, and every tool that renews this sign-in holds the same
 * localhost port as a lock from the renewal until the launched client has signed in.
 */
/** What [Wc3MapRunner] needs from the Battle.net app; replaced in tests. */
internal interface BattleNetApp {
    fun isRunning(): Boolean

    /** @param onWait called once if another launch holds the lock and this one has to wait for it */
    fun renew(onWait: () -> Unit = {}): BattleNetSignIn.Renewal
}

internal class BattleNetSignIn private constructor(private val config: Path, private val launcher: Path) : BattleNetApp {

    /**
     * Another tool holds the renewal lock for longer than a renewal and a sign-in take. Not a reason to launch
     * without it: that launch could spend the token the other tool's client has not read yet.
     */
    class LockBusyException(message: String) : IOException(message)

    /** The lock, held until the launched client has signed in; see [awaitSignIn]. */
    class Renewal internal constructor(
        private val gate: ServerSocket,
        /** Whether the launched client has replaced the renewed `EDITOR_TOKEN` yet; throws when that cannot be told. */
        private val signedIn: () -> Boolean,
        private val pollMillis: Long = 250,
        private val onClose: () -> Unit = {},
    ) : AutoCloseable {
        /**
         * Blocks until the launched client replaces `EDITOR_TOKEN`, which it does as it signs in (about ten seconds after
         * launch), or [SIGN_IN_TIMEOUT_SECONDS] pass, then releases the lock. Released earlier, another tool's renewal
         * could replace the token before this client read it, leaving two clients one token.
         */
        fun awaitSignIn(): Boolean {
            val deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(SIGN_IN_TIMEOUT_SECONDS)
            try {
                while (System.nanoTime() < deadline) {
                    if (signedIn()) return true
                    Thread.sleep(pollMillis)
                }
                return false
            } finally {
                close()
            }
        }

        override fun close() {
            try {
                gate.close()
            } catch (_: IOException) {
            }
            onClose()
        }
    }

    /**
     * [SIGNIN_SCRIPT] running in the user's own registry view, where the client replaces `EDITOR_TOKEN` as it signs in.
     * Read from grill's own view, the token may never change: a packaged app's private copy keeps the old one.
     */
    internal class SignInWatcher(private val process: ProcessHandle?, private val output: Path) {
        /** True once the client has signed in; throws once the watcher has ended without seeing it. */
        fun signedIn(): Boolean {
            val alive = process?.isAlive == true
            // Read after the check, so a watcher that saw the sign-in and ended in between is not missed.
            val lines = parseTokenScriptOutput(completeLines(readOutput(output))).lines
            if ("signed-in" in lines) return true
            if ("timeout" in lines || !alive) throw IOException("the sign-in watcher ended (${lines.lastOrNull() ?: "no output"})")
            return false
        }

        fun ready(): Boolean = "ready" in parseTokenScriptOutput(completeLines(readOutput(output))).lines

        fun stop() {
            process?.destroyForcibly()
            try {
                process?.onExit()?.get(OUTPUT_RELEASE_SECONDS, TimeUnit.SECONDS)
            } catch (_: Exception) {
            }
            try {
                Files.deleteIfExists(output)
            } catch (_: IOException) {
                output.toFile().deleteOnExit()
            }
        }
    }

    /**
     * How [Renewal] tells that the launched client has signed in: from [SignInWatcher] started outside grill's registry
     * view, or, if that cannot start, by reading `EDITOR_TOKEN` from here. Called after the renewal and before the
     * launch, so the token seen as the starting point is the renewed one.
     */
    private fun signInCheck(gate: ServerSocket): Renewal {
        try {
            val output = tempDir().resolve("grill-battlenet-signin-${UUID.randomUUID()}.log")
            val command = listOf("powershell.exe", "-NoProfile", "-NonInteractive", "-ExecutionPolicy", "Bypass", "-Command",
                "& ${powerShellQuote(scriptCopy(SIGNIN_SCRIPT, tempDir()).toString())}" +
                    " -TimeoutSeconds ${SIGN_IN_TIMEOUT_SECONDS + 10} *> ${powerShellQuote(output.toString())}")
            val pid = OutsideStart.startOrHere(command, null, OutsideStart.Show.HIDDEN)
            val watcher = SignInWatcher(ProcessHandle.of(pid).orElse(null), output)
            val deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(SIGNIN_READY_SECONDS)
            while (!watcher.ready() && System.nanoTime() < deadline) Thread.sleep(50)
            if (watcher.ready()) return Renewal(gate, watcher::signedIn, onClose = watcher::stop)
            watcher.stop()
        } catch (e: IOException) {
            Log.println("Watching the Warcraft III sign-in from grill instead: ${e.message}")
        }
        val token = try {
            readBinary(GAME_KEY, "EDITOR_TOKEN")
        } catch (_: IOException) {
            null
        }
        return Renewal(gate, { readBinary(GAME_KEY, "EDITOR_TOKEN") != token })
    }

    override fun isRunning(): Boolean = ProcessHandle.allProcesses().anyMatch { named(command(it), LAUNCHER) }

    override fun renew(onWait: () -> Unit): Renewal = renew(GATE_WAIT_SECONDS, onWait)

    /**
     * Stores a token from the app as `EDITOR_TOKEN` and returns the held lock. Throws, with a message for the user,
     * when the app cannot provide one: it is closed, signed out, updating, or running a client of its own.
     */
    fun renew(gateWaitSeconds: Long, onWait: () -> Unit = {}): Renewal {
        val gate = acquireGate(gateWaitSeconds, onWait)
        try {
            renewLocked()
            return signInCheck(gate)
        } catch (e: Throwable) {
            gate.close()
            throw e
        }
    }

    private fun renewLocked() {
        if (!isRunning()) throw IOException("Battle.net is not running.")
        if (games().any { startedByLauncher(it) }) throw IOException(APP_GAME_RUNNING)
        val script = scriptCopy(TOKEN_SCRIPT, tempDir())
        // Asked to start the game, the app shows its window in front; the watcher minimizes it as soon as it appears
        // and brings the map's client forward once that has a window.
        watchLauncherWindow()
        val result = runTokenScript(script)
        problem(result)?.let { throw IOException(it) }
    }

    /**
     * Runs [TOKEN_SCRIPT] hidden in the user's own registry view, where the app writes `WEB_TOKEN` and the client reads
     * `EDITOR_TOKEN`, whatever view grill has. Only if WMI cannot start it does it run from here.
     */
    private fun runTokenScript(script: Path): TokenScriptResult {
        val output = tempDir().resolve("grill-battlenet-renewal-${UUID.randomUUID()}.log")
        try {
            val pid = OutsideStart.startOrHere(tokenScriptCommand(script, launcher, TIMEOUT_SECONDS, output), null,
                OutsideStart.Show.HIDDEN)
            val handle = ProcessHandle.of(pid).orElse(null)
            val result = awaitTokenScript(handle, output, TIMEOUT_SECONDS + SCRIPT_EXIT_GRACE_SECONDS)
            // The script ends right after its result line; let it close the output file before that is removed.
            try {
                handle?.onExit()?.get(OUTPUT_RELEASE_SECONDS, TimeUnit.SECONDS)
            } catch (_: ExecutionException) {
            } catch (_: TimeoutException) {
                // still running: the delete below falls back to deleteOnExit
            }
            return result
        } finally {
            try {
                Files.deleteIfExists(output)
            } catch (_: IOException) {
                output.toFile().deleteOnExit()
            }
        }
    }

    /** What [TOKEN_SCRIPT] printed: its outcome, null until its `result` line, and every line. */
    data class TokenScriptResult(val outcome: String?, val lines: List<String>)

    private fun acquireGate(waitSeconds: Long, onWait: () -> Unit): ServerSocket {
        val deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(waitSeconds)
        var waiting = false
        while (true) {
            val socket = ServerSocket()
            try {
                // 127.0.0.1 exactly, as every holder binds it; the loopback name could resolve to ::1.
                socket.bind(InetSocketAddress(InetAddress.getByAddress(byteArrayOf(127, 0, 0, 1)), gatePort(config)))
                return socket
            } catch (e: BindException) {
                socket.close()
                if (!waiting) {
                    waiting = true
                    onWait()
                }
                if (System.nanoTime() >= deadline) {
                    throw LockBusyException("Another Warcraft III launch has held the Battle.net sign-in for over $waitSeconds s.")
                }
                Thread.sleep(250)
            } catch (e: IOException) {
                socket.close()
                throw e
            }
        }
    }

    companion object {
        const val GAME_KEY = "HKCU\\Software\\Blizzard Entertainment\\Warcraft III"
        private const val LAUNCHER = "Battle.net.exe"
        private const val GAME = "Warcraft III.exe"
        private const val TIMEOUT_SECONDS = 90L
        /** How long past its own timeout the renewal script may take to start and to write its result. */
        private const val SCRIPT_EXIT_GRACE_SECONDS = 30L
        /** How long the renewal script may take to exit, and close its output file, after writing its result. */
        private const val OUTPUT_RELEASE_SECONDS = 5L
        const val SIGN_IN_TIMEOUT_SECONDS = 120L
        const val APP_GAME_RUNNING = "Close the Warcraft III client started from Battle.net, then run the map again."
        /** Another holder renews (up to [TIMEOUT_SECONDS]) and waits for its client's sign-in; outwait both. */
        const val GATE_WAIT_SECONDS = TIMEOUT_SECONDS + SIGN_IN_TIMEOUT_SECONDS + 30

        /** A renewal for [config] whose launcher is never started; for tests that only touch the lock. */
        internal fun forTest(config: Path): BattleNetSignIn = BattleNetSignIn(config, Paths.get(LAUNCHER))
        private val REG_BINARY = Regex("""^\s*(\S+)\s+REG_BINARY\s+([0-9A-Fa-f]*)\s*$""", RegexOption.MULTILINE)

        /** The renewal script, run in the user's own registry view. */
        const val TOKEN_SCRIPT = "battlenet-token.ps1"
        /** The sign-in watcher, run in the user's own registry view ([SignInWatcher]). */
        const val SIGNIN_SCRIPT = "battlenet-signin.ps1"
        /** How long the sign-in watcher may take to start and read the renewed token before the game is launched. */
        private const val SIGNIN_READY_SECONDS = 15L
        /** The watcher that keeps the app's window out of sight during the renewal. */
        const val WINDOW_SCRIPT = "battlenet-window.ps1"
        /** The watcher's limit without a client: every request the renewal may send, then a launch and a slow sign-in. */
        const val WINDOW_WATCH_SECONDS = TIMEOUT_SECONDS + 150
        private const val WINDOW_READY_SECONDS = 3L
        private val RESULT = Regex("""result (\S+)""")

        private fun tempDir(): Path = Paths.get(System.getProperty("java.io.tmpdir"))

        /**
         * A copy of the resource [script] in [dir], named by its content, so a grill of another version never runs this
         * one's copy. A file, not -EncodedCommand: encoded, the window watcher is close to Windows' 32,767-character
         * command line limit.
         */
        fun scriptCopy(script: String, dir: Path): Path {
            val content = BattleNetSignIn::class.java.classLoader.getResourceAsStream(script)?.use { it.readBytes() }
                ?: throw IOException("$script is missing from grill")
            val hash = MessageDigest.getInstance("SHA-256").digest(content).take(6).joinToString("") { "%02x".format(it) }
            val file = dir.resolve("grill-${script.removeSuffix(".ps1")}-$hash.ps1")
            if (!Files.isRegularFile(file) || !Files.readAllBytes(file).contentEquals(content)) Files.write(file, content)
            return file
        }

        /** The PowerShell command that runs [WINDOW_SCRIPT] with [arguments]; null if the script cannot be provided. */
        fun windowWatcherCommand(arguments: List<String>, dir: Path = tempDir()): List<String>? {
            val file = try {
                scriptCopy(WINDOW_SCRIPT, dir)
            } catch (_: IOException) {
                return null
            }
            return listOf("powershell.exe", "-NoProfile", "-NonInteractive", "-ExecutionPolicy", "Bypass", "-File", file.toString()) + arguments
        }

        /** A PowerShell single-quoted string literal. PowerShell also reads the typographic single quotes as quotes. */
        fun powerShellQuote(text: String): String = "'" + text.replace(Regex("['‘’‚‛]")) { it.value + it.value } + "'"

        /**
         * The command that runs [script] and redirects everything it prints to [output]. Started outside, the script's
         * output cannot come back through a pipe, so it goes to a file.
         */
        fun tokenScriptCommand(script: Path, launcher: Path, timeoutSeconds: Long, output: Path): List<String> =
            listOf("powershell.exe", "-NoProfile", "-NonInteractive", "-ExecutionPolicy", "Bypass", "-Command",
                "& ${powerShellQuote(script.toString())} -BattleNetExe ${powerShellQuote(launcher.toString())}" +
                    " -TimeoutSeconds $timeoutSeconds *> ${powerShellQuote(output.toString())}")

        fun parseTokenScriptOutput(output: String): TokenScriptResult {
            val lines = output.lines().map { it.trim() }.filter { it.isNotEmpty() }
            return TokenScriptResult(lines.firstNotNullOfOrNull { RESULT.matchEntire(it)?.groupValues?.get(1) }, lines)
        }

        /**
         * The complete lines of a script's redirected output: Windows PowerShell writes UTF-16 with a byte order mark,
         * and a line still being written could read as a different result.
         */
        fun completeLines(output: ByteArray): String {
            val utf16 = output.size >= 2 && output[0] == 0xFF.toByte() && output[1] == 0xFE.toByte()
            val text = if (utf16) String(output, 2, output.size - 2, Charsets.UTF_16LE) else String(output, Charsets.UTF_8)
            return text.substring(0, text.lastIndexOf('\n') + 1)
        }

        /** The message for the user when the renewal did not succeed; null when it did. */
        fun problem(result: TokenScriptResult): String? = when (result.outcome) {
            "refreshed" -> null
            "no-token" -> "Battle.net did not start Warcraft III. Check that it is signed in and the game is ready to play."
            "app-game-running" -> APP_GAME_RUNNING
            "token-not-visible" -> "Battle.net started Warcraft III, but wrote its sign-in where other programs cannot" +
                " read it, probably because Battle.net was started from a packaged app (such as the Claude desktop app)." +
                " Quit Battle.net from its tray icon and start it from the Start menu."
            else -> "The Battle.net sign-in renewal failed: " +
                (if (result.lines.isEmpty()) "it printed nothing" else result.lines.takeLast(3).joinToString(" | ")) + "."
        }

        /**
         * Waits for the script's result line, or for the script to end, and ends the script if it outlives
         * [timeoutSeconds] or this thread is interrupted.
         *
         * @param script null when it has ended already
         */
        fun awaitTokenScript(script: ProcessHandle?, output: Path, timeoutSeconds: Long, pollMillis: Long = 250): TokenScriptResult {
            val deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(timeoutSeconds)
            var done = false
            try {
                while (true) {
                    // Read after the check, so a script that wrote its result and ended in between is not missed.
                    val running = script != null && script.isAlive
                    val result = parseTokenScriptOutput(completeLines(readOutput(output)))
                    if (result.outcome != null || !running) {
                        done = true
                        return result
                    }
                    if (System.nanoTime() >= deadline) return TokenScriptResult(null, result.lines + "the renewal script did not finish")
                    Thread.sleep(pollMillis)
                }
            } finally {
                if (!done) script?.destroyForcibly()
            }
        }

        private fun readOutput(output: Path): ByteArray = try {
            Files.readAllBytes(output)
        } catch (_: IOException) {
            ByteArray(0) // not created yet
        }

        /**
         * Starts the window watcher and waits until it has noted the windows already open (at most
         * [WINDOW_READY_SECONDS]). It then runs on its own: the app shows its window again as the map's client signs in,
         * so it ends a minute after that client appears, or after [WINDOW_WATCH_SECONDS]. Best effort: a watcher that does
         * not start only means the app's window may show.
         */
        private fun watchLauncherWindow() {
            val command = windowWatcherCommand(listOf("-Seconds", WINDOW_WATCH_SECONDS.toString(), "-FocusGame")) ?: return
            val process = try {
                ProcessBuilder(command).redirectError(ProcessBuilder.Redirect.DISCARD).start()
            } catch (_: IOException) {
                return
            }
            val ready = CountDownLatch(1)
            Thread {
                try {
                    // The watcher prints nothing after "ready", so its output can be let go then.
                    process.inputReader().use { reader ->
                        while (true) {
                            val line = reader.readLine() ?: break
                            if (line.trim() == "ready") break
                        }
                    }
                } catch (_: IOException) {
                } finally {
                    ready.countDown()
                }
            }.apply { isDaemon = true }.start()
            ready.await(WINDOW_READY_SECONDS, TimeUnit.SECONDS)
        }

        fun find(): BattleNetSignIn? {
            if (!System.getProperty("os.name").startsWith("Windows", ignoreCase = true)) return null
            val appData = System.getenv("APPDATA") ?: return null
            val launcher = launcherExecutable() ?: return null
            return BattleNetSignIn(Paths.get(appData, "Battle.net", "Battle.net.config"), launcher)
        }

        private fun launcherExecutable(): Path? {
            ProcessHandle.allProcesses().map { command(it) }.filter { named(it, LAUNCHER) }.findFirst().orElse(null)
                ?.let { return it }
            return listOfNotNull(System.getenv("ProgramFiles(x86)"), System.getenv("ProgramFiles"))
                .map { Paths.get(it, "Battle.net", LAUNCHER) }
                .firstOrNull(Files::isRegularFile)
        }

        /** Whether [executable] belongs to the install Battle.net manages; the token only works there. */
        fun isRegisteredGame(executable: Path): Boolean {
            val key = "HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\Warcraft III"
            for (view in listOf("/reg:32", "/reg:64")) {
                for (line in readCommand("reg.exe", "query", key, "/v", "InstallLocation", view)) {
                    val fields = line.trim().split(Regex("\\s{2,}"), 3)
                    if (fields.size == 3 && fields[0].equals("InstallLocation", ignoreCase = true)
                        && executable.toAbsolutePath().normalize().startsWith(Paths.get(fields[2]).toAbsolutePath().normalize())) {
                        return true
                    }
                }
            }
            return false
        }

        fun parseBinary(regQueryOutput: List<String>, value: String): String? =
            REG_BINARY.findAll(regQueryOutput.joinToString("\n"))
                .firstOrNull { it.groupValues[1].equals(value, ignoreCase = true) }
                ?.groupValues?.get(2)?.uppercase(Locale.ROOT)

        /** The lock port every tool that renews this sign-in, WurstScript's Run Map among them, uses for a Battle.net profile. */
        fun gatePort(config: Path): Int {
            val key = config.toAbsolutePath().normalize().toString().lowercase(Locale.ROOT)
            val hash = MessageDigest.getInstance("SHA-256").digest(key.toByteArray(Charsets.UTF_8))
            return 33000 + Integer.remainderUnsigned(ByteBuffer.wrap(hash).int, 10000)
        }

        private fun games(): List<ProcessHandle> =
            ProcessHandle.allProcesses().filter { named(command(it), GAME) }.toList()

        /** A client the app started; anything else is someone else's game and is left alone. */
        private fun startedByLauncher(game: ProcessHandle): Boolean =
            game.parent().map { named(command(it), LAUNCHER) }.orElse(false)

        private fun command(process: ProcessHandle): Path? = process.info().command().map { Paths.get(it) }.orElse(null)

        private fun named(executable: Path?, image: String): Boolean =
            executable?.fileName?.toString()?.equals(image, ignoreCase = true) == true

        private fun readBinary(key: String, value: String): String? =
            parseBinary(readCommand("reg.exe", "query", key, "/v", value), value)

        private fun readCommand(vararg command: String): List<String> {
            val process = ProcessBuilder(*command).redirectError(ProcessBuilder.Redirect.DISCARD).start()
            if (!process.waitFor(8, TimeUnit.SECONDS)) {
                process.destroyForcibly()
                throw IOException("Windows registry query timed out.")
            }
            return process.inputReader().use { it.readLines() }
        }
    }
}
