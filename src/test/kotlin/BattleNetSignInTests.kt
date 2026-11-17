import config.WurstProjectConfig
import file.BattleNetApp
import file.BattleNetSignIn
import file.OutsideStart
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.ServerSocket
import file.Wc3ClientDetector
import file.Wc3MapRunner
import org.testng.Assert
import org.testng.SkipException
import org.testng.annotations.Test
import java.nio.file.Files
import java.nio.file.Paths

class BattleNetSignInTests {
    private fun reforgedClient(): Wc3ClientDetector.ClientInfo {
        val root = Files.createTempDirectory("grill-battlenet-client")
        Files.writeString(Files.createDirectories(root.resolve("_retail_/x86_64")).resolve("Warcraft III.exe"), "")
        return Wc3ClientDetector.inspectGameRoot(root)!!
    }

    private val editorLaunch = listOf("Warcraft III.exe", "-launch", "-editor", "-windowmode", "windowed", "-loadfile", "map.w3x")

    @Test
    fun readsOnlyTheRequestedBinaryValueFromRegQuery() {
        val output = listOf(
            "",
            "HKEY_CURRENT_USER\\Software\\Blizzard Entertainment\\Battle.net\\Launch Options\\W3",
            "    WEB_TOKEN    REG_BINARY    01000000d08c9ddf",
            "    LOCALE    REG_SZ    enUS",
            "")
        Assert.assertEquals(BattleNetSignIn.parseBinary(output, "WEB_TOKEN"), "01000000D08C9DDF")
        Assert.assertEquals(BattleNetSignIn.parseBinary(output, "web_token"), "01000000D08C9DDF")
        Assert.assertNull(BattleNetSignIn.parseBinary(output, "LOCALE"))
        Assert.assertNull(BattleNetSignIn.parseBinary(
            listOf("ERROR: The system was unable to find the specified registry key or value."), "WEB_TOKEN"))
    }

    @Test
    fun takesTheSameLockAsWurstScript() {
        if (!System.getProperty("os.name").startsWith("Windows", ignoreCase = true)) throw SkipException("Windows paths")
        // 33000 + sha256(lower-cased path) as uint32 BE % 10000, the port every tool that renews this sign-in binds
        Assert.assertEquals(BattleNetSignIn.gatePort(Paths.get("C:\\Users\\tester\\AppData\\Roaming\\Battle.net\\Battle.net.config")), 36941)
    }

    @Test
    fun theRenewalScriptShipsAndRunsWithItsOutputRedirectedToAFile() {
        val dir = Files.createTempDirectory("grill renewal script")
        val script = BattleNetSignIn.scriptCopy(BattleNetSignIn.TOKEN_SCRIPT, dir)
        Assert.assertEquals(script.parent, dir)
        val content = Files.readString(script)
        Assert.assertTrue(content.contains("\"result token-not-visible\""))
        Assert.assertTrue(content.all { it.code < 128 }, "Windows PowerShell reads a .ps1 without BOM as ANSI")
        Assert.assertEquals(BattleNetSignIn.scriptCopy(BattleNetSignIn.TOKEN_SCRIPT, dir), script, "a second call reuses the file")

        val command = BattleNetSignIn.tokenScriptCommand(Paths.get("C:\\it's here\\s.ps1"),
            Paths.get("C:\\Program Files (x86)\\Battle.net\\Battle.net.exe"), 90, Paths.get("C:\\tmp\\out.log"))
        Assert.assertEquals(command.subList(0, 6),
            listOf("powershell.exe", "-NoProfile", "-NonInteractive", "-ExecutionPolicy", "Bypass", "-Command"))
        Assert.assertEquals(command[6], "& 'C:\\it''s here\\s.ps1' -BattleNetExe 'C:\\Program Files (x86)\\Battle.net\\Battle.net.exe'" +
            " -TimeoutSeconds 90 *> 'C:\\tmp\\out.log'")
        Assert.assertEquals(BattleNetSignIn.powerShellQuote("O\u2019Neil"), "'O\u2019\u2019Neil'")
    }

    private fun utf16(text: String) = byteArrayOf(0xFF.toByte(), 0xFE.toByte()) + text.toByteArray(Charsets.UTF_16LE)

    @Test
    fun onlyCompleteLinesOfTheScriptsOutputCount() {
        Assert.assertEquals(BattleNetSignIn.completeLines(utf16("asked 0.0\r\nresult refreshed\r\n")), "asked 0.0\r\nresult refreshed\r\n")
        Assert.assertEquals(BattleNetSignIn.completeLines(utf16("asked 0.0\r\nresult refre")), "asked 0.0\r\n")
        Assert.assertEquals(BattleNetSignIn.completeLines("asked 0.0\nresult no-token\n".toByteArray()), "asked 0.0\nresult no-token\n")
        val parsed = BattleNetSignIn.parseTokenScriptOutput("asked 0.0\r\ntoken 0.5\r\nresult refreshed\r\n")
        Assert.assertEquals(parsed.outcome, "refreshed")
        Assert.assertEquals(parsed.lines, listOf("asked 0.0", "token 0.5", "result refreshed"))
        Assert.assertNull(BattleNetSignIn.parseTokenScriptOutput("asked 0.0\r\n").outcome)
    }

    @Test
    fun aScriptThatHasEndedIsReadOnceMoreAndOneStillRunningIsWaitedFor() {
        val output = Files.createTempFile("grill-renewal", ".log")
        Files.write(output, utf16("asked 0.0\r\nresult app-game-running\r\n"))
        Assert.assertEquals(BattleNetSignIn.awaitTokenScript(null, output, 5).outcome, "app-game-running")
        Files.write(output, utf16("asked 0.0\r\n"))
        val ended = BattleNetSignIn.awaitTokenScript(null, output, 5)
        Assert.assertNull(ended.outcome)
        Assert.assertEquals(ended.lines, listOf("asked 0.0"))
        if (!System.getProperty("os.name").startsWith("Windows", ignoreCase = true)) return
        // A script still running without a result is ended at the timeout.
        val script = ProcessBuilder("ping", "-n", "60", "127.0.0.1").redirectOutput(ProcessBuilder.Redirect.DISCARD).start()
        val timedOut = BattleNetSignIn.awaitTokenScript(script.toHandle(), output, 0, pollMillis = 10)
        Assert.assertNull(timedOut.outcome)
        Assert.assertEquals(timedOut.lines.last(), "the renewal script did not finish")
        Assert.assertTrue(script.waitFor(10, java.util.concurrent.TimeUnit.SECONDS), "the script was ended")
    }

    @Test
    fun everyRenewalOutcomeHasItsMessage() {
        fun problem(outcome: String?, vararg lines: String) =
            BattleNetSignIn.problem(BattleNetSignIn.TokenScriptResult(outcome, lines.toList()))
        Assert.assertNull(problem("refreshed"))
        Assert.assertTrue(problem("no-token")!!.startsWith("Battle.net did not start Warcraft III."))
        Assert.assertEquals(problem("app-game-running"), BattleNetSignIn.APP_GAME_RUNNING)
        Assert.assertTrue(problem("token-not-visible")!!.contains("start it from the Start menu"))
        Assert.assertEquals(problem(null), "The Battle.net sign-in renewal failed: it printed nothing.")
        Assert.assertEquals(problem(null, "a", "b", "c", "d"), "The Battle.net sign-in renewal failed: b | c | d.")
    }

    @Test
    fun commandLineQuotingKeepsArgumentsWhole() {
        Assert.assertEquals(OutsideStart.quoteWindowsArg("-editor"), "-editor")
        Assert.assertEquals(OutsideStart.quoteWindowsArg(""), "\"\"")
        Assert.assertEquals(OutsideStart.quoteWindowsArg("C:\\Program Files\\x.exe"), "\"C:\\Program Files\\x.exe\"")
        Assert.assertEquals(OutsideStart.quoteWindowsArg("say \"hi\""), "\"say \\\"hi\\\"\"")
        Assert.assertEquals(OutsideStart.quoteWindowsArg("C:\\dir with space\\"), "\"C:\\dir with space\\\\\"")
    }

    @Test
    fun aProcessStartedThroughWmiGetsItsArgumentsAndWorkingDirectory() {
        if (!System.getProperty("os.name").startsWith("Windows", ignoreCase = true)) throw SkipException("WMI is Windows only")
        val dir = Files.createTempDirectory("grill outside start")
        val out = dir.resolve("args.txt")
        val program = dir.resolve("Args.java")
        Files.writeString(program, """
            public class Args {
                public static void main(String[] a) throws Exception {
                    StringBuilder s = new StringBuilder(System.getProperty("user.dir"));
                    for (int i = 1; i < a.length; i++) s.append('\n').append('[').append(a[i]).append(']');
                    java.nio.file.Files.writeString(java.nio.file.Path.of(a[0]), s.toString());
                }
            }
        """.trimIndent())
        val args = listOf("plain", "with space", "quote\"inside", "trailing\\", "")
        val java = ProcessHandle.current().info().command().orElseThrow()
        val pid = OutsideStart.start(listOf(java, program.toString(), out.toString()) + args, dir, OutsideStart.Show.HIDDEN)
        Assert.assertTrue(pid > 0)
        val deadline = System.nanoTime() + 60_000_000_000L
        while (!Files.exists(out) && System.nanoTime() < deadline) Thread.sleep(100)
        Thread.sleep(200)
        Assert.assertEquals(Files.readString(out).lines(), listOf(dir.toString()) + args.map { "[$it]" })
    }

    @Test
    fun theWindowWatcherShipsAndRunsFromAFileNamedByItsContent() {
        val dir = Files.createTempDirectory("grill-window-watcher")
        val command = BattleNetSignIn.windowWatcherCommand(listOf("-Seconds", "30", "-FocusGame"), dir)!!
        Assert.assertEquals(command.subList(0, 6),
            listOf("powershell.exe", "-NoProfile", "-NonInteractive", "-ExecutionPolicy", "Bypass", "-File"))
        Assert.assertEquals(command.subList(7, command.size), listOf("-Seconds", "30", "-FocusGame"))
        val file = Paths.get(command[6])
        Assert.assertEquals(file.parent, dir)
        val script = Files.readString(file)
        Assert.assertTrue(script.contains("param([int]\$Seconds = 20, [int]\$GameSeconds = 60, [switch]\$FocusGame, [switch]\$Log)"))
        Assert.assertTrue(script.contains("\"ready\""), "the caller waits for this line")
        Assert.assertTrue(script.all { it.code < 128 }, "Windows PowerShell reads a .ps1 without BOM as ANSI")
        // A second call reuses the same file.
        Assert.assertEquals(BattleNetSignIn.windowWatcherCommand(listOf(), dir)!![6], command[6])
    }

    @Test
    fun legacyModeAndOtherSystemsNeverAskBattleNet() {
        val client = reforgedClient()
        val reports = mutableListOf<String>()
        val find: () -> BattleNetSignIn? = { throw AssertionError("Battle.net must not be asked") }
        Assert.assertNull(Wc3MapRunner.renewSignIn(client, editorLaunch, "legacy", reports::add, find, { true }, "Windows 11"))
        Assert.assertNull(Wc3MapRunner.renewSignIn(client, editorLaunch, "auto", reports::add, find, { true }, "Mac OS X"))
        Assert.assertNull(Wc3MapRunner.renewSignIn(client, editorLaunch, "battlenet", reports::add, find, { true }, "Linux"))
        Assert.assertNull(Wc3MapRunner.renewSignIn(client, listOf("war3.exe", "-window", "-loadfile", "map.w3x"), "auto",
            reports::add, find, { true }, "Windows 11"), "a classic launch has no -editor sign-in to renew")
        Assert.assertEquals(reports, emptyList<String>())
    }

    @Test
    fun autoModeLaunchesOnTheStoredSignInWhenBattleNetIsMissing() {
        val reports = mutableListOf<String>()
        Assert.assertNull(Wc3MapRunner.renewSignIn(reforgedClient(), editorLaunch, "auto", reports::add, { null }, { true }, "Windows 11"))
        Assert.assertEquals(reports.size, 1)
        Assert.assertTrue(reports[0].contains("may ask for a login"), reports[0])
    }

    @Test
    fun aRenewalLockHeldElsewhereIsReportedAsBusy() {
        if (!System.getProperty("os.name").startsWith("Windows", ignoreCase = true)) throw SkipException("Windows only")
        val config = Files.createTempDirectory("grill-battlenet-lock").resolve("Battle.net.config")
        ServerSocket().use { holder ->
            holder.bind(InetSocketAddress(InetAddress.getByAddress(byteArrayOf(127, 0, 0, 1)), BattleNetSignIn.gatePort(config)))
            var waited = false
            try {
                BattleNetSignIn.forTest(config).renew(gateWaitSeconds = 1, onWait = { waited = true })
                Assert.fail("the lock is held elsewhere")
            } catch (e: BattleNetSignIn.LockBusyException) {
                Assert.assertTrue(waited, "the user is told why the launch waits")
            }
        }
    }

    @Test(expectedExceptions = [IllegalStateException::class])
    fun aBusyRenewalLockStopsTheLaunchEvenInAutoMode() {
        // Launching without the lock could spend the token another tool's client has not read yet.
        val busy = object : BattleNetApp {
            override fun isRunning() = true
            override fun renew(onWait: () -> Unit): BattleNetSignIn.Renewal =
                throw BattleNetSignIn.LockBusyException("another launch is signing in")
        }
        Wc3MapRunner.renewSignIn(reforgedClient(), editorLaunch, "auto", {}, { busy }, { true }, "Windows 11")
    }

    @Test
    fun aSignInThatCannotBeWatchedNeitherFailsTheLaunchNorKeepsTheLock() {
        // The map runs by now: an exception would make `grill run --hot` roll back a session the new map uses.
        val gate = ServerSocket().apply { bind(InetSocketAddress(InetAddress.getByAddress(byteArrayOf(127, 0, 0, 1)), 0)) }
        val renewal = BattleNetSignIn.Renewal(gate, "TOKEN", { throw java.io.IOException("registry query timed out") }, 1)
        val reports = mutableListOf<String>()

        Wc3MapRunner.awaitSignIn(renewal, reports::add)

        Assert.assertTrue(gate.isClosed, "the lock is released")
        Assert.assertTrue(reports.last().contains("registry query timed out"), reports.toString())
    }

    @Test
    fun theLauncherWindowWatcherOutlivesASlowRenewalAndTheSignInAfterIt() {
        // The renewal asks the app again every 25 s for up to 90 s, and the app shows its window once more as the
        // launched client signs in (up to 120 s); each can bring its window back.
        Assert.assertTrue(BattleNetSignIn.WINDOW_WATCH_SECONDS >= 90 + BattleNetSignIn.SIGN_IN_TIMEOUT_SECONDS,
            "${BattleNetSignIn.WINDOW_WATCH_SECONDS} s")
    }

    @Test
    fun aSignInIsAwaitedUntilTheClientReplacesTheToken() {
        val gate = ServerSocket().apply { bind(InetSocketAddress(InetAddress.getByAddress(byteArrayOf(127, 0, 0, 1)), 0)) }
        val reads = mutableListOf("TOKEN", "TOKEN", "NEW")
        val renewal = BattleNetSignIn.Renewal(gate, "TOKEN", { reads.removeAt(0) }, 1)
        val reports = mutableListOf<String>()

        Wc3MapRunner.awaitSignIn(renewal, reports::add)

        Assert.assertTrue(reads.isEmpty(), "polled until the token changed")
        Assert.assertTrue(gate.isClosed)
        Assert.assertEquals(reports, listOf("Waiting for Warcraft III to sign in..."))
    }

    @Test
    fun anInvalidConfiguredPathFallsBackToDetection() {
        if (!System.getProperty("os.name").startsWith("Windows", ignoreCase = true)) throw SkipException("Windows path rules")
        val project = Files.createTempDirectory("grill-bad-setting")
        Files.writeString(Files.createDirectories(project.resolve(".vscode")).resolve("settings.json"),
            """{ "wurst.wc3path": "C:\\Games\\War<craft" }""")
        Assert.assertNull(WurstProjectConfig.configuredGamePath(project))
    }

    @Test(expectedExceptions = [IllegalStateException::class])
    fun battleNetModeRefusesToLaunchWithoutARenewal() {
        Wc3MapRunner.renewSignIn(reforgedClient(), editorLaunch, "battlenet", {}, { null }, { true }, "Windows 11")
    }
}
