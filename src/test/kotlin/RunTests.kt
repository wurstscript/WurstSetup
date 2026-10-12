import config.newProjectConfig
import file.CLICommand
import file.ExitHandler
import file.SetupMain
import file.Wc3ClientDetector
import file.Wc3MapRunner
import org.testng.Assert
import org.testng.annotations.Test
import java.nio.file.Files
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.jar.Attributes
import java.util.jar.JarEntry
import java.util.jar.JarOutputStream
import java.util.jar.Manifest

class RunTests {
    @Test
    fun launchesNativeMacClientWithoutWine() {
        val root = Files.createTempDirectory("grill-run-mac")
        val bundle = root.resolve("Warcraft III.app")
        val exe = Files.writeString(Files.createDirectories(bundle.resolve("Contents/MacOS")).resolve("Warcraft III"), "")
        val setup = SetupMain().apply { gamePath = bundle }
        val client = Wc3MapRunner.resolveClient(setup)!!
        val map = root.resolve("Map with spaces.w3x")
        Assert.assertEquals(Wc3MapRunner.launchCommand(client, map, "Mac OS X"),
            listOf(exe.toString(), "-launch", "-editor", "-windowmode", "windowed", "-loadfile", map.toString()))
    }

    private class TestExit(val code: Int) : RuntimeException()

    private fun runWithCompilerFixture(failBuild: Boolean): Pair<Int, String> {
        val project = Files.createTempDirectory("grill-run-pipeline")
        val install = Files.createDirectories(project.resolve("install/wurst-compiler"))
        val manifest = Manifest().apply {
            mainAttributes[Attributes.Name.MANIFEST_VERSION] = "1.0"
            mainAttributes[Attributes.Name.MAIN_CLASS] = "RunCompilerFixture"
        }
        JarOutputStream(Files.newOutputStream(install.resolve("wurstscript.jar")), manifest).use { jar ->
            jar.putNextEntry(JarEntry("RunCompilerFixture.class"))
            javaClass.getResourceAsStream("/RunCompilerFixture.class")!!.use { it.copyTo(jar) }
            jar.closeEntry()
        }
        Files.writeString(project.resolve("wurst.build"), "projectName: Example\nscriptMode: lua\nwc3Patch: v3.0\n")
        Files.writeString(project.resolve("Input.w3x"), "input map")
        val build = Files.createDirectories(project.resolve("_build"))
        // Avoid fetching core JASS: this fixture records invocation rather than compiling Wurst.
        val jassdoc = Files.createDirectories(build.resolve("dependencies/jassdoc"))
        Files.writeString(jassdoc.resolve("common.j"), "")
        val client = Files.createDirectories(project.resolve("Game/_retail_/x86_64"))
        Files.writeString(client.resolve("Warcraft III.exe"), "invalid executable")
        if (failBuild) Files.writeString(project.resolve("fail-build"), "")
        else Files.writeString(project.resolve("omit-output"), "")
        val previousInstall = System.getProperty("wurst.install.dir")
        val previousExit = ExitHandler.handler
        val previousErr = System.err
        val errors = ByteArrayOutputStream()
        var code = -1
        try {
            System.setProperty("wurst.install.dir", install.parent.toString())
            ExitHandler.handler = { throw TestExit(it) }
            System.setErr(PrintStream(errors))
            try {
                SetupMain().apply { projectRoot = project }.doMain(arrayOf("run", "Input.w3x",
                    "--wc3-path", client.toString(), "--dev", "--noPJass", "--measure", "--quiet"))
            } catch (exit: TestExit) {
                code = exit.code
            }
            val arguments = Files.readAllLines(project.resolve("compiler-arguments.txt"))
            Assert.assertTrue(arguments.containsAll(listOf("-build", "-dev", "-noPJass", "-measure", "-lua")))
            Assert.assertEquals(arguments[arguments.indexOf("-inputmap") + 1], project.resolve("Input.w3x").toString())
        } finally {
            System.setErr(previousErr)
            ExitHandler.handler = previousExit
            if (previousInstall == null) System.clearProperty("wurst.install.dir")
            else System.setProperty("wurst.install.dir", previousInstall)
        }
        return code to errors.toString()
    }

    @Test
    fun compilationFailurePreventsLaunch() {
        val (code, errors) = runWithCompilerFixture(failBuild = true)
        Assert.assertEquals(code, 1)
        Assert.assertTrue(errors.contains("Wurst build failed"), errors)
        Assert.assertFalse(errors.contains("Could not launch Warcraft III"), errors)
    }

    @Test
    fun missingOutputAfterSuccessfulBuildPreventsLaunch() {
        val (code, errors) = runWithCompilerFixture(failBuild = false)
        Assert.assertEquals(code, 1)
        Assert.assertTrue(errors.contains("Could not launch Warcraft III"), errors)
        Assert.assertTrue(errors.contains("Built map was not found"), errors)
        Assert.assertTrue(errors.contains("--wc3-path"), errors)
    }

    @Test
    fun acceptsBuildFlagsAndProjectDirectory() {
        val project = Files.createTempDirectory("grill-run-project")
        val setup = SetupMain()
        setup.parseArgs(listOf("run", "Map with spaces.w3x", "--dev", "--measure", "--noPJass",
            "--quiet", "--debug", "-projectDir", project.toString(), "--wc3-path", "C:/Games/Warcraft III"))
        Assert.assertEquals(setup.command, CLICommand.RUN)
        Assert.assertEquals(setup.commandArg, "Map with spaces.w3x")
        Assert.assertEquals(setup.projectRoot.toAbsolutePath().normalize(), project.toAbsolutePath().normalize())
        Assert.assertTrue(setup.devBuild && setup.measure && setup.noPJass && setup.quiet && setup.debug)
        Assert.assertTrue(setup.gamePathExplicit)
    }

    @Test
    fun usesConfiguredClientAndHonorsExplicitOverride() {
        val project = Files.createTempDirectory("grill-run-settings")
        val client = Files.createDirectories(project.resolve("Game"))
        Files.writeString(client.resolve("war3.exe"), "")
        Files.createDirectories(project.resolve(".vscode"))
        val escapedPath = client.toString().replace("\\", "\\\\")
        Files.writeString(project.resolve(".vscode/settings.json"), "{\"wurst.wc3path\":\"$escapedPath\"}")
        val setup = SetupMain().apply { projectRoot = project }
        Assert.assertEquals(Wc3MapRunner.resolveClient(setup)?.executable, client.resolve("war3.exe"))
        setup.gamePath = project.resolve("missing-client")
        Assert.assertNull(Wc3MapRunner.resolveClient(setup), "Invalid explicit paths must not silently launch another client")
    }

    @Test
    fun launchesReforgedWithEditorAndLoadfileAsSeparateArguments() {
        val root = Files.createTempDirectory("grill-run-client")
        val exe = Files.createDirectories(root.resolve("_retail_/x86_64")).resolve("Warcraft III.exe")
        Files.writeString(exe, "")
        val map = root.resolve("Map with spaces.w3x")
        val command = Wc3MapRunner.launchCommand(Wc3ClientDetector.inspectGameRoot(root)!!, map, "Windows 11")
        Assert.assertEquals(command, listOf(exe.toString(), "-launch", "-editor", "-windowmode", "windowed", "-loadfile", map.toString()))
    }

    @Test
    fun launchArgumentsFollowClientEvenWhenProjectTargetsReforged() {
        val root = Files.createTempDirectory("grill-run-classic")
        val exe = root.resolve("war3.exe")
        Files.writeString(exe, "")
        val map = root.resolve("map.w3x")
        Assert.assertEquals(Wc3MapRunner.launchCommand(Wc3ClientDetector.inspectGameRoot(root)!!, map, "Windows 11"),
            listOf(exe.toString(), "-window", "-loadfile", map.toString()))
    }

    @Test
    fun staleConfiguredClientFallsBackButExplicitInvalidClientDoesNot() {
        val project = Files.createTempDirectory("grill-run-stale-client")
        Files.createDirectories(project.resolve(".vscode"))
        Files.writeString(project.resolve(".vscode/settings.json"), "{\"wurst.wc3path\":\"missing-client\"}")
        val root = Files.createDirectories(project.resolve("Game"))
        val exe = Files.writeString(root.resolve("war3.exe"), "")
        val setup = SetupMain().apply { projectRoot = project }
        Assert.assertEquals(Wc3MapRunner.resolveClient(setup) { root }?.executable, exe)
        setup.gamePath = project.resolve("explicit-missing-client")
        Assert.assertNull(Wc3MapRunner.resolveClient(setup) { Assert.fail("Explicit selection must not auto-detect"); root })
    }

    @Test
    fun linuxLaunchPrefixesWineAndKeepsPathsWithSpacesIntact() {
        val root = Files.createTempDirectory("grill-run-wine")
        val exe = Files.writeString(Files.createDirectories(root.resolve("_retail_/x86_64")).resolve("Warcraft III.exe"), "")
        val map = root.resolve("Map with spaces.w3x")
        Assert.assertEquals(Wc3MapRunner.launchCommand(Wc3ClientDetector.inspectGameRoot(root)!!, map, "Linux"),
            listOf("wine", exe.toString(), "-launch", "-editor", "-windowmode", "windowed", "-loadfile", map.toString()))
    }

    @Test
    fun stagesOldClientsInInstallationAndNewerClassicClientsInDocuments() {
        val root = Files.createTempDirectory("grill-run-legacy-stage")
        Files.writeString(root.resolve("war3.exe"), "")
        val client = Wc3ClientDetector.inspectGameRoot(root)!!
        val map = Files.writeString(root.resolve("built.w3x"), "compiled map")
        val documents = Files.createDirectories(root.resolve("documents/Warcraft III"))
        val oldTarget = Wc3MapRunner.stageMap(client.copy(version = "1.27.1"), map) { Assert.fail("Old clients use installation Maps"); documents }
        Assert.assertEquals(oldTarget, root.resolve("Maps/Test/GrillRunMap.w3x"))
        Assert.assertEquals(Files.readString(oldTarget), "compiled map")
        for (version in listOf("1.28.5", "1.29.2", "1.31.1")) {
            val target = Wc3MapRunner.stageMap(client.copy(version = version), map) { documents }
            Assert.assertEquals(target, documents.resolve("Maps/Test/GrillRunMap.w3x"))
            Assert.assertEquals(Files.readString(target), "compiled map")
            Assert.assertFalse(Files.exists(oldTarget), "A stale installation copy must not shadow the documents copy")
        }
        Files.writeString(map, "rebuilt map")
        Wc3MapRunner.stageMap(client.copy(version = "1.31.1"), map) { documents }
        Assert.assertEquals(Files.readString(documents.resolve("Maps/Test/GrillRunMap.w3x")), "rebuilt map")
    }

    @Test
    fun reforgedLaunchUsesBuildArtifactWithoutStaging() {
        val root = Files.createTempDirectory("grill-run-reforged-stage")
        Files.writeString(Files.createDirectories(root.resolve("_retail_/x86_64")).resolve("Warcraft III.exe"), "")
        val map = Files.writeString(root.resolve("built.w3x"), "compiled map")
        val client = Wc3ClientDetector.inspectGameRoot(root)!!
        Assert.assertEquals(Wc3MapRunner.stageMap(client, map) { Assert.fail("Reforged does not access documents"); root }, map)
    }

    @Test
    fun launchWindowFlagFollowsSelectedClientVersion() {
        val root = Files.createTempDirectory("grill-run-window")
        val exe = Files.writeString(root.resolve("Warcraft III.exe"), "")
        val client = Wc3ClientDetector.inspectGameRoot(root)!!
        val map = root.resolve("built.w3x")
        Assert.assertEquals(Wc3MapRunner.launchCommand(client.copy(version = "1.30.4"), map, "Windows"),
            listOf(exe.toString(), "-window", "-loadfile", map.toString()))
        Assert.assertEquals(Wc3MapRunner.launchCommand(client.copy(version = "1.31.1"), map, "Windows"),
            listOf(exe.toString(), "-windowmode", "windowed", "-loadfile", map.toString()))
    }

    @Test
    fun usesActualCompilerOutputIncludingRenamedMaps() {
        val project = Files.createTempDirectory("grill-run-output")
        val map = Files.writeString(project.resolve("Renamed map.w3x"), "built map")
        val output = listOf("Build succeeded. Output file: <$map>")
        Assert.assertEquals(Wc3MapRunner.builtMap(project, newProjectConfig(projectName = "Other"), output), map)
    }

    @Test
    fun fallsBackToCompilerNamingRule() {
        val project = Files.createTempDirectory("grill-run-output-default")
        val map = Files.writeString(Files.createDirectories(project.resolve("_build")).resolve("Example.w3x"), "built map")
        Assert.assertEquals(Wc3MapRunner.builtMap(project, newProjectConfig(projectName = "Example"), emptyList()), map)
    }

    @Test(expectedExceptions = [IllegalStateException::class])
    fun refusesMissingBuiltOutput() {
        val project = Files.createTempDirectory("grill-run-no-output")
        Wc3MapRunner.builtMap(project, newProjectConfig(projectName = "Missing"), emptyList())
    }
}
