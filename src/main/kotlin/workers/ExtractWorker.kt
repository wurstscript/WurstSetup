package workers

import global.InstallationManager
import global.Log
import mu.KotlinLogging
import java.nio.file.Files
import java.nio.file.Path
import javax.swing.JProgressBar
import javax.swing.SwingUtilities
import javax.swing.SwingWorker


class ExtractWorker(private val filePath: Path, val progressBar: JProgressBar?, val callback: (Boolean) -> Unit) : SwingWorker<Boolean, Void>() {
  private val log = KotlinLogging.logger {}
  private var extractSuccess: Boolean = false

  @Throws(Exception::class)
  override fun doInBackground(): Boolean? {
    try {
      if (progressBar != null) {
        SwingUtilities.invokeLater {
          progressBar.isIndeterminate = true
        }
      }
      Log.print("Extracting compiler..")
      log.debug("extract compiler")
      extractSuccess = file.ZipArchiveExtractor.extractArchive(filePath, InstallationManager.installDir)
      Files.delete(filePath)
    } catch (e: Exception) {
      log.error(e.localizedMessage)
      callback.invoke(false)
    }
    return null
  }

  override fun done() {
    if (progressBar != null) {
      SwingUtilities.invokeLater {
        progressBar.isIndeterminate = false
      }
    }
    callback.invoke(extractSuccess)
  }
}
