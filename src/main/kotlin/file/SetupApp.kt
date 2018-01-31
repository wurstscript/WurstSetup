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
        log.info("startup setup version: <{}>", CompileTimeInfo.version)
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
        val url = InstallationManager::class.java.protectionDomain.codeSource.location
        val ownFile = Paths.get(url.toURI())
        log.info("path: " + url)
        log.info("file: " + ownFile.toAbsolutePath())
        if (ownFile != null && Files.exists(ownFile) && ownFile.toString().endsWith(".jar") && ownFile.parent.fileName.toString() != ".wurst") {
            log.info("copy jar")
            Files.copy(ownFile, Paths.get(InstallationManager.installDir.toString(), "WurstSetup.jar"), StandardCopyOption.REPLACE_EXISTING)
        }
    }

    fun handleArgs(setup: SetupMain) {
        if (ConnectionManager.netStatus == NetStatus.ONLINE && InstallationManager.isJenkinsBuilt(CompileTimeInfo.version)) {
            log.info("setup update check")
            val latestSetupBuild = ConnectionManager.getLatestSetupBuild()
            if (latestSetupBuild > InstallationManager.getJenkinsBuildVer(CompileTimeInfo.version)) {
                SetupUpdateDialog("There is a more recent version of the setup tool available. It is highly recommended" +
                        " to update before making any further changes.")
                return
            }
        }

        log.info("setup uptodate")
        if (!setup.silent) {
            log.info("is GUI launch")
            UiManager.initUI()
        } else {
            log.info("is silent launch")
            if (setup.removeInstallation) {
                if (InstallationManager.status != InstallationManager.InstallationStatus.NOT_INSTALLED) {
                    log.info("remove installation")
                    if (setup.force) {
                        InstallationManager.handleRemove()
                    }
                }
            } else if (setup.updateInstall) {
                if (InstallationManager.status == InstallationManager.InstallationStatus.INSTALLED_OUTDATED
                        || InstallationManager.status == InstallationManager.InstallationStatus.NOT_INSTALLED) {
                    log.info("compiler update found")
                    if (setup.force) {
                        InstallationManager.handleUpdate()
                    } else {
                        UpdateFoundDialog("A Wurst compiler update has been found!")
                    }
                }
            }
        }
    }
}
