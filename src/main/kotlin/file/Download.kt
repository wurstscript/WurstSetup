package file

import ui.MainWindow
import workers.DownloadWithProgressWorker
import java.io.IOException
import java.nio.file.Path

object Download {

    private const val baseUrl = "peeeq.de/hudson/job/Wurst/lastSuccessfulBuild/artifact/downloads/"
    private const val bareboneUrl = "github.com/wurstscript/WurstBareboneTemplate/archive/master.zip"
    private const val compileName = "wurstpack_compiler.zip"

    @Throws(IOException::class)
    private fun downloadFile(url: String, callback: (Path) -> Unit) {
        DownloadWithProgressWorker(url, MainWindow.ui.progressBar, callback).execute()
    }

    @Throws(IOException::class)
    fun downloadCompiler(callback: (Path) -> Unit) {
        try {
            downloadFile("https://" + baseUrl + compileName, callback)
        } catch (e: IOException) {
            downloadFile("http://" + baseUrl + compileName, callback)
        }
    }

    @Throws(IOException::class)
    fun downloadBareboneProject(callback: (Path) -> Unit) {
        try {
            downloadFile("https://" + bareboneUrl, callback)
        } catch (e: IOException) {
            downloadFile("http://" + bareboneUrl, callback)
        }
    }
}