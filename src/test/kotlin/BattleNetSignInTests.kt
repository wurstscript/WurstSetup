import config.WurstProjectConfig
import file.BattleNetApp
import file.BattleNetSignIn
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
    fun takesTheSameLockAsTheE2eHarnessAndWurstScript() {
        if (!System.getProperty("os.name").startsWith("Windows", ignoreCase = true)) throw SkipException("Windows paths")
        // wc3-e2e-harness node/src/runner/battlenet.cjs: 33000 + sha256(lower-cased path) as uint32 BE % 10000
        Assert.assertEquals(BattleNetSignIn.gatePort(Paths.get("C:\\Users\\tester\\AppData\\Roaming\\Battle.net\\Battle.net.config")), 36941)
    }

    @Test
    fun theWindowWatcherShipsAndRunsAsOneEncodedCommand() {
        val command = BattleNetSignIn.windowWatcherCommand("-Seconds 30 -FocusGame")!!
        Assert.assertEquals(command.subList(0, command.size - 1),
            listOf("powershell.exe", "-NoProfile", "-NonInteractive", "-ExecutionPolicy", "Bypass", "-EncodedCommand"))
        val script = String(java.util.Base64.getDecoder().decode(command.last()), Charsets.UTF_16LE)
        Assert.assertTrue(script.startsWith("& {"), script.take(20))
        Assert.assertTrue(script.contains("param([int]\$Seconds = 20, [switch]\$FocusGame, [switch]\$Log)"))
        Assert.assertTrue(script.contains("\"ready\""), "the caller waits for this line")
        Assert.assertTrue(script.trimEnd().endsWith("} -Seconds 30 -FocusGame"))
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
    fun theLauncherWindowWatcherOutlivesEveryRequestOfASlowRenewal() {
        // The renewal asks the app again every 25 s for up to 90 s; each request can bring its window back.
        Assert.assertTrue(BattleNetSignIn.WINDOW_WATCH_SECONDS > 90, "${BattleNetSignIn.WINDOW_WATCH_SECONDS} s")
    }

    @Test
    fun releasingTheRenewalEndsTheLauncherWindowWatcher() {
        // Otherwise it would go on minimizing a launcher the user opens after the map has started.
        val windows = System.getProperty("os.name").startsWith("Windows", ignoreCase = true)
        val watcher = (if (windows) ProcessBuilder("ping", "-n", "30", "127.0.0.1") else ProcessBuilder("sleep", "30"))
            .redirectOutput(ProcessBuilder.Redirect.DISCARD).start()
        val gate = ServerSocket().apply { bind(InetSocketAddress(InetAddress.getByAddress(byteArrayOf(127, 0, 0, 1)), 0)) }

        BattleNetSignIn.Renewal(gate, "TOKEN", { "NEW" }, 1, watcher).close()

        Assert.assertTrue(watcher.waitFor(5, java.util.concurrent.TimeUnit.SECONDS), "the watcher was ended")
        Assert.assertTrue(gate.isClosed)
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
