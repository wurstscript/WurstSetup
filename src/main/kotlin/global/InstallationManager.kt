package global

import file.Download
import file.SetupApp
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
import java.util.concurrent.TimeUnit
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
        log.info("verify Install")
        status = InstallationStatus.NOT_INSTALLED
        currentCompilerVersion = -1
        latestCompilerVersion = 0
        if (Files.exists(installDir) && Files.exists(compilerJar)) {
            status = InstallationStatus.INSTALLED_UNKNOWN
            try {
                if (!Files.isWritable(compilerJar)) {
                    showWurstInUse()
                } else {
                    getVersionFomJar()
                }
            } catch (_: Error) {
                log.warn("Installation is custom.")
            }
        }
        if (ConnectionManager.netStatus == NetStatus.ONLINE) {
            latestCompilerVersion = ConnectionManager.getLatestCompilerBuild()
            if (currentCompilerVersion >= latestCompilerVersion) {
                status = InstallationStatus.INSTALLED_UPTODATE
            }
        }
        return status
    }

    /** Gets the version of the wurstscript.jar via cli */
    fun getVersionFomJar() {
        val proc = Runtime.getRuntime().exec("java -jar " + compilerJar.toAbsolutePath() + " --version")
        proc.waitFor(100, TimeUnit.MILLISECONDS)
        val input = proc.inputStream.bufferedReader().use { it.readText() }.trim()
        val err = proc.errorStream.bufferedReader().use { it.readText() }

        when {
            err.contains("AccessDeniedException", true) -> // If the err output contains this exception, the .jar is currently running
                showWurstInUse()
            err.contains("Exception") -> status = InstallationStatus.INSTALLED_OUTDATED
            else -> {
                parseCMDLine(input)
            }
        }
    }

    private fun parseCMDLine(input: String) {
        val lines = input.split(System.getProperty("line.separator"))
        lines.forEach { line ->
            if (isJenkinsBuilt(line)) {
                currentCompilerVersion = getJenkinsBuildVer(line)
                status = InstallationStatus.INSTALLED_OUTDATED
            }
        }
        if (status != InstallationStatus.INSTALLED_OUTDATED) {
            throw Error("Installation failed!")
        }
    }

    private fun showWurstInUse() {
        ErrorDialog("The Wurst compiler is currently in use.\n" +
                "Please close all running instances and vscode, then retry.", true)
    }

    fun handleUpdate() {
        val isFreshInstall = status == InstallationStatus.NOT_INSTALLED
        try {
            log.info(if (isFreshInstall) "isInstall" else "isUpdate")
            Log.print(if (isFreshInstall) "Installing WurstScript..\n" else "Updating WursScript..\n")
            Log.print("Downloading compiler..")
            log.info("download compiler")

            Download.downloadCompiler {
                Log.print(" done.\n")

                ExtractWorker(it, if (SetupApp.setup.silent) null else MainWindow.ui.progressBar) {
                    if (it) {
                        Log.print("done\n")
                        if (status == InstallationStatus.NOT_INSTALLED) {
                            wurstConfig = WurstConfigData()
                        }
                        log.info("done")
                        if (!Files.exists(compilerJar)) {
                            Log.print("ERROR")
                        } else {
                            Log.print(if (isFreshInstall) "Installation complete\n" else "Update complete\n")
                            if (! SetupApp.setup.silent) {
                                SwingUtilities.invokeLater { MainWindow.ui.progressBar.value = 0 }
                            }
                            InstallationManager.verifyInstallation()
                        }

                    } else {
                        Log.print("error\n")
                        log.error("error")
                        ErrorDialog("Could not extract patch files.\nWurst might still be in use.\nMake sure to close VSCode before updating.", false)
                    }
                    UiManager.refreshComponents()
                }.execute()


            }
        } catch (e: Exception) {
            log.error("exception: ", e)
            Log.print("\n===ERROR COMPILER UPDATE===\n" + e.message + "\nPlease report here: github.com/wurstscript/WurstScript/issues\n")
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
        log.info("removed installation")
    }

    private fun clearFolder(dir: Path) {
		log.info("clearing: $dir")
        Files.walk(dir)
                .forEach {
                    if (it != dir) {
                        if (Files.isDirectory(it)) {
                            clearFolder(it)
                        } else {
                            try {
                                Files.delete(it)
                            } catch (_e: Exception) {
								if (_e.message?.contains("it is being used by another process")!!) {
									log.warn("It seems like wurst is still running. some files might not be removed.")
									return@forEach
								} else {
									log.error { _e }
								}
                            }
                        }
                    }
                }
    }

    enum class InstallationStatus {
        NOT_INSTALLED,
        INSTALLED_UNKNOWN,
        INSTALLED_OUTDATED,
        INSTALLED_UPTODATE
    }


}
