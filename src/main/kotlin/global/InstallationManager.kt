package global

import file.Download
import net.ConnectionManager
import net.NetStatus
import ui.ErrorDialog
import ui.MainWindow
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.regex.Pattern



/**
 * Manages the global Wurst installation located inside the ~/.wurst directory
 */
object InstallationManager {
    private val FOLDER_PATH = ".wurst"
    private val CONFIG_FILE_NAME = "wurst_config.yml"
    private val COMPILER_FILE_NAME = "wurstscript.jar"

    val installDir: Path = Paths.get(System.getProperty("user.home"), FOLDER_PATH)
    val configFile: Path = installDir.resolve(CONFIG_FILE_NAME)
    val compilerJar: Path = installDir.resolve(COMPILER_FILE_NAME)

    var wurstConfig: WurstConfigData? = null

    var status = InstallationStatus.NOT_INSTALLED
    var currentCompilerVersion = -1
    var latestCompilerVersion = 0

    fun verifyInstallation(): InstallationStatus {
        status = InstallationStatus.NOT_INSTALLED
        if (Files.exists(installDir) && Files.exists(configFile) && Files.exists(compilerJar)) {
//            wurstConfig = YamlHelper.yaml.loadAs(String(Files.readAllBytes(configFile)), WurstConfigData::class.java)
            status = InstallationStatus.INSTALLED_UNKNOWN
            getVersionFomJar()
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
        if(!Files.isWritable(compilerJar)) {
            showWurstInUse()
            return
        }
        val proc = Runtime.getRuntime().exec("java -jar " + compilerJar.toAbsolutePath() + " --version")
        proc.waitFor()
        val input = proc.inputStream.bufferedReader().use { it.readText() }.trim()
        val err = proc.errorStream.bufferedReader().use { it.readText() }

        if (err.contains("AccessDeniedException", true)) {
            // If the err output contains this exception, the .jar is currently running
            showWurstInUse()
        } else if (err.contains("Exception")) {
            status = InstallationStatus.INSTALLED_OUTDATED
        } else {
            if (isJenkinsBuilt(input)) {
                currentCompilerVersion = getJenkinsBuildVer(input)
                status = InstallationStatus.INSTALLED_OUTDATED
            }
        }
    }

    private fun showWurstInUse() {
        ErrorDialog("The Wurst compiler is currently in use.\n" +
                "Please close all running instances and vscode, then retry.", true)
    }

    fun handleUpdate() {
        val isFreshInstall = status == InstallationStatus.NOT_INSTALLED
        try {
            Log.print(if (isFreshInstall) "Installing WurstScript..\n" else "Updating WursScript..\n")
            Log.print("Downloading compiler..")
            val compilerFile = Download.downloadCompiler()
            Log.print("done\n")

            Log.print("Extracting compiler..")
            val extractSuccess = file.ZipArchiveExtractor.extractArchive(compilerFile, installDir)
            if (extractSuccess) {
                Log.print("done\n")
                if (status == InstallationStatus.NOT_INSTALLED) {
                    wurstConfig = WurstConfigData()
                }
                Log.print("Saving Config..")
//                Files.write(configFile, YamlHelper.yaml.dump(wurstConfig).toByteArray())
                Log.print("done\n")

                if (!Files.exists(compilerJar)) {
                    Log.print("ERROR")
                } else {
                    Log.print(if (isFreshInstall) "Installation complete\n" else "Update complete\n")
                }

            } else {
                Log.print("error\n")
                ErrorDialog("Could not extract patch files.\nWurst might still be in use.\nMake sure to close VSCode before updating.", false)
            }
            MainWindow.ui?.refreshComponents()
        } catch (e: Exception) {
            Log.print("\n===ERROR COMPILER UPDATE===\n" + e.message + "\nPlease report here: github.com/wurstscript/WurstScript/issues\n")
        }

    }

    private val jenkinsVerPattern = Pattern.compile("\\d\\.\\d\\.\\d\\.\\d(?:-\\w+)+-(\\d+)")!!

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

    enum class InstallationStatus {
        NOT_INSTALLED,
        INSTALLED_UNKNOWN,
        INSTALLED_OUTDATED,
        INSTALLED_UPTODATE
    }

}
