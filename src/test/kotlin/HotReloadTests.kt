import file.CLICommand
import file.ExitHandler
import file.SetupApp
import file.SetupMain
import file.Wc3ClientDetector
import file.Wc3MapRunner
import org.testng.Assert
import org.testng.annotations.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.jar.Attributes
import java.util.jar.JarEntry
import java.util.jar.JarOutputStream
import java.util.jar.Manifest

/**
 * `grill run --hot` and `grill reload` against a stand-in compiler (`RunCompilerFixture`) and session class
 * (`de.peeeq.wurstio.languageserver.requests.LuaHotReloadSession` in the test sources). Nothing launches Warcraft III:
 * the launch is replaced by a recorder.
 */
class HotReloadTests {
    private class TestExit(val code: Int) : RuntimeException()

    private data class Outcome(val code: Int, val out: String, val err: String)

    private class Project(scriptMode: String = "lua", withSession: Boolean = true) {
        val root: Path = Files.createTempDirectory("grill-hot")
        val install: Path = Files.createDirectories(root.resolve("install/wurst-compiler"))
        val customMapData: Path = root.resolve("CustomMapData")
        val client: Path = Files.createDirectories(root.resolve("Game/_retail_/x86_64"))

        init {
            val manifest = Manifest().apply {
                mainAttributes[Attributes.Name.MANIFEST_VERSION] = "1.0"
                mainAttributes[Attributes.Name.MAIN_CLASS] = "RunCompilerFixture"
            }
            JarOutputStream(Files.newOutputStream(install.resolve("wurstscript.jar")), manifest).use { jar ->
                val session = "de/peeeq/wurstio/languageserver/requests/LuaHotReloadSession.class"
                for (entry in listOf("RunCompilerFixture.class") + (if (withSession) listOf(session) else emptyList())) {
                    jar.putNextEntry(JarEntry(entry))
                    javaClass.getResourceAsStream("/$entry")!!.use { it.copyTo(jar) }
                    jar.closeEntry()
                }
            }
            Files.writeString(root.resolve("wurst.build"), "projectName: Example\nscriptMode: $scriptMode\nwc3Patch: v3.0\n")
            Files.writeString(root.resolve("Input.w3x"), "input map")
            // Avoid fetching core JASS: the fixture records invocations rather than compiling Wurst.
            val jassdoc = Files.createDirectories(root.resolve("_build/dependencies/jassdoc"))
            Files.writeString(jassdoc.resolve("common.j"), "")
            Files.writeString(client.resolve("Warcraft III.exe"), "not a game")
        }

        fun compilerArguments(): List<String> = Files.readAllLines(root.resolve("compiler-arguments.txt"))

        fun sessionCalls(): List<String> = root.resolve("session-arguments.txt").let {
            if (Files.exists(it)) Files.readAllLines(it) else emptyList()
        }

        /** Each launch, with the session calls made before it. */
        fun grill(vararg args: String, launches: MutableList<Pair<Path, List<String>>> = mutableListOf()): Outcome {
            val previousInstall = System.getProperty("wurst.install.dir")
            val previousExit = ExitHandler.handler
            val previousLauncher = SetupApp.launchMap
            val previousOut = System.out
            val previousErr = System.err
            val out = ByteArrayOutputStream()
            val err = ByteArrayOutputStream()
            var code = -1
            try {
                System.setProperty("wurst.install.dir", install.parent.toString())
                ExitHandler.handler = { throw TestExit(it) }
                SetupApp.launchMap = { _, map -> launches.add(map to sessionCalls()) }
                System.setOut(PrintStream(out))
                System.setErr(PrintStream(err))
                try {
                    SetupMain().apply { projectRoot = root }.doMain(arrayOf(*args, "--quiet",
                        "--custom-map-data", customMapData.toString(), "--wc3-path", client.toString()))
                } catch (exit: TestExit) {
                    code = exit.code
                }
            } finally {
                System.setOut(previousOut)
                System.setErr(previousErr)
                SetupApp.launchMap = previousLauncher
                ExitHandler.handler = previousExit
                if (previousInstall == null) System.clearProperty("wurst.install.dir")
                else System.setProperty("wurst.install.dir", previousInstall)
            }
            return Outcome(code, out.toString(), err.toString())
        }
    }

    @Test
    fun parsesHotFlagMapAfterFlagsAndCustomMapData() {
        val run = SetupMain()
        run.parseArgs(listOf("run", "--hot", "Map with spaces.w3x", "--dev"))
        Assert.assertEquals(run.command, CLICommand.RUN)
        Assert.assertTrue(run.hotReload)
        Assert.assertEquals(run.commandArg, "Map with spaces.w3x")
        Assert.assertTrue(run.devBuild)

        val reload = SetupMain()
        reload.parseArgs(listOf("reload", "--custom-map-data", "D:/CMD", "Other.w3x"))
        Assert.assertEquals(reload.command, CLICommand.RELOAD)
        Assert.assertEquals(reload.commandArg, "Other.w3x")
        Assert.assertEquals(reload.customMapDataPath.toString().replace('\\', '/'), "D:/CMD")
    }

    @Test
    fun hotRunBuildsWithLuaHotReloadAndStartsTheSessionBeforeLaunching() {
        val project = Project()
        val launches = mutableListOf<Pair<Path, List<String>>>()
        val outcome = project.grill("run", "--hot", "Input.w3x", "--dev", launches = launches)
        Assert.assertEquals(outcome.code, 0, outcome.err)
        val arguments = project.compilerArguments()
        Assert.assertTrue(arguments.containsAll(listOf("-build", "-lua", "-luaHotReload", "-dev")), arguments.toString())
        val start = listOf("start|${project.root.resolve("_build").toAbsolutePath()}|${project.customMapData.toAbsolutePath()}")
        Assert.assertEquals(project.sessionCalls(), start)
        // The session is in place before the map starts polling.
        Assert.assertEquals(launches, listOf(project.root.resolve("_build/Actual output.w3x").toAbsolutePath().normalize() to start))
    }

    @Test
    fun failedSessionStartPreventsLaunch() {
        // A compiler without hot reload has no session class.
        val project = Project(withSession = false)
        val launches = mutableListOf<Pair<Path, List<String>>>()
        val outcome = project.grill("run", "--hot", "Input.w3x", launches = launches)
        Assert.assertEquals(outcome.code, 1)
        Assert.assertTrue(outcome.err.contains("The installed compiler has no Lua hot reload"), outcome.err)
        Assert.assertEquals(launches, emptyList<Pair<Path, List<String>>>())
    }

    @Test
    fun plainRunNeitherBuildsForHotReloadNorStartsASession() {
        val project = Project()
        val launches = mutableListOf<Pair<Path, List<String>>>()
        Assert.assertEquals(project.grill("run", "Input.w3x", launches = launches).code, 0)
        Assert.assertFalse(project.compilerArguments().contains("-luaHotReload"))
        Assert.assertEquals(project.sessionCalls(), emptyList<String>())
        Assert.assertEquals(launches.size, 1)
    }

    @Test
    fun reloadRebuildsWithTheHotRunsArgumentsAndPrintsThePayload() {
        val project = Project()
        Assert.assertEquals(project.grill("run", "--hot", "Input.w3x", "--dev").code, 0)
        Files.delete(project.root.resolve("compiler-arguments.txt"))
        val outcome = project.grill("reload")
        Assert.assertEquals(outcome.code, 0, outcome.err)
        val arguments = project.compilerArguments()
        Assert.assertTrue(arguments.containsAll(listOf("-build", "-lua", "-luaHotReload", "-dev")), arguments.toString())
        Assert.assertEquals(arguments[arguments.indexOf("-inputmap") + 1], project.root.resolve("Input.w3x").toAbsolutePath().toString())
        Assert.assertEquals(project.sessionCalls().last(), "send|${project.root.resolve("_build")}|${project.customMapData}")
        Assert.assertTrue(outcome.out.contains("payload 1 (Hello_greet) written to"), outcome.out)
        Assert.assertTrue(outcome.out.contains("warning: closure Hello_init_closure1 was removed"), outcome.out)
        Assert.assertTrue(Files.exists(project.customMapData.resolve("Wurst/hotreload-1.pld")))
    }

    @Test
    fun refusedReloadExitsNonZeroWithTheReasons() {
        val project = Project()
        Assert.assertEquals(project.grill("run", "--hot", "Input.w3x").code, 0)
        Files.writeString(project.root.resolve("refuse-reload"), "")
        val outcome = project.grill("reload", "Input.w3x")
        Assert.assertEquals(outcome.code, 1)
        Assert.assertTrue(outcome.err.contains("the type of field Hello_Unit_hp changed"), outcome.err)
        Assert.assertTrue(outcome.err.contains("Hot reload refused"), outcome.err)
        Assert.assertFalse(outcome.out.contains("payload"), outcome.out)
    }

    @Test
    fun reloadWithoutAHotRunExitsNonZero() {
        val project = Project()
        val outcome = project.grill("reload", "Input.w3x")
        Assert.assertEquals(outcome.code, 1)
        Assert.assertTrue(outcome.err.contains("no map was started with hot run"), outcome.err)
        Assert.assertTrue(outcome.err.contains("grill run --hot"), outcome.err)
    }

    @Test
    fun failedReloadBuildWritesNoPayload() {
        val project = Project()
        Assert.assertEquals(project.grill("run", "--hot", "Input.w3x").code, 0)
        Files.writeString(project.root.resolve("fail-build"), "")
        val outcome = project.grill("reload", "Input.w3x")
        Assert.assertEquals(outcome.code, 1)
        Assert.assertTrue(outcome.err.contains("Wurst build failed"), outcome.err)
        Assert.assertEquals(project.sessionCalls().count { it.startsWith("send|") }, 0)
    }

    @Test
    fun jassProjectsAreRejectedBeforeBuilding() {
        for (command in listOf(arrayOf("run", "--hot", "Input.w3x"), arrayOf("reload", "Input.w3x"))) {
            val project = Project(scriptMode = "jass")
            val outcome = project.grill(*command)
            Assert.assertEquals(outcome.code, 1, command.joinToString(" "))
            Assert.assertTrue(outcome.err.contains("Lua"), outcome.err)
            Assert.assertTrue(outcome.err.contains("JHCR"), outcome.err)
            Assert.assertFalse(Files.exists(project.root.resolve("compiler-arguments.txt")), "Nothing is built")
            Assert.assertEquals(project.sessionCalls(), emptyList<String>())
        }
    }

    @Test
    fun customMapDataFollowsOptionThenVsCodeSettingThenDocuments() {
        val project = Files.createTempDirectory("grill-hot-cmd")
        val documents = project.resolve("Documents/Warcraft III")
        val setup = SetupMain().apply { projectRoot = project }
        Assert.assertEquals(Wc3MapRunner.customMapDataPath(setup) { documents }, documents.resolve("CustomMapData"))
        Files.createDirectories(project.resolve(".vscode"))
        val configured = project.resolve("Configured CMD")
        Files.writeString(project.resolve(".vscode/settings.json"),
            "{\"wurst.customMapDataPath\":\"${configured.toString().replace("\\", "\\\\")}\"}")
        Assert.assertEquals(Wc3MapRunner.customMapDataPath(setup) { documents }, configured)
        setup.customMapDataPath = project.resolve("Explicit")
        Assert.assertEquals(Wc3MapRunner.customMapDataPath(setup) { Assert.fail("An explicit folder wins"); documents },
            project.resolve("Explicit"))
    }

    @Test
    fun sessionCommandUsesTheInstalledCompilerJar() {
        val project = Project()
        val previousInstall = System.getProperty("wurst.install.dir")
        System.setProperty("wurst.install.dir", project.install.parent.toString())
        try {
            val command = global.InstallationManager.compilerClassCommand(
                "de.peeeq.wurstio.languageserver.requests.LuaHotReloadSession", "send", "a b", "c")
            Assert.assertEquals(command.drop(1), listOf("-cp", project.install.resolve("wurstscript.jar").toAbsolutePath().toString(),
                "de.peeeq.wurstio.languageserver.requests.LuaHotReloadSession", "send", "a b", "c"))
        } finally {
            if (previousInstall == null) System.clearProperty("wurst.install.dir")
            else System.setProperty("wurst.install.dir", previousInstall)
        }
    }
}
