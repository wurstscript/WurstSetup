import global.CLIParser
import global.InstallationManager
import org.testng.annotations.Test
import org.testng.Assert
import java.nio.file.Files
import java.util.jar.JarOutputStream

class InstallationTests {
	@Test(priority = 8)
	fun testVersionPattern() {
		assert(InstallationManager.isJenkinsBuilt("1.7.0.0-jenkins-Wurst-531"))

	}

	@Test(priority = 8)
	fun testInstall() {
		CLIParser.getVersionFomJar()

		assert(InstallationManager.status != InstallationManager.InstallationStatus.NOT_INSTALLED)
	}

	@Test(priority = 8)
	fun testCompilerWithoutAgentDocsRemovesStaleLanguageDoc() {
		val previousInstallDir = System.getProperty("wurst.install.dir")
		val previousStatus = InstallationManager.status
		val previousCompilerVersion = InstallationManager.currentCompilerVersion
		val previousLatestCompilerVersion = InstallationManager.latestCompilerVersion
		val installDir = Files.createTempDirectory("wurst-setup-agent-docs")
		val compilerDir = installDir.resolve("wurst-compiler")
		val docsFile = compilerDir.resolve("agent-docs/WURST_LANGUAGE.md")
		try {
			System.setProperty("wurst.install.dir", installDir.toString())
			Files.createDirectories(docsFile.parent)
			Files.writeString(docsFile, "stale compiler guidance")
			Files.createDirectories(compilerDir)
			JarOutputStream(Files.newOutputStream(compilerDir.resolve("wurstscript.jar"))).use { }

			InstallationManager.verifyInstallation(probeVersion = false)

			Assert.assertFalse(Files.exists(docsFile))
		} finally {
			if (previousInstallDir == null) {
				System.clearProperty("wurst.install.dir")
			} else {
				System.setProperty("wurst.install.dir", previousInstallDir)
			}
			InstallationManager.status = previousStatus
			InstallationManager.currentCompilerVersion = previousCompilerVersion
			InstallationManager.latestCompilerVersion = previousLatestCompilerVersion
		}
	}
}

