package file

import global.InstallationManager
import global.Log
import mu.KotlinLogging
import net.ConnectionManager
import net.NetStatus
import ui.SetupUpdateDialog
import ui.UiManager
import ui.UpdateFoundDialog
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption


object SetupApp {
    private val log = KotlinLogging.logger {}
    init {
        val conStatus = ConnectionManager.checkConnectivity()
        log.info("ConnectionStatus: " + conStatus)
        when (conStatus) {
            NetStatus.CLIENT_OFFLINE -> Log.print("Client offline. All update functionality disabled.")
            NetStatus.SERVER_OFFLINE -> Log.print("Server offline. All update functionality disabled.")
            NetStatus.ONLINE -> Log.print("Server online!")
        }

        InstallationManager.verifyInstallation()
        copyJar()
    }

    private fun copyJar() {
        var url = InstallationManager::class.java.protectionDomain.codeSource.location
        val ownFile = Paths.get(url.toURI())
        log.info("path: " + url)
        log.info("file: " + ownFile.toAbsolutePath())
        if (ownFile.toString().endsWith(".jar") && ownFile.parent.fileName.toString() != ".wurst") {
            println("copy jar")
            Files.copy(ownFile, Paths.get(InstallationManager.installDir.toString(), "WurstSetup.jar"), StandardCopyOption.REPLACE_EXISTING)
        }
    }

    fun handleArgs(setup: SetupMain) {
        if (ConnectionManager.netStatus == NetStatus.ONLINE && InstallationManager.isJenkinsBuilt(CompileTimeInfo.version) ) {
            log.info("setup outdated")
            if (ConnectionManager.getLatestSetupBuild() > InstallationManager.getJenkinsBuildVer(CompileTimeInfo.version)) {
                SetupUpdateDialog("There is a more recent version of the setup tool available. It is highly recommended" +
                        " to update before making any further changes.")
            }
        } else {
            log.info("setup uptodate")
            if (!setup.silent) {
                log.info("is GUI launch")
                UiManager.initUI()
            } else {
                if (setup.checkWurstUpdate) {
                    println("silent compiler update check")
                    if (InstallationManager.status == InstallationManager.InstallationStatus.INSTALLED_OUTDATED) {
                        UpdateFoundDialog("A Wurst compiler update has been found!")
                    }
                }
            }
        }
    }

}
