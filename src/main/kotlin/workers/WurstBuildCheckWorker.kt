package workers

import file.CompileTimeInfo
import global.InstallationManager
import net.ConnectionManager
import net.NetStatus
import ui.SetupUpdateDialog
import ui.UiManager
import javax.swing.SwingWorker

class WurstBuildCheckWorker : SwingWorker<Boolean, Void>() {


    @Throws(Exception::class)
    override fun doInBackground(): Boolean? {
        ConnectionManager.checkWurstBuild()
        InstallationManager.verifyInstallation()
        if (ConnectionManager.netStatus == NetStatus.ONLINE && InstallationManager.isJenkinsBuilt(CompileTimeInfo.version)) {
            val latestSetupBuild = ConnectionManager.getLatestSetupBuild()
            if (latestSetupBuild > InstallationManager.getJenkinsBuildVer(CompileTimeInfo.version)) {
                SetupUpdateDialog("There is a more recent version of the setup tool available. It is highly recommended" +
                        " to update before making any further changes.")
            }
        }
        UiManager.refreshComponents()
        return null
    }
}
