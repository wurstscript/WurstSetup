
import file.CLICommand
import file.DependencyManager
import file.SetupApp
import file.SetupMain
import global.InstallationManager
import net.ConnectionManager
import org.testng.Assert
import org.testng.annotations.Test
import java.nio.file.Files
import java.util.*



class CMDTests {

	companion object {
		private const val INSTALL = "install"
		private const val REMOVE = "remove"
		private const val GENERATE = "generate"
        private const val HELP = "help"
        private const val TEST = "test"
		private const val WURSTSCRIPT = "wurstscript"
	}


	@Test(priority = 1)
	fun testUnInstallCmd() {
		SetupMain.main(Arrays.asList(INSTALL, WURSTSCRIPT).toTypedArray())
		ConnectionManager.checkConnectivity("http://google.com")
		ConnectionManager.checkWurstBuild()
		InstallationManager.verifyInstallation()
		Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)

		SetupMain.main(Arrays.asList(REMOVE, WURSTSCRIPT).toTypedArray())
		InstallationManager.verifyInstallation()
		Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.NOT_INSTALLED)

		SetupMain.main(Arrays.asList(INSTALL, WURSTSCRIPT).toTypedArray())
		InstallationManager.verifyInstallation()
		Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)
	}

    @Test(priority = 2)
    fun testCreateHelpCmd() {
        Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)
        val setupMain = SetupMain()
        setupMain.doMain(Arrays.asList(HELP).toTypedArray())

        Assert.assertEquals(setupMain.command, CLICommand.HELP)
    }

	@Test(priority = 2)
	fun testCreateProjectCmd() {
		Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)
		SetupMain.main(Arrays.asList(GENERATE, "myname").toTypedArray())

		Assert.assertTrue(Files.exists(SetupApp.DEFAULT_DIR.resolve("myname")))

		SetupMain.main(Arrays.asList(INSTALL, "-projectDir", "./myname/").toTypedArray())
	}

	@Test(priority = 3)
	fun testAddDependency() {
		Assert.assertTrue(Files.exists(SetupApp.DEFAULT_DIR.resolve("myname/wurst.build")))

		SetupMain.main(Arrays.asList(INSTALL, "https://github.com/Frotty/Frentity", "-projectDir", "./myname/").toTypedArray())

		val buildfile = String(Files.readAllBytes(SetupApp.DEFAULT_DIR.resolve("./myname/wurst.build")))
		Assert.assertTrue(buildfile.contains("https://github.com/Frotty/Frentity"))
	}


    @Test(priority = 3)
    fun testProjectTest() {

        val testproject = SetupApp.DEFAULT_DIR.resolve("testproject")
        DependencyManager.cloneRepo("https://github.com/wurstscript/WurstStdlib2.git", testproject)
        Assert.assertTrue(Files.exists(testproject.resolve("wurst.build")))

        SetupMain.main(Arrays.asList(INSTALL, "-projectDir", "./testproject/").toTypedArray())

        val setupMain = SetupMain()
        setupMain.projectRoot = testproject
        setupMain.doMain(arrayOf(TEST))
    }


	@Test(priority = 4)
	fun testInvalid() {
		Assert.expectThrows(IllegalArgumentException::class.java) { SetupMain.main(Arrays.asList("-someInvalidCommand").toTypedArray()) }
	}

}
