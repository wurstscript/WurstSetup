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


class CMDTests {

    companion object {
        private const val INSTALL = "install"
        private const val REMOVE = "remove"
        private const val GENERATE = "generate"
        private const val HELP = "help"
        private const val TEST = "test"
        private const val BUILD = "build"
        private const val WURSTSCRIPT = "wurstscript"
    }


    @Test(priority = 1)
    fun testUnInstallCmd() {
        SetupMain.main(listOf(INSTALL, WURSTSCRIPT).toTypedArray())
        ConnectionManager.checkConnectivity("http://google.com")
        ConnectionManager.checkWurstBuild()
        InstallationManager.verifyInstallation()
        Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)

        SetupMain.main(listOf(REMOVE, WURSTSCRIPT).toTypedArray())
        InstallationManager.verifyInstallation()
        Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.NOT_INSTALLED)

        SetupMain.main(listOf(INSTALL, WURSTSCRIPT).toTypedArray())
        InstallationManager.verifyInstallation()
        Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)
    }

    @Test(priority = 2)
    fun testCreateHelpCmd() {
        Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)
        val setupMain = SetupMain()
        setupMain.doMain(listOf(HELP).toTypedArray())

        Assert.assertEquals(setupMain.command, CLICommand.HELP)
    }

    @Test(priority = 2)
    fun testCreateProjectCmd() {
        Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)
        SetupMain.main(listOf(GENERATE, "myname").toTypedArray())

        Assert.assertTrue(Files.exists(SetupApp.DEFAULT_DIR.resolve("myname")))

        SetupMain.main(listOf(INSTALL, "-projectDir", "./myname/").toTypedArray())
    }

    @Test(priority = 3)
    fun testAddDependency() {
        Assert.assertTrue(Files.exists(SetupApp.DEFAULT_DIR.resolve("myname/wurst.build")))

        SetupMain.main(listOf(INSTALL, "https://github.com/Frotty/Frentity", "-projectDir", "./myname/").toTypedArray())

        val buildfile = String(Files.readAllBytes(SetupApp.DEFAULT_DIR.resolve("./myname/wurst.build")))
        Assert.assertTrue(buildfile.contains("https://github.com/Frotty/Frentity"))
    }


    @Test(priority = 3)
    fun testProjectTest() {

        val testproject = SetupApp.DEFAULT_DIR.resolve("testproject")
        DependencyManager.cloneRepo("https://github.com/wurstscript/WurstStdlib2.git", testproject)
        Assert.assertTrue(Files.exists(testproject.resolve("wurst.build")))

        SetupMain.main(listOf(INSTALL, "-projectDir", "./testproject/").toTypedArray())

        val setupMain = SetupMain()
        setupMain.projectRoot = testproject
        setupMain.doMain(arrayOf(TEST))
    }

    @Test(priority = 3)
    fun testBranchPull() {
        val testproject = SetupApp.DEFAULT_DIR.resolve("ptrtestproject")
        DependencyManager.cloneRepo("https://github.com/wurstscript/WurstStdlib2:ptr", testproject)

        Assert.assertTrue(Files.exists(testproject))
        FileRepository(testproject.resolve(".git").toFile()).use { repository ->
            Assert.assertEquals(repository.branch, "ptr")

        }
    }

    @Test(priority = 3)
    fun testProjectBuild() {

        val testproject = SetupApp.DEFAULT_DIR.resolve("buildproject")
        DependencyManager.cloneRepo("https://github.com/Frotty/ConflagrationSpell.git", testproject)
        Assert.assertTrue(Files.exists(testproject.resolve("wurst.build")))

        SetupMain.main(listOf(INSTALL, "-projectDir", "./buildproject/").toTypedArray())

        val setupMain = SetupMain()
        setupMain.projectRoot = testproject
        setupMain.doMain(arrayOf(BUILD, "ExampleMap.w3x"))
    }


    @Test(priority = 4)
    fun testInvalid() {
        Assert.expectThrows(IllegalArgumentException::class.java) { SetupMain.main(listOf("-someInvalidCommand").toTypedArray()) }
    }

    @Test(priority = 5)
    fun testInvalidInstall() {
        val invalid = SetupApp.DEFAULT_DIR.resolve("invalidbuild")
        DependencyManager.cloneRepo("https://github.com/Frotty/ConflagrationSpell.git", invalid)
        Assert.assertTrue(Files.exists(invalid.resolve("wurst.build")))

        Assert.expectThrows(IllegalArgumentException::class.java) { SetupMain.main(listOf(INSTALL, "someInvalid", "-projectDir", "./invalidbuild/").toTypedArray()) }
    }

}
