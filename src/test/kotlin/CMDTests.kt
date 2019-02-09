
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


@Test(priority = 1)
fun testUnInstallCmd() {
    SetupMain.main(Arrays.asList("-silent", "-force", "-update").toTypedArray())
    ConnectionManager.checkConnectivity()
    ConnectionManager.checkWurstBuild()
    InstallationManager.verifyInstallation()
    Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)

    SetupMain.main(Arrays.asList("-silent", "-force", "-remove").toTypedArray())
    InstallationManager.verifyInstallation()
    Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.NOT_INSTALLED)

    SetupMain.main(Arrays.asList("-silent", "-force", "-update").toTypedArray())
    InstallationManager.verifyInstallation()
    Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)
}

@Test(priority = 2)
fun testCreateProjectCmd() {
    Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)
    SetupMain.main(Arrays.asList("-silent", "-generate", "-projectDir", "bin/test/").toTypedArray())

	DependencyManager.isUpdateAvailable(Paths.get("bin/test/"), WurstProjectConfigData())

    SetupMain.main(Arrays.asList("-silent", "-update", "-projectDir", "bin/test/").toTypedArray())
}


@Test(priority = 3)
fun testInvalid() {
	val byteArrayOutputStream = ByteArrayOutputStream()
	System.setErr(PrintStream(byteArrayOutputStream))
	SetupMain.main(Arrays.asList("-silent", "-jdrhersdgsadf").toTypedArray())
	assert(byteArrayOutputStream.toString().contains("is not a valid option"))
}
