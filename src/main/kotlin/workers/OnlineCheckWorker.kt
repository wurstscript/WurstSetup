package workers

import mu.KotlinLogging
import net.ConnectionManager
import ui.UiManager
import javax.swing.SwingWorker

class OnlineCheckWorker(private val url: String, private val doneListener: () -> Unit) : SwingWorker<Boolean, Void>() {
    private val log = KotlinLogging.logger {}

    @Throws(Exception::class)
    override fun doInBackground(): Boolean? {
        log.debug("check connectivity")
        ConnectionManager.checkConnectivity(url)
        UiManager.refreshComponents()
        log.debug("check build")
        doneListener.invoke()
        return null
    }
}
