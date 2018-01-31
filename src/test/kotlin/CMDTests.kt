import file.main
import global.InstallationManager
import org.testng.Assert
import org.testng.annotations.Test
import java.util.*

@Test(priority = 1)
fun testUnInstallCmd() {
    main(Arrays.asList("-silent", "-force", "-removeInstallation").toTypedArray())
    Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.NOT_INSTALLED)

    main(Arrays.asList("-silent", "-force", "-updateInstallation").toTypedArray())
    InstallationManager.verifyInstallation()
    Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)
}

@Test(priority = 2)
fun testCreateProjectCmd() {
    Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)
    main(Arrays.asList("-silent", "-createProject", "-projectDir", "stest/").toTypedArray())

    main(Arrays.asList("-silent", "-updateProject", "-projectDir", "stest/").toTypedArray())
}