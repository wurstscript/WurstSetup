
import global.InstallationManager
import org.testng.annotations.Test


@Test(priority = 8)
fun testVersionPattern() {
    assert(InstallationManager.isJenkinsBuilt("1.7.0.0-jenkins-Wurst-531"))

}

@Test(priority = 8)
fun testInstall() {
	InstallationManager.getVersionFomJar()

	assert(InstallationManager.currentCompilerVersion > 1000)
}
