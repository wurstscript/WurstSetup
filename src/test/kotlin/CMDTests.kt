
import file.SetupMain
import global.InstallationManager
import org.testng.Assert
import org.testng.annotations.Test
import java.util.*

//@Test(priority = 1)
//fun testUnInstallCmd() {
//    SetupMain.main(Arrays.asList("-silent", "-force", "-removeInstallation").toTypedArray())
//    Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.NOT_INSTALLED)
//
//    SetupMain.main(Arrays.asList("-silent", "-force", "-updateInstallation").toTypedArray())
//    InstallationManager.verifyInstallation()
//    Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)
//}
//
//@Test(priority = 2)
//fun testCreateProjectCmd() {
//    Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)
//    SetupMain.main(Arrays.asList("-silent", "-createProject", "-projectDir", "stest/").toTypedArray())
//
//    SetupMain.main(Arrays.asList("-silent", "-updateProject", "-projectDir", "stest/").toTypedArray())
//}