package global

import logging.KotlinLogging

object CLIParser {
	private val log = KotlinLogging.logger {}

	/** Gets the version of the wurstscript.jar via cli */
	fun getVersionFomJar() {
		log.debug("running wurst to extract the version")
		val proc = Runtime.getRuntime().exec(InstallationManager.compilerLaunchCommand("--version"))
		val input = proc.inputStream.bufferedReader().use { it.readText() }.trim()
		val err = proc.errorStream.bufferedReader().use { it.readText() }
		proc.waitFor()

		if (err.isNotEmpty()) {
			log.error(err)
		}
		when {
			// If the err output contains this exception, the .jar is currently running
			err.contains("AccessDeniedException", true) -> showWurstInUse()
			// JVM startup errors (e.g. slim JRE missing lib/jvm.cfg) — treat as outdated so an update is offered
			err.contains("Error:") && input.isBlank() -> {
				log.warn("JVM startup error from bundled runtime: ${err.trim()}")
				InstallationManager.status = InstallationManager.InstallationStatus.INSTALLED_OUTDATED
			}
			// Other exceptions or failures require update to fix
			err.contains("Exception") || err.contains("Failed") -> {
				log.error("Classifying installation as outdated due to $err")
				InstallationManager.status = InstallationManager.InstallationStatus.INSTALLED_OUTDATED
			}
			else -> {
				parseCMDLine(input)
			}
		}
	}

	private fun parseCMDLine(input: String) {
		log.debug("parsing CMD output: $input")
		val lines = input.split(System.getProperty("line.separator"))
		lines.forEach { line ->
			if (InstallationManager.isJenkinsBuilt(line)) {
				log.debug("Found jenkins build string $line")
				InstallationManager.currentCompilerVersion = InstallationManager.getJenkinsBuildVer(line)
				InstallationManager.status = InstallationManager.InstallationStatus.INSTALLED_OUTDATED
			} else if (line.matches(Regex("""\d+\.\d+\.\d+\.\d+(?:-[\w.]+)*"""))) {
				log.debug("Found compiler version string $line")
				InstallationManager.status = InstallationManager.InstallationStatus.INSTALLED_UNKNOWN
			}
		}
		if (InstallationManager.status == InstallationManager.InstallationStatus.NOT_INSTALLED) {
			log.debug("Failed to extract jenkins version from $input")
			throw Error("Installation failed!")
		}
	}

	fun showWurstInUse() {
		log.error("The Wurst compiler is currently in use. Please close all running instances and vscode, then retry.")
	}
}
