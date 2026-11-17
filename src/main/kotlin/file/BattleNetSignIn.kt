package file

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
import java.util.concurrent.TimeUnit

/**
 * Renews the sign-in that Warcraft III 3.x uses for `-editor` launches, from the Battle.net desktop app.
 *
 * A client started with `-editor` (every `grill run` of a Reforged client) signs in with `EDITOR_TOKEN`
 * (`HKCU\Software\Blizzard Entertainment\Warcraft III`) and replaces it with a fresh one each time it signs in. Once
 * that token lapses (overnight is enough) every launch stops at the in-game login panel. The app's own session does
 * not reach it: a client the app starts with `-loadfile` shows the login panel too.
 *
 * Just before the app starts the game it writes a one-time `WEB_TOKEN` (`...\Battle.net\Launch Options\W3`). Stored
 * as `EDITOR_TOKEN`, that token signs the next `-editor` client in. [renew] asks the app to start the game, closes
 * that client before it spends the token, and stores the token; the map is then launched as before.
 *
 * WurstScript's Run Map (`BattleNetSignIn`) and the wc3-e2e-harness (`docs/BattleNetSignIn.md`) do the same, and all
 * hold the same localhost port as a lock from the renewal until the launched client has signed in.
 */
internal class BattleNetSignIn private constructor(private val config: Path, private val launcher: Path) {

    /** The lock, held until the launched client has signed in; see [awaitSignIn]. */
    class Renewal internal constructor(private val gate: ServerSocket, private val token: String) : AutoCloseable {
        /**
         * Blocks until the launched client replaces `EDITOR_TOKEN`, which it does as it signs in (about ten seconds after
         * launch), or [SIGN_IN_TIMEOUT_SECONDS] pass, then releases the lock. Released earlier, another tool's renewal
         * could replace the token before this client read it, leaving two clients one token.
         */
        fun awaitSignIn(): Boolean {
            val deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(SIGN_IN_TIMEOUT_SECONDS)
            try {
                while (System.nanoTime() < deadline) {
                    if (readBinary(GAME_KEY, "EDITOR_TOKEN") != token) return true
                    Thread.sleep(500)
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
        }
    }

    fun isRunning(): Boolean = ProcessHandle.allProcesses().anyMatch { named(command(it), LAUNCHER) }

    /**
     * Stores a token from the app as `EDITOR_TOKEN` and returns the held lock. Throws, with a message for the user,
     * when the app cannot provide one: it is closed, signed out, updating, or running a client of its own.
     */
    fun renew(): Renewal {
        val gate = acquireGate()
        try {
            return Renewal(gate, renewLocked())
        } catch (e: Throwable) {
            gate.close()
            throw e
        }
    }

    private fun renewLocked(): String {
        if (!isRunning()) throw IOException("Battle.net is not running.")
        val seen = HashSet<Long>()
        for (game in games()) {
            if (startedByLauncher(game)) {
                throw IOException("Close the Warcraft III client started from Battle.net, then run the map again.")
            }
            seen.add(game.pid())
        }
        val before = readBinary(LAUNCH_KEY, "WEB_TOKEN")
        val start = System.nanoTime()
        var askedAt = Long.MIN_VALUE
        var token: String? = null
        var tokenAt = 0L
        var closed = 0
        while (System.nanoTime() - start < TimeUnit.SECONDS.toNanos(TIMEOUT_SECONDS)) {
            if (token == null && (askedAt == Long.MIN_VALUE
                    || System.nanoTime() - askedAt >= TimeUnit.SECONDS.toNanos(ASK_AGAIN_SECONDS))) {
                askedAt = System.nanoTime()
                // An app that is still starting ignores the command, so it is repeated while waiting.
                ProcessBuilder(launcher.toString(), "--exec=launch W3")
                    .redirectOutput(ProcessBuilder.Redirect.DISCARD)
                    .redirectError(ProcessBuilder.Redirect.DISCARD)
                    .start()
            }
            for (game in games()) {
                if (!seen.add(game.pid()) || !startedByLauncher(game)) continue
                game.destroyForcibly()
                closed++
            }
            if (token == null) {
                val now = readBinary(LAUNCH_KEY, "WEB_TOKEN")
                if (now != null && now != before) {
                    token = now
                    tokenAt = System.nanoTime()
                }
            }
            // The app writes the token, then starts the game: wait briefly for that game so it is closed.
            if (token != null && (closed > 0
                    || System.nanoTime() - tokenAt >= TimeUnit.SECONDS.toNanos(GAME_AFTER_TOKEN_SECONDS))) break
            Thread.sleep(50)
        }
        if (token == null) {
            throw IOException("Battle.net did not start Warcraft III. Check that it is signed in and the game is ready to play.")
        }
        writeBinary(GAME_KEY, "EDITOR_TOKEN", token)
        return token
    }

    private fun acquireGate(): ServerSocket {
        val deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(30)
        while (true) {
            val socket = ServerSocket()
            try {
                // 127.0.0.1 exactly, as the harness binds it; the loopback name could resolve to ::1.
                socket.bind(InetSocketAddress(InetAddress.getByAddress(byteArrayOf(127, 0, 0, 1)), gatePort(config)))
                return socket
            } catch (e: BindException) {
                socket.close()
                if (System.nanoTime() >= deadline) throw IOException("Another Warcraft III test is using Battle.net.", e)
                Thread.sleep(250)
            } catch (e: IOException) {
                socket.close()
                throw e
            }
        }
    }

    companion object {
        const val LAUNCH_KEY = "HKCU\\Software\\Blizzard Entertainment\\Battle.net\\Launch Options\\W3"
        const val GAME_KEY = "HKCU\\Software\\Blizzard Entertainment\\Warcraft III"
        private const val LAUNCHER = "Battle.net.exe"
        private const val GAME = "Warcraft III.exe"
        private const val TIMEOUT_SECONDS = 90L
        private const val ASK_AGAIN_SECONDS = 25L
        private const val GAME_AFTER_TOKEN_SECONDS = 10L
        const val SIGN_IN_TIMEOUT_SECONDS = 120L
        private val REG_BINARY = Regex("""^\s*(\S+)\s+REG_BINARY\s+([0-9A-Fa-f]*)\s*$""", RegexOption.MULTILINE)

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

        /** The lock port the wc3-e2e-harness and WurstScript use for the same Battle.net profile. */
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

        private fun writeBinary(key: String, value: String, hex: String) {
            val process = ProcessBuilder("reg.exe", "add", key, "/v", value, "/t", "REG_BINARY", "/d", hex, "/f")
                .redirectOutput(ProcessBuilder.Redirect.DISCARD).redirectError(ProcessBuilder.Redirect.DISCARD).start()
            if (!process.waitFor(8, TimeUnit.SECONDS) || process.exitValue() != 0) {
                process.destroyForcibly()
                throw IOException("Could not store the Warcraft III sign-in.")
            }
        }

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
