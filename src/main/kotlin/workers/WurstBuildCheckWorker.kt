package workers

import global.InstallationManager
import net.ConnectionManager
import ui.MainWindow
import javax.swing.SwingUtilities
import javax.swing.SwingWorker

class WurstBuildCheckWorker : SwingWorker<Boolean, Void>() {


    @Throws(Exception::class)
    override fun doInBackground(): Boolean? {
        ConnectionManager.checkWurstBuild()
        InstallationManager.verifyInstallation()
        SwingUtilities.invokeLater({
            MainWindow.ui.refreshComponents(false)
        })
        return null
    }
}
