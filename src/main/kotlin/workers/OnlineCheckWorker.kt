package workers

import mu.KotlinLogging
import net.ConnectionManager
import ui.UiManager
import javax.swing.SwingWorker

class OnlineCheckWorker : SwingWorker<Boolean, Void>() {
    private val log = KotlinLogging.logger {}

    @Throws(Exception::class)
    override fun doInBackground(): Boolean? {
        log.info("check connectivity")
        ConnectionManager.checkConnectivity()
        UiManager.refreshComponents()
        log.info("check build")
        WurstBuildCheckWorker().execute()
        return null
    }
}
