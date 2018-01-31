import file.main
import global.InstallationManager
import org.testng.Assert
import org.testng.annotations.Test
import java.util.*

@Test
fun testInstallCmd() {
    main(Arrays.asList("-silent", "-force", "-removeInstallation").toTypedArray())
    Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.NOT_INSTALLED)

    main(Arrays.asList("-silent", "-force", "-updateInstallation").toTypedArray())
    InstallationManager.verifyInstallation()
    Assert.assertEquals(InstallationManager.status, InstallationManager.InstallationStatus.INSTALLED_UPTODATE)
}