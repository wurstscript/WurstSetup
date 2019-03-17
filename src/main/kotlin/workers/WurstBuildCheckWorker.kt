package workers

import file.CompileTimeInfo
import global.InstallationManager
import mu.KotlinLogging
import net.ConnectionManager
import net.NetStatus
import ui.SetupUpdateDialog
import ui.UiManager
import javax.swing.SwingWorker

class WurstBuildCheckWorker : SwingWorker<Boolean, Void>() {

    private val log = KotlinLogging.logger {}

    @Throws(Exception::class)
    override fun doInBackground(): Boolean? {
        ConnectionManager.checkWurstBuild()
        InstallationManager.verifyInstallation()
        if (ConnectionManager.netStatus == NetStatus.ONLINE) {
            val latestSetupBuild = ConnectionManager.getLatestSetupBuild()
            log.info("current setup ver: ${InstallationManager.getJenkinsBuildVer(CompileTimeInfo.version)} latest Setup: $latestSetupBuild")
            if (latestSetupBuild > InstallationManager.getJenkinsBuildVer(CompileTimeInfo.version)) {
                SetupUpdateDialog("There is a more recent version of the setup tool available. It is highly recommended" +
                        " to update before making any further changes.")
            }
        }
        UiManager.refreshComponents()
        return null
    }
}
