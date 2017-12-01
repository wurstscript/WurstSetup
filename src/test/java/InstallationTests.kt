
import file.SetupMain
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

}
