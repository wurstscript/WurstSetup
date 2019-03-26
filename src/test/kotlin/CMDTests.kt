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

	@Test(priority = 1)
	fun testUnInstallCmd() {
		SetupMain.main(Arrays.asList(SILENT, FORCE, UPDATE).toTypedArray())
		ConnectionManager.checkConnectivity()
		ConnectionManager.checkWurstBuild()
		InstallationManager.verifyInstallation()
		Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)

		SetupMain.main(Arrays.asList(SILENT, FORCE, "-remove").toTypedArray())
		InstallationManager.verifyInstallation()
		Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.NOT_INSTALLED)

		SetupMain.main(Arrays.asList(SILENT, FORCE, UPDATE).toTypedArray())
		InstallationManager.verifyInstallation()
		Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)
	}

	@Test(priority = 2)
	fun testCreateProjectCmd() {
		Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)
		SetupMain.main(Arrays.asList(SILENT, "-generate", "-projectDir", PROJECT_DIR).toTypedArray())

		DependencyManager.isUpdateAvailable(Paths.get(PROJECT_DIR), WurstProjectConfigData())

		SetupMain.main(Arrays.asList(SILENT, UPDATE, "-projectDir", PROJECT_DIR).toTypedArray())
	}


	@Test(priority = 3)
	fun testInvalid() {
		val byteArrayOutputStream = ByteArrayOutputStream()
		System.setErr(PrintStream(byteArrayOutputStream))
		SetupMain.main(Arrays.asList(SILENT, "-jdrhersdgsadf").toTypedArray())
		assert(byteArrayOutputStream.toString().contains("is not a valid option"))
	}

	companion object {
		private const val SILENT = "-silent"
		private const val FORCE = "-force"
		private const val UPDATE = "-update"
		private const val PROJECT_DIR = "bin/test/"
	}

}
