import file.BattleNetSignIn
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

    @Test(expectedExceptions = [IllegalStateException::class])
    fun battleNetModeRefusesToLaunchWithoutARenewal() {
        Wc3MapRunner.renewSignIn(reforgedClient(), editorLaunch, "battlenet", {}, { null }, { true }, "Windows 11")
    }
}
