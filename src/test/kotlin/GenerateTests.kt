import config.ScriptMode
import config.Wc3Patch
import file.CLICommand
import file.ExitHandler
import file.SetupApp
import file.SetupMain
import org.testng.Assert
import org.testng.annotations.Test
import java.nio.file.Files
import java.util.Comparator

private class ExitException2(val code: Int) : RuntimeException("exit $code")

private fun catchExit2(block: () -> Unit): Int {
    val prev = ExitHandler.handler
    var code = -1
    try {
        ExitHandler.handler = { throw ExitException2(it) }
        block()
    } catch (e: ExitException2) {
        code = e.code
    } finally {
        ExitHandler.handler = prev
    }
    return code
}

class GenerateTests {

    @Test(priority = 10)
    fun testNonInteractiveDefaults() {
        val setup = SetupMain()
        setup.parseArgs(listOf("generate", "myproject"))
        Assert.assertEquals(setup.command, CLICommand.GENERATE)
        Assert.assertEquals(setup.commandArg, "myproject")
        Assert.assertEquals(setup.scriptMode, ScriptMode.LUA)
        Assert.assertEquals(setup.wc3Patch, Wc3Patch.REFORGED)
        Assert.assertFalse(setup.addAgents)
        Assert.assertFalse(setup.addGithubWorkflow)
    }

    @Test(priority = 10)
    fun testScriptModeJassFlag() {
        val setup = SetupMain()
        setup.parseArgs(listOf("generate", "myproject", "--script-mode", "jass"))
        Assert.assertEquals(setup.scriptMode, ScriptMode.JASS)
        Assert.assertEquals(setup.wc3Patch, Wc3Patch.REFORGED)
    }

    @Test(priority = 10)
    fun testWc3PatchPre129Flag() {
        val setup = SetupMain()
        setup.parseArgs(listOf("generate", "myproject", "--wc3-patch", "pre1.29"))
        Assert.assertEquals(setup.scriptMode, ScriptMode.LUA)
        Assert.assertEquals(setup.wc3Patch, Wc3Patch.PRE_129)
    }

    @Test(priority = 10)
    fun testAllGenerateFlagsTogether() {
        val setup = SetupMain()
        setup.parseArgs(
            listOf(
                "generate", "myproject",
                "--script-mode", "jass",
                "--wc3-patch", "pre1.29",
                "--with-agents",
                "--with-ci"
            )
        )
        Assert.assertEquals(setup.scriptMode, ScriptMode.JASS)
        Assert.assertEquals(setup.wc3Patch, Wc3Patch.PRE_129)
        Assert.assertTrue(setup.addAgents)
        Assert.assertTrue(setup.addGithubWorkflow)
    }

    @Test(priority = 10)
    fun testWithAndNoAgentsFlagOrder() {
        val setup = SetupMain()
        setup.parseArgs(listOf("generate", "myproject", "--with-agents"))
        Assert.assertTrue(setup.addAgents)

        val setup2 = SetupMain()
        setup2.parseArgs(listOf("generate", "myproject", "--no-agents"))
        Assert.assertFalse(setup2.addAgents)
    }

    @Test(priority = 10)
    fun testNoArgsExitsZeroWithoutOldUi() {
        val code = catchExit2 { SetupMain().doMain(arrayOf()) }
        Assert.assertEquals(code, 0, "No-args launch should exit 0 (CLI-first message), not open the old Swing UI")
    }

    @Test(priority = 10)
    fun testCiWorkflowContainsExpectedContent() {
        val tmpDir = Files.createTempDirectory("grill-ci-test")
        try {
            SetupApp.writeCiWorkflow(tmpDir)
            val workflow = Files.readString(tmpDir.resolve(".github/workflows/grill.yml"))

            Assert.assertTrue(workflow.contains("name: Grill CI"))
            Assert.assertTrue(workflow.contains("container:"))
            Assert.assertTrue(workflow.contains("image: frotty/wurstscript"))
            Assert.assertTrue(workflow.contains("uses: actions/checkout@v4"))
            Assert.assertTrue(workflow.contains("run: grill install"))
            Assert.assertTrue(workflow.contains("run: grill build ExampleMap.w3x"))
            Assert.assertFalse(workflow.contains("uses: frotty/wurstscript@master"))
        } finally {
            Files.walk(tmpDir).sorted(Comparator.reverseOrder()).forEach {
                try {
                    Files.deleteIfExists(it)
                } catch (_: Exception) {
                }
            }
        }
    }
}
