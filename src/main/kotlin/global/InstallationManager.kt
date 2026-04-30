package global

import file.Download
import file.SetupApp
import file.ZipArchiveExtractor
import file.clearFolder
import mu.KotlinLogging
import net.ConnectionManager
import net.NetStatus
import ui.ErrorDialog
import ui.MainWindow
import ui.UiManager
import workers.ExtractWorker
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.regex.Pattern
import javax.swing.SwingUtilities


/**
 * Manages the global Wurst installation located inside the ~/.wurst directory
 */
object InstallationManager {
    private val log = KotlinLogging.logger {}
    private const val FOLDER_PATH = ".wurst"
    private const val COMPILER_FILE_NAME = "wurstscript.jar"
    private const val GRILL_JAR_NAME = "grill.jar"
    private const val LEGACY_GRILL_JAR_NAME = "WurstSetup.jar"

    val installDir: Path = Paths.get(System.getProperty("user.home"), FOLDER_PATH)
    val compilerDir: Path = installDir.resolve("wurst-compiler")
    val runtimeDir: Path = installDir.resolve("wurst-runtime")
    val grillDir: Path = installDir.resolve("grill-cli")
    val compilerJar: Path = compilerDir.resolve(COMPILER_FILE_NAME)
    val legacyCompilerJar: Path = installDir.resolve(COMPILER_FILE_NAME)
    val grillJar: Path = grillDir.resolve(GRILL_JAR_NAME)
    val legacyGrillJar: Path = installDir.resolve(LEGACY_GRILL_JAR_NAME)
    val wurstscriptLauncher: Path = installDir.resolve(if (isWindows()) "wurstscript.cmd" else "wurstscript")
    val grillLauncher: Path = installDir.resolve(if (isWindows()) "grill.cmd" else "grill")
    val bundledJava: Path = runtimeDir.resolve("bin").resolve(if (isWindows()) "java.exe" else "java")

    var wurstConfig: WurstConfigData? = null

    var status = InstallationStatus.NOT_INSTALLED
    var currentCompilerVersion = -1
    var latestCompilerVersion = 0

    fun verifyInstallation(): InstallationStatus {
        log.debug("verify Install")
        status = InstallationStatus.NOT_INSTALLED
        currentCompilerVersion = -1
        latestCompilerVersion = 0
        val detectedCompilerJar = detectCompilerJar()
        log.info("verifyInstallation: detectedCompilerJar=$detectedCompilerJar exists=${detectedCompilerJar?.let { Files.exists(it) }}")
        if (detectedCompilerJar != null) {
            log.info("Found installation at $detectedCompilerJar")
            status = InstallationStatus.INSTALLED_UNKNOWN
            try {
                if (!Files.isWritable(detectedCompilerJar)) {
                    CLIParser.showWurstInUse()
                } else {
                    CLIParser.getVersionFomJar()
                }
            } catch (_: Error) {
                log.warn("Custom WurstScript installation detected.")
            }
        } else {
            log.info("WurstScript is not currently installed (no compiler jar found at $compilerJar or $legacyCompilerJar).")
        }
        if (ConnectionManager.netStatus == NetStatus.ONLINE) {
				log.debug("Client online, check for update")
            latestCompilerVersion = ConnectionManager.getLatestCompilerBuild()
				log.debug("latest compiler: $latestCompilerVersion")
            if (currentCompilerVersion >= 0 && latestCompilerVersion > 0 && currentCompilerVersion >= latestCompilerVersion) {
                status = InstallationStatus.INSTALLED_UPTODATE
            }
        } else {
            log.debug("Client offline, check for update")
        }
        return status
    }


    fun handleUpdate() {
        val isFreshInstall = status == InstallationStatus.NOT_INSTALLED
        try {
            log.debug(if (isFreshInstall) "isInstall" else "isUpdate")
            Log.print(if (isFreshInstall) "Installing WurstScript..\n" else "Updating WursScript..\n")
            Log.print("Downloading compiler..")
            log.info("⏬ Downloading WurstScript..")

			downloadCompiler(isFreshInstall)
        } catch (e: Exception) {
            log.error("Exception: ", e)
            Log.print("\n===ERROR COMPILER UPDATE===\n" + e.message + "\nPlease report here: github.com/wurstscript/WurstScript/issues\n")
        }

    }

	private fun downloadCompiler(isFreshInstall: Boolean) {
		Download.downloadCompiler {
			Log.print(" done.\n")

			if (SetupApp.setup.isGUILaunch) {
				startExtractWorker(it, isFreshInstall)
			} else {
                log.info("\t\uD83D\uDCE6 Extracting..")
				ZipArchiveExtractor.extractArchive(it, installDir)
				Files.delete(it)
	                ensureGrillJarInstalled()
                setLaunchersExecutable()
                log.info("✔ Installed WurstScript to $installDir")
                log.debug("compilerJar exists: ${Files.exists(compilerJar)}")
                log.debug("grillJar exists: ${Files.exists(grillJar)}")
                log.debug("runtimeDir exists: ${Files.exists(runtimeDir)}")
			}

		}
	}

    private fun setLaunchersExecutable() {
        try {
            installDir.resolve("grill").toFile().setExecutable(true)
            installDir.resolve("wurstscript").toFile().setExecutable(true)
        } catch (_: Exception) {
        }
    }

    fun ensureGrillJarInstalled() {
        val ownJar = resolveOwnJar() ?: return
        Files.createDirectories(grillDir)
        if (ownJar != grillJar) {
            Files.copy(ownJar, grillJar, java.nio.file.StandardCopyOption.REPLACE_EXISTING)
        }
        try {
            Files.deleteIfExists(legacyGrillJar)
        } catch (_: Exception) {
        }
    }

    private fun startExtractWorker(it: Path, isFreshInstall: Boolean) {
		ExtractWorker(it, if (SetupApp.setup.isGUILaunch) MainWindow.ui.progressBar else null) {
			if (it) {
				checkExtraction(isFreshInstall)
			} else {
				Log.print("error\n")
				log.error("error")
				ErrorDialog("Could not extract patch files.\nWurst might still be in use.\nMake sure to close VSCode before updating.", false)
			}
			UiManager.refreshComponents()
		}.execute()
	}

	private fun checkExtraction(isFreshInstall: Boolean) {
		Log.print("done\n")
		if (status == InstallationStatus.NOT_INSTALLED) {
			wurstConfig = WurstConfigData()
		}
			if (detectCompilerJar() == null) {
				Log.print("ERROR")
			} else {
                ensureGrillJarInstalled()
                setLaunchersExecutable()
				Log.print(if (isFreshInstall) "Installation complete\n" else "Update complete\n")
	            log.debug("Installed WurstScript")
			if (SetupApp.setup.isGUILaunch) {
				SwingUtilities.invokeLater { MainWindow.ui.progressBar.value = 0 }
			}
			verifyInstallation()
		}
	}

	private val jenkinsVerPattern = Pattern.compile("""(?:\d\.){3}\d(?:-\w+)+-(\d+)""")

    fun isJenkinsBuilt(version: String): Boolean {
        return jenkinsVerPattern.matcher(version).matches()
    }

    fun getJenkinsBuildVer(version: String): Int {
        val matcher = jenkinsVerPattern.matcher(version)
        if (matcher.matches()) {
            return matcher.group(1).toInt()
        }
        return 0
    }

    fun handleRemove() {
        clearFolder(installDir)
        verifyInstallation()
        log.info("WurstScript has been removed.")
    }


		fun getCompilerPath(): String {
			return (detectCompilerJar() ?: compilerJar).toAbsolutePath().toString()
		}

    fun compilerLaunchCommand(vararg extraArgs: String): Array<String> {
        if (Files.exists(wurstscriptLauncher) && Files.exists(bundledJava)) {
            return if (isWindows()) {
                arrayOf("cmd", "/c", wurstscriptLauncher.toAbsolutePath().toString(), *extraArgs)
            } else {
                arrayOf(wurstscriptLauncher.toAbsolutePath().toString(), *extraArgs)
            }
        }
        val compiler = detectCompilerJar() ?: compilerJar
        val java = if (Files.exists(bundledJava)) bundledJava.toAbsolutePath().toString() else "java"
        return arrayOf(java, "-jar", compiler.toAbsolutePath().toString(), *extraArgs)
    }

    private fun hasRecognizedInstallationLayout(): Boolean {
        if (Files.exists(compilerJar) &&
            Files.exists(grillJar) &&
            Files.exists(runtimeDir) &&
            Files.exists(wurstscriptLauncher) &&
            Files.exists(grillLauncher)
        ) {
            return true
        }
        return Files.exists(legacyCompilerJar)
    }

    private fun detectCompilerJar(): Path? {
        return when {
            Files.exists(compilerJar) -> compilerJar
            Files.exists(legacyCompilerJar) -> legacyCompilerJar
            else -> null
        }
    }

    private fun resolveOwnJar(): Path? {
        return try {
            val url = InstallationManager::class.java.protectionDomain.codeSource.location
            val ownFile = Paths.get(url.toURI())
            if (Files.exists(ownFile) && ownFile.toString().endsWith(".jar")) ownFile else null
        } catch (_: Exception) {
            null
        }
    }

    private fun isWindows(): Boolean = System.getProperty("os.name").contains("windows", true)

		enum class InstallationStatus {
        NOT_INSTALLED,
        INSTALLED_UNKNOWN,
        INSTALLED_OUTDATED,
        INSTALLED_UPTODATE
    }

}
