package workers

import config.WurstProjectConfigData
import java.nio.file.Path
import javax.swing.SwingWorker

class ProjectCreateWorker(val projectRoot: Path, val gameRoot: Path?, private val configData: WurstProjectConfigData) : SwingWorker<Boolean, Void>() {

    @Throws(Exception::class)
    override fun doInBackground(): Boolean? {
        config.WurstProjectConfig.handleCreate(projectRoot, gameRoot, configData)
        return null
    }
}
