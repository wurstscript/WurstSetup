package file

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL
import java.nio.channels.Channels
import java.nio.file.Path

object Download {

    private val baseUrl = "peeeq.de/hudson/job/Wurst/lastSuccessfulBuild/artifact/downloads/"
    private val bareboneUrl = "github.com/wurstscript/WurstBareboneTemplate/archive/master.zip"
    private val compileName = "wurstpack_compiler.zip"

    @Throws(IOException::class)
    private fun downloadFile(url: String, targetFile: Path): Path {
        var urlNorm = url
        if (urlNorm.startsWith("/")) {
            urlNorm = urlNorm.replaceFirst("/", "")
        }
        val website = URL(urlNorm.replace(" ", "%20"))
        val rbc = Channels.newChannel(website.openStream())
        val fos = FileOutputStream(targetFile.toFile())
        fos.channel.transferFrom(rbc, 0, java.lang.Long.MAX_VALUE)
        rbc.close()
        return targetFile
    }

    @Throws(IOException::class)
    fun downloadCompiler(): Path {
        return try {
            downloadFile("https://" + baseUrl + compileName, File.createTempFile("tempWurstCompiler", ".zip").toPath())
        } catch (e: IOException) {
            downloadFile("http://" + baseUrl + compileName, File.createTempFile("tempWurstCompiler", ".zip").toPath())
        }
    }

    @Throws(IOException::class)
    fun downloadBareboneProject(): Path {
        return try {
            downloadFile("https://" + bareboneUrl, File.createTempFile("tempProject", ".zip").toPath())
        } catch (e: IOException) {
            downloadFile("http://" + bareboneUrl, File.createTempFile("tempProject", ".zip").toPath())
        }
    }
}