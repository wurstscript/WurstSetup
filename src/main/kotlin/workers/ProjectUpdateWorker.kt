package workers

import config.WurstProjectConfigData
import java.nio.file.Path
import javax.swing.SwingWorker

/** Handles updating an existing project */
class ProjectUpdateWorker(private val projectRoot: Path, private val gamePath: Path, private val configData: WurstProjectConfigData) : SwingWorker<Boolean, Void>() {

    @Throws(Exception::class)
    override fun doInBackground(): Boolean? {
        config.WurstProjectConfig.handleUpdate(projectRoot, gamePath, configData)
        return null
    }
}
