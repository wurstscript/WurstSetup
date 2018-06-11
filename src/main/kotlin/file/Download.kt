package file

import mu.KotlinLogging
import ui.MainWindow
import workers.DownloadWithProgressWorker
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.nio.channels.Channels
import java.nio.file.Path
import java.nio.file.Paths


object Download {
    private val log = KotlinLogging.logger {}

    private const val baseUrl = "peeeq.de/hudson/job/Wurst/lastSuccessfulBuild/artifact/downloads/"
    private const val bareboneUrl = "github.com/wurstscript/WurstBareboneTemplate/archive/master.zip"
    private const val compileName = "wurstpack_compiler.zip"

    @Throws(IOException::class)
    private fun downloadFile(filePath: String, callback: (Path) -> Unit) {
        if (SetupApp.setup.silent) {
            downloadDirect(filePath, callback)
        } else {
            DownloadWithProgressWorker(filePath, MainWindow.ui.progressBar, callback).execute()
        }
    }

    @Throws(IOException::class)
    fun downloadCompiler(callback: (Path) -> Unit) {
        try {
            downloadFile("https://$baseUrl$compileName", callback)
        } catch (e: IOException) {
            downloadFile("http://$baseUrl$compileName", callback)
        }
    }

    @Throws(IOException::class)
    fun downloadBareboneProject(callback: (Path) -> Unit) {
        try {
            downloadFile("https://$bareboneUrl", callback)
        } catch (e: IOException) {
            downloadFile("http://$bareboneUrl", callback)
        }
    }

    private fun downloadDirect(filePath: String, callback: (Path) -> Unit) {
        val url = URL(filePath)
        val httpConnection = url.openConnection() as HttpURLConnection
        val size = httpConnection.contentLength / 1024 / 1024

        log.info("(" + (if (size == 0) "<1" else size) + "MB)")

        val filename = filePath.substring(filePath.lastIndexOf("/") + 1)
        val fos = FileOutputStream(filename)
        val rbc = Channels.newChannel(url.openStream())
        fos.use {
            fos.channel.transferFrom(rbc, 0, java.lang.Long.MAX_VALUE)
        }
        callback.invoke(Paths.get(filename))
    }
}
