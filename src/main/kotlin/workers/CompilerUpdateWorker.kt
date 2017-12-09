package workers

import global.InstallationManager
import javax.swing.SwingWorker

class CompilerUpdateWorker : SwingWorker<Boolean, Void>() {

    @Throws(Exception::class)
    override fun doInBackground(): Boolean? {
        InstallationManager.handleUpdate()
        return null
    }
}
