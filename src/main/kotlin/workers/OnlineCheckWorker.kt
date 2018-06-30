package workers

import net.ConnectionManager
import ui.UiManager
import javax.swing.SwingWorker

class OnlineCheckWorker : SwingWorker<Boolean, Void>() {


    @Throws(Exception::class)
    override fun doInBackground(): Boolean? {
        ConnectionManager.checkConnectivity()
        UiManager.refreshComponents()
        WurstBuildCheckWorker().execute()

        return null
    }
}
