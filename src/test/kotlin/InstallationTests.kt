
import file.SetupMain
import global.InstallationManager
import org.testng.annotations.Test

class InstallationTests {

    @Test fun testSilent() {
        val setupMain = SetupMain()
        setupMain.doMain(arrayOf("-silent"))
        assert(setupMain.silent)

        val setupMain2 = SetupMain()
        setupMain2.doMain(arrayOf())
        assert(!setupMain2.silent)
    }

    @Test fun testVersionPattern() {
        assert(InstallationManager.isJenkinsBuilt("1.7.0.0-jenkins-Wurst-531"))
    }

}
