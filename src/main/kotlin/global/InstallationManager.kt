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
    private const val CONFIG_FILE_NAME = "wurst_config.yml"
    private const val COMPILER_FILE_NAME = "wurstscript.jar"

    val installDir: Path = Paths.get(System.getProperty("user.home"), FOLDER_PATH)
    val configFile: Path = installDir.resolve(CONFIG_FILE_NAME)
    val compilerJar: Path = installDir.resolve(COMPILER_FILE_NAME)

    var wurstConfig: WurstConfigData? = null

    var status = InstallationStatus.NOT_INSTALLED
    var currentCompilerVersion = -1
    var latestCompilerVersion = 0

    fun verifyInstallation(): InstallationStatus {
        log.debug("verify Install")
        status = InstallationStatus.NOT_INSTALLED
        currentCompilerVersion = -1
        latestCompilerVersion = 0
        if (Files.exists(installDir) && Files.exists(compilerJar)) {
			log.debug("Found installation")
            status = InstallationStatus.INSTALLED_UNKNOWN
            try {
                if (!Files.isWritable(compilerJar)) {
                    CLIParser.showWurstInUse()
                } else {
					CLIParser.getVersionFomJar()
                }
            } catch (_: Error) {
                log.warn("Custom WurstScript installation detected.")
            }
        } else {
			log.info("WurstScript is not currently installed.")
		}
        if (ConnectionManager.netStatus == NetStatus.ONLINE) {
			log.debug("Client online, check for update")
            latestCompilerVersion = ConnectionManager.getLatestCompilerBuild()
			log.debug("latest compiler: $latestCompilerVersion")
            if (currentCompilerVersion >= latestCompilerVersion) {
                status = InstallationStatus.INSTALLED_UPTODATE
            }
        }
        return status
    }


    fun handleUpdate() {
        val isFreshInstall = status == InstallationStatus.NOT_INSTALLED
        try {
            log.debug(if (isFreshInstall) "isInstall" else "isUpdate")
            Log.print(if (isFreshInstall) "Installing WurstScript..\n" else "Updating WursScript..\n")
            Log.print("Downloading compiler..")
            log.info("\u23EC Downloading WurstScript..")

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
				ZipArchiveExtractor.extractArchive(it, installDir)
				Files.delete(it)
			}

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
		if (!Files.exists(compilerJar)) {
			Log.print("ERROR")
		} else {
			Log.print(if (isFreshInstall) "Installation complete\n" else "Update complete\n")
            log.info("✔ Installed WzrstScript")
			if (SetupApp.setup.isGUILaunch) {
				SwingUtilities.invokeLater { MainWindow.ui.progressBar.value = 0 }
			}
			verifyInstallation()
		}
	}

	private val jenkinsVerPattern = Pattern.compile("\\d\\.\\d\\.\\d\\.\\d(?:-\\w+)+-(\\d+)")

    fun isJenkinsBuilt(version: String): Boolean {
        val matcher = jenkinsVerPattern.matcher(version)
        if (matcher.matches()) {
            return true
        }
        return false
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
		return compilerJar.toAbsolutePath().toString()
	}

	enum class InstallationStatus {
        NOT_INSTALLED,
        INSTALLED_UNKNOWN,
        INSTALLED_OUTDATED,
        INSTALLED_UPTODATE
    }

}
