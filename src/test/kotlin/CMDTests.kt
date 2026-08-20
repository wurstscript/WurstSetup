import file.ExitHandler
import file.CLICommand
import file.DependencyManager
import file.SetupApp
import file.SetupMain
import global.InstallationManager
import net.ConnectionManager
import org.eclipse.jgit.internal.storage.file.FileRepository
import org.testng.Assert
import org.testng.annotations.AfterClass
import org.testng.annotations.Test
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Comparator


private class ExitException(val code: Int) : RuntimeException("exit $code")

private fun catchExit(block: () -> Unit): Int {
    val prev = ExitHandler.handler
    var code = -1
    try {
        ExitHandler.handler = { throw ExitException(it) }
        block()
    } catch (e: ExitException) {
        code = e.code
    } finally {
        ExitHandler.handler = prev
    }
    return code
}

private fun deleteRecursively(path: Path) {
    if (!Files.exists(path)) {
        return
    }
    Files.walk(path).use { files ->
        files.sorted(Comparator.reverseOrder()).forEach {
            Files.deleteIfExists(it)
        }
    }
}

private fun tryDeleteRecursively(path: Path) {
    try {
        deleteRecursively(path)
    } catch (_: Exception) {
        path.toFile().deleteOnExit()
    }
}

class CMDTests {

    companion object {
        private const val INSTALL = "install"
        private const val REMOVE = "remove"
        private const val GENERATE = "generate"
        private const val HELP = "help"
        private const val TEST = "test"
        private const val BUILD = "build"
        private const val WURSTSCRIPT = "wurstscript"
        private val testInstallDir = Files.createTempDirectory("wurst-setup-install")
        private val generatedProjectDir = Files.createTempDirectory("wurst-setup-generated")
        private val generatedProjectName = generatedProjectDir.toString()

        init {
            System.setProperty("wurst.install.dir", testInstallDir.toString())
        }
    }

    private fun ensureGeneratedProjectExists() {
        val projectDir = generatedProjectDir
        if (!Files.exists(projectDir.resolve("wurst.build"))) {
            SetupMain.main(listOf(GENERATE, generatedProjectName).toTypedArray())
        }
    }


    @Test(priority = 1)
    fun testUnInstallCmd() {
        SetupMain.main(listOf(INSTALL, WURSTSCRIPT).toTypedArray())
        ConnectionManager.checkConnectivity("http://google.com")
        ConnectionManager.checkWurstBuild()
        InstallationManager.verifyInstallation()
        Assert.assertNotEquals(InstallationManager.status, InstallationManager.InstallationStatus.NOT_INSTALLED)

        try {
            SetupMain.main(listOf(REMOVE, WURSTSCRIPT).toTypedArray())
            InstallationManager.verifyInstallation()
            Assert.assertEquals(
                InstallationManager.status,
                InstallationManager.InstallationStatus.NOT_INSTALLED,
                "Remove failed — compiler jar may be locked by VSCode or another process"
            )
        } finally {
            // Always restore so subsequent tests have a working compiler
            SetupMain.main(listOf(INSTALL, WURSTSCRIPT).toTypedArray())
            InstallationManager.verifyInstallation()
            Assert.assertNotEquals(
                InstallationManager.status,
                InstallationManager.InstallationStatus.NOT_INSTALLED,
                "Reinstall after remove must succeed for subsequent tests to work"
            )
        }
    }

    @Test(priority = 2)
    fun testCreateHelpCmd() {
        Assert.assertNotEquals(InstallationManager.status, InstallationManager.InstallationStatus.NOT_INSTALLED)
        val setupMain = SetupMain()
        setupMain.doMain(listOf(HELP).toTypedArray())

        Assert.assertEquals(setupMain.command, CLICommand.HELP)
    }

    @Test(priority = 2)
    fun testCreateProjectCmd() {
        Assert.assertNotEquals(InstallationManager.status, InstallationManager.InstallationStatus.NOT_INSTALLED)
        deleteRecursively(generatedProjectDir)
        SetupMain.main(listOf(GENERATE, generatedProjectName).toTypedArray())

        Assert.assertTrue(Files.exists(generatedProjectDir))

        SetupMain.main(listOf(INSTALL, "-projectDir", generatedProjectDir.toString()).toTypedArray())
    }

    @Test(priority = 3)
    fun testAddDependency() {
        ensureGeneratedProjectExists()
        Assert.assertTrue(Files.exists(generatedProjectDir.resolve("wurst.build")))

        SetupMain.main(listOf(INSTALL, "https://github.com/Frotty/Frentity", "-projectDir", generatedProjectDir.toString()).toTypedArray())

        val buildfile = String(Files.readAllBytes(generatedProjectDir.resolve("wurst.build")))
        Assert.assertTrue(buildfile.contains("https://github.com/Frotty/Frentity"))
    }

    @Test(priority = 3)
    fun testAddDependencyBranched() {
        ensureGeneratedProjectExists()
        Assert.assertTrue(Files.exists(generatedProjectDir.resolve("wurst.build")))

        SetupMain.main(listOf(INSTALL, "https://github.com/Frotty/wurst-item-recipes:main", "-projectDir", generatedProjectDir.toString()).toTypedArray())

        val buildfile = String(Files.readAllBytes(generatedProjectDir.resolve("wurst.build")))
        Assert.assertTrue(buildfile.contains("https://github.com/Frotty/wurst-item-recipes:main"))
    }


    @Test(priority = 3)
    fun testProjectTest() {
        val cwd = Paths.get("").toAbsolutePath()
        Files.deleteIfExists(cwd.resolve("compiled.j.txt"))
        Files.deleteIfExists(cwd.resolve("temp").resolve("output.j"))

        val testproject = Files.createTempDirectory("wurst-stdlib-test")
        DependencyManager.cloneRepo("https://github.com/wurstscript/WurstStdlib2.git", testproject)
        Assert.assertTrue(Files.exists(testproject.resolve("wurst.build")))

        SetupMain.main(listOf(INSTALL, "-projectDir", testproject.toString()).toTypedArray())

        val setupMain = SetupMain()
        setupMain.projectRoot = testproject
        val code = catchExit { setupMain.doMain(arrayOf(TEST, "--noPJass")) }
        Assert.assertEquals(code, 0, "grill test --noPJass should succeed on WurstStdlib2")
        Assert.assertFalse(Files.exists(cwd.resolve("compiled.j.txt")), "grill test must not emit compiled.j.txt in the caller root")
        Assert.assertFalse(Files.exists(cwd.resolve("temp").resolve("output.j")), "grill test must not emit output.j in caller temp/")
        Assert.assertTrue(Files.exists(testproject.resolve("_build/grill/output.j")), "compiler output should be emitted under _build/grill")
    }

    @Test(priority = 3)
    fun testBranchPull() {
        val testproject = Files.createTempDirectory("wurst-branch-test")
        DependencyManager.cloneRepo("https://github.com/wurstscript/WurstStdlib2:master", testproject)

        Assert.assertTrue(Files.exists(testproject))
        FileRepository(testproject.resolve(".git").toFile()).use { repository ->
            Assert.assertEquals(repository.branch, "master")

        }
    }

    @Test(priority = 3)
    fun testProjectBuild() {
        val testproject = Files.createTempDirectory("wurst-build-test")
        DependencyManager.cloneRepo("https://github.com/Frotty/ConflagrationSpell.git", testproject)
        Assert.assertTrue(Files.exists(testproject.resolve("wurst.build")))

        SetupMain.main(listOf(INSTALL, "-projectDir", testproject.toString()).toTypedArray())
        Assert.assertTrue(
            Files.exists(testproject.resolve("_build/dependencies")),
            "grill install must have pulled dependencies before building"
        )

        val setupMain = SetupMain()
        setupMain.projectRoot = testproject
        val code = catchExit { setupMain.doMain(arrayOf(BUILD, "ExampleMap.w3x", "--noPJass")) }
        Assert.assertEquals(code, 0, "grill build should succeed on ConflagrationSpell")
    }

    @Test(priority = 4)
    fun testTypecheckCreatedProject() {
        ensureGeneratedProjectExists()
        val projectDir = generatedProjectDir
        Assert.assertTrue(Files.exists(projectDir.resolve("wurst.build")), "generated project must exist")
        Assert.assertTrue(
            Files.exists(projectDir.resolve("_build/dependencies")),
            "grill install must have run on the generated project"
        )

        val setupMain = SetupMain()
        setupMain.projectRoot = projectDir
        val code = catchExit { setupMain.doMain(arrayOf("typecheck")) }
        Assert.assertEquals(code, 0, "grill typecheck must succeed on a freshly generated project with stdlib installed")
    }


    @Test(priority = 4)
    fun testInvalid() {
        val status = catchExit {
            SetupMain.main(listOf("-someInvalidCommand").toTypedArray())
        }
        Assert.assertEquals(status, 1)
    }

    @Test(priority = 5)
    fun testInvalidInstall() {
        val invalid = Files.createTempDirectory("wurst-invalid-test")
        DependencyManager.cloneRepo("https://github.com/Frotty/ConflagrationSpell.git", invalid)
        Assert.assertTrue(Files.exists(invalid.resolve("wurst.build")))

        val status = catchExit {
            SetupMain.main(listOf(INSTALL, "someInvalid", "-projectDir", invalid.toString()).toTypedArray())
        }
        Assert.assertEquals(status, 1)

    }

    @Test(priority = 10)
    fun testCompilerDependentCommandsFailWhenCompilerIsMissingEvenInQuietMode() {
        val project = Files.createTempDirectory("grill-missing-compiler")
        Files.writeString(project.resolve("wurst.build"), "projectName: missing-compiler\n")
        Files.createDirectories(project.resolve("ExampleMap.w3x"))

        val previousInstallDir = System.getProperty("wurst.install.dir")
        val emptyInstallDir = Files.createTempDirectory("grill-empty-install")
        try {
            System.setProperty("wurst.install.dir", emptyInstallDir.toString())

            val commands = listOf(
                arrayOf(TEST, "--quiet"),
                arrayOf("typecheck", "--quiet"),
                arrayOf(BUILD, "ExampleMap.w3x", "--quiet"),
                arrayOf("exportobjects", "ExampleMap.w3x", "--quiet")
            )
            commands.forEach { args ->
                val setup = SetupMain().apply { projectRoot = project }
                val status = catchExit { setup.doMain(args) }
                Assert.assertEquals(status, 1, "${args[0]} must fail when the compiler is missing")
            }
        } finally {
            if (previousInstallDir == null) {
                System.clearProperty("wurst.install.dir")
            } else {
                System.setProperty("wurst.install.dir", previousInstallDir)
            }
            tryDeleteRecursively(project)
            tryDeleteRecursively(emptyInstallDir)
        }
    }

    @AfterClass(alwaysRun = true)
    fun cleanupGeneratedProject() {
        tryDeleteRecursively(generatedProjectDir)
    }

}
