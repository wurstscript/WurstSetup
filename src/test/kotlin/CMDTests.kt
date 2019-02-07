
import file.SetupMain
import global.InstallationManager
import net.ConnectionManager
import org.testng.Assert
import org.testng.annotations.Test
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
    SetupMain.main(Arrays.asList("-silent", "-generate", "-projectDir", "stest/").toTypedArray())

    SetupMain.main(Arrays.asList("-silent", "-update", "-projectDir", "stest/").toTypedArray())
}
