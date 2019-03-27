
import file.SetupApp
import file.SetupMain
import global.InstallationManager
import net.ConnectionManager
import org.testng.Assert
import org.testng.annotations.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.file.Files
import java.util.*

class CMDTests {

	companion object {
		private const val INSTALL = "install"
		private const val REMOVE = "remove"
		private const val GENERATE = "generate"
		private const val UPDATE = "update"
		private const val WURSTSCRIPT = "wurstscript"
	}


	@Test(priority = 1)
	fun testUnInstallCmd() {
		SetupMain.main(Arrays.asList(INSTALL, WURSTSCRIPT).toTypedArray())
		ConnectionManager.checkConnectivity()
		ConnectionManager.checkWurstBuild()
		InstallationManager.verifyInstallation()
		Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)

		SetupMain.main(Arrays.asList(REMOVE, WURSTSCRIPT).toTypedArray())
		InstallationManager.verifyInstallation()
		Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.NOT_INSTALLED)

		SetupMain.main(Arrays.asList(UPDATE, WURSTSCRIPT).toTypedArray())
		InstallationManager.verifyInstallation()
		Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)
	}

	@Test(priority = 2)
	fun testCreateProjectCmd() {
		Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)
		SetupMain.main(Arrays.asList(GENERATE, "myname").toTypedArray())

		Assert.assertTrue(Files.exists(SetupApp.DEFAULT_DIR.resolve("myname")))

		SetupMain.main(Arrays.asList(UPDATE, "-projectDir", "./myname/").toTypedArray())
	}

	@Test(priority = 3)
	fun testAddDependency() {
		Assert.assertTrue(Files.exists(SetupApp.DEFAULT_DIR.resolve("myname/wurst.build")))

		SetupMain.main(Arrays.asList(INSTALL, "https://github.com/Frotty/Frentity/tree/master/wurst", "-projectDir", "./myname/").toTypedArray())

		val buildfile = String(Files.readAllBytes(SetupApp.DEFAULT_DIR.resolve("./myname/wurst.build")))
		Assert.assertTrue(buildfile.contains("https://github.com/Frotty/Frentity/tree/master/wurst"))
	}


	@Test(priority = 4)
	fun testInvalid() {
		val byteArrayOutputStream = ByteArrayOutputStream()
		System.setErr(PrintStream(byteArrayOutputStream))
		SetupMain.main(Arrays.asList("-someInvalidCommand").toTypedArray())
		assert(byteArrayOutputStream.toString().contains("Invalid grill command"))
	}

}
