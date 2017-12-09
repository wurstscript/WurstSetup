package workers

import file.WurstProjectConfig
import file.WurstProjectConfigData
import java.nio.file.Path
import javax.swing.SwingWorker

class ProjectCreateWorker(val projectRoot: Path, val gameRoot: Path?, private val config: WurstProjectConfigData) : SwingWorker<Boolean, Void>() {

    @Throws(Exception::class)
    override fun doInBackground(): Boolean? {
        WurstProjectConfig.handleCreate(projectRoot, gameRoot, config)
        return null
    }
}
