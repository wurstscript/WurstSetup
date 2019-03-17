package global

import file.SetupApp
import mu.KotlinLogging
import ui.ErrorDialog
import java.util.concurrent.TimeUnit

object CLIParser {
	private val log = KotlinLogging.logger {}

	/** Gets the version of the wurstscript.jar via cli */
	fun getVersionFomJar() {
		log.info("running wurst to extract the version")
		val proc = Runtime.getRuntime().exec(arrayOf("java", "-jar", InstallationManager.compilerJar.toAbsolutePath().toString(), "--version"))
		proc.waitFor(100, TimeUnit.MILLISECONDS)
		val input = proc.inputStream.bufferedReader().use { it.readText() }.trim()
		val err = proc.errorStream.bufferedReader().use { it.readText() }

		if (err.isNotEmpty()) {
			log.error(err)
		}
		when {
			// If the err output contains this exception, the .jar is currently running
			err.contains("AccessDeniedException", true) -> showWurstInUse()
			// Other exceptions or failures require update to fix
			err.contains("Exception") -> InstallationManager.status = InstallationManager.InstallationStatus.INSTALLED_OUTDATED
			err.contains("Failed") -> InstallationManager.status = InstallationManager.InstallationStatus.INSTALLED_OUTDATED
			else -> {
				parseCMDLine(input)
			}
		}
	}

	private fun parseCMDLine(input: String) {
		log.info("parsing CMD output")
		val lines = input.split(System.getProperty("line.separator"))
		lines.forEach { line ->
			if (InstallationManager.isJenkinsBuilt(line)) {
				log.info("Found jenkins build string $line")
				InstallationManager.currentCompilerVersion = InstallationManager.getJenkinsBuildVer(line)
				InstallationManager.status = InstallationManager.InstallationStatus.INSTALLED_OUTDATED
			}
		}
		if (InstallationManager.status != InstallationManager.InstallationStatus.INSTALLED_OUTDATED) {
			throw Error("Installation failed!")
		}
	}

	fun showWurstInUse() {
		if (!SetupApp.setup.silent) {
			ErrorDialog("The Wurst compiler is currently in use.\n" +
				"Please close all running instances and vscode, then retry.", true)
		}
		log.error("The Wurst compiler is currently in use.\n" +
			"Please close all running instances and vscode, then retry.")
	}
}
