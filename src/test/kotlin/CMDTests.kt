import config.WurstProjectConfigData
import file.DependencyManager
import file.SetupMain
import global.InstallationManager
import net.ConnectionManager
import org.testng.Assert
import org.testng.annotations.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.file.Paths
import java.util.*

class CMDTests {

	companion object {
		private const val SILENT = "-silent"
		private const val FORCE = "-force"
		private const val INSTALL = "-install"
		private const val REMOVE = "-remove"
		private const val GENERATE = "-generate"
		private const val UPDATE = "-update"
		private const val WURSTSCRIPT = "wurstscript"
		private const val PROJECT_DIR = "bin/test/"
	}


	@Test(priority = 1)
	fun testUnInstallCmd() {
		SetupMain.main(Arrays.asList(SILENT, FORCE, INSTALL, WURSTSCRIPT).toTypedArray())
		ConnectionManager.checkConnectivity()
		ConnectionManager.checkWurstBuild()
		InstallationManager.verifyInstallation()
		Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)

		SetupMain.main(Arrays.asList(SILENT, FORCE, REMOVE, WURSTSCRIPT).toTypedArray())
		InstallationManager.verifyInstallation()
		Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.NOT_INSTALLED)

		SetupMain.main(Arrays.asList(SILENT, FORCE, UPDATE, WURSTSCRIPT).toTypedArray())
		InstallationManager.verifyInstallation()
		Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)
	}

	@Test(priority = 2)
	fun testCreateProjectCmd() {
		Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)
		SetupMain.main(Arrays.asList(SILENT, GENERATE, "myname").toTypedArray())

		DependencyManager.isUpdateAvailable(Paths.get(PROJECT_DIR), WurstProjectConfigData())

		SetupMain.main(Arrays.asList(SILENT, UPDATE, "-projectDir ./myname/").toTypedArray())
	}


	@Test(priority = 3)
	fun testInvalid() {
		val byteArrayOutputStream = ByteArrayOutputStream()
		System.setErr(PrintStream(byteArrayOutputStream))
		SetupMain.main(Arrays.asList(SILENT, "-jdrhersdgsadf").toTypedArray())
		assert(byteArrayOutputStream.toString().contains("is not a valid option"))
	}

}
