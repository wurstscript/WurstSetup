package workers

import global.InstallationManager
import javax.swing.SwingWorker

class RemoveWurstWorker : SwingWorker<Boolean, Void>() {

    @Throws(Exception::class)
    override fun doInBackground(): Boolean? {
        InstallationManager.handleRemove()
        return null
    }
}
