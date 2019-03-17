package workers

import config.WurstProjectConfigData
import java.nio.file.Path
import javax.swing.SwingWorker

/** Handles creating a new project */
class ProjectCreateWorker(private val projectRoot: Path, private val gameRoot: Path?, private val configData: WurstProjectConfigData) : SwingWorker<Boolean, Void>() {

    @Throws(Exception::class)
    override fun doInBackground(): Boolean? {
        config.WurstProjectConfig.handleCreate(projectRoot, gameRoot, configData)
        return null
    }
}
