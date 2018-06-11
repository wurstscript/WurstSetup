package workers

import net.ConnectionManager
import ui.MainWindow
import javax.swing.SwingUtilities
import javax.swing.SwingWorker

class OnlineCheckWorker : SwingWorker<Boolean, Void>() {


    @Throws(Exception::class)
    override fun doInBackground(): Boolean? {
        ConnectionManager.checkConnectivity()
        SwingUtilities.invokeLater({
            MainWindow.ui.refreshComponents(false)
            WurstBuildCheckWorker().execute()
        })

        return null
    }
}
