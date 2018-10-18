package workers

import config.WurstProjectConfigData
import java.nio.file.Path
import javax.swing.SwingWorker

class ProjectUpdateWorker(val projectRoot: Path, val gamePath: Path, private val configData: WurstProjectConfigData) : SwingWorker<Boolean, Void>() {

    @Throws(Exception::class)
    override fun doInBackground(): Boolean? {
        config.WurstProjectConfig.handleUpdate(projectRoot, gamePath, configData)
        return null
    }
}
