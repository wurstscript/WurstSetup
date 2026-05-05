import file.ExitHandler
import file.CLICommand
import file.DependencyManager
import file.SetupApp
import file.SetupMain
import global.InstallationManager
import net.ConnectionManager
import org.eclipse.jgit.internal.storage.file.FileRepository
import org.testng.Assert
import org.testng.annotations.Test
import java.nio.file.Files
import java.nio.file.Path
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
    Files.walk(path).sorted(Comparator.reverseOrder()).forEach {
        Files.deleteIfExists(it)
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
        private val generatedProjectName = "myname-${System.currentTimeMillis()}"
    }

    private fun ensureGeneratedProjectExists() {
        val projectDir = SetupApp.DEFAULT_DIR.resolve(generatedProjectName)
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
        deleteRecursively(SetupApp.DEFAULT_DIR.resolve(generatedProjectName))
        SetupMain.main(listOf(GENERATE, generatedProjectName).toTypedArray())

        Assert.assertTrue(Files.exists(SetupApp.DEFAULT_DIR.resolve(generatedProjectName)))

        SetupMain.main(listOf(INSTALL, "-projectDir", "./$generatedProjectName/").toTypedArray())
    }

    @Test(priority = 3)
    fun testAddDependency() {
        ensureGeneratedProjectExists()
        Assert.assertTrue(Files.exists(SetupApp.DEFAULT_DIR.resolve("$generatedProjectName/wurst.build")))

        SetupMain.main(listOf(INSTALL, "https://github.com/Frotty/Frentity", "-projectDir", "./$generatedProjectName/").toTypedArray())

        val buildfile = String(Files.readAllBytes(SetupApp.DEFAULT_DIR.resolve("./$generatedProjectName/wurst.build")))
        Assert.assertTrue(buildfile.contains("https://github.com/Frotty/Frentity"))
    }

    @Test(priority = 3)
    fun testAddDependencyBranched() {
        ensureGeneratedProjectExists()
        Assert.assertTrue(Files.exists(SetupApp.DEFAULT_DIR.resolve("$generatedProjectName/wurst.build")))

        SetupMain.main(listOf(INSTALL, "https://github.com/Frotty/wurst-item-recipes:main", "-projectDir", "./$generatedProjectName/").toTypedArray())

        val buildfile = String(Files.readAllBytes(SetupApp.DEFAULT_DIR.resolve("./$generatedProjectName/wurst.build")))
        Assert.assertTrue(buildfile.contains("https://github.com/Frotty/wurst-item-recipes:main"))
    }


    @Test(priority = 3)
    fun testProjectTest() {
        val testproject = Files.createTempDirectory("wurst-stdlib-test")
        DependencyManager.cloneRepo("https://github.com/wurstscript/WurstStdlib2.git", testproject)
        Assert.assertTrue(Files.exists(testproject.resolve("wurst.build")))

        SetupMain.main(listOf(INSTALL, "-projectDir", testproject.toString()).toTypedArray())

        val setupMain = SetupMain()
        setupMain.projectRoot = testproject
        val code = catchExit { setupMain.doMain(arrayOf(TEST)) }
        Assert.assertEquals(code, 0, "grill test should succeed on WurstStdlib2")
    }

    @Test(priority = 3)
    fun testBranchPull() {
        val testproject = Files.createTempDirectory("wurst-ptr-test")
        DependencyManager.cloneRepo("https://github.com/wurstscript/WurstStdlib2:ptr", testproject)

        Assert.assertTrue(Files.exists(testproject))
        FileRepository(testproject.resolve(".git").toFile()).use { repository ->
            Assert.assertEquals(repository.branch, "ptr")

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
        val projectDir = SetupApp.DEFAULT_DIR.resolve(generatedProjectName)
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

}
