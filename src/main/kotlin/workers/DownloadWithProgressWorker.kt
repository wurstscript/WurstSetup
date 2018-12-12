package workers

import global.Log
import mu.KotlinLogging
import ui.MainWindow
import java.io.BufferedOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import javax.swing.JProgressBar
import javax.swing.SwingUtilities
import javax.swing.SwingWorker


class DownloadWithProgressWorker(private val filePath: String, private val progressBar: JProgressBar, val finish: Path.() -> Unit) : SwingWorker<Boolean, Void>() {
    private val log = KotlinLogging.logger {}
    @Throws(Exception::class)
    override fun doInBackground(): Boolean? {
        try {
            SwingUtilities.invokeLater { progressBar.isIndeterminate = false }
            MainWindow.ui.disableButtons()
            val url = URL(filePath)
            val httpConnection = url.openConnection() as HttpURLConnection
            httpConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0")
            val completeFileSize = httpConnection.contentLength

            val size = completeFileSize / 1024 / 1024
            Log.print("(" + (if (size == 0) "<1" else size) + "MB)")
            val input = java.io.BufferedInputStream(httpConnection.inputStream)
            var substring = filePath.substring(filePath.lastIndexOf("/") + 1)
            if (Files.exists(Paths.get(substring))) {
                substring += ".2.jar"
            }

            val fos = java.io.FileOutputStream(substring)
            val bout = BufferedOutputStream(fos, 1024)
            val data = ByteArray(1024)
            var downloadedFileSize: Long = 0
            var x = input.read(data, 0, 1024)
            do {
                downloadedFileSize += x.toLong()

                // calculate progress
                val currentProgress = (downloadedFileSize.toDouble() / completeFileSize.toDouble() * 100.0).toInt()

                // update progress bar
                SwingUtilities.invokeLater { progressBar.value = currentProgress }

                bout.write(data, 0, x)
                x = input.read(data, 0, 1024)
            } while (x >= 0)
            bout.close()
            input.close()
            fos.close()
        } catch (e: Exception) {
            log.error(e.localizedMessage)
        }
        return null
    }

    override fun done() {
        SwingUtilities.invokeLater { progressBar.isIndeterminate = true }
        finish.invoke(Paths.get(filePath.substring(filePath.lastIndexOf("/") + 1)))
    }
}
