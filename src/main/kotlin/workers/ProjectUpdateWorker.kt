package workers

import file.WurstProjectConfig
import file.WurstProjectConfigData
import java.nio.file.Path
import javax.swing.SwingWorker

class ProjectUpdateWorker(val projectRoot: Path, val gamePath: Path, private val config: WurstProjectConfigData) : SwingWorker<Boolean, Void>() {

    @Throws(Exception::class)
    override fun doInBackground(): Boolean? {
        WurstProjectConfig.handleUpdate(projectRoot, gamePath, config)
        return null
    }
}
