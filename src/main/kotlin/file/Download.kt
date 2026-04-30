package file

import global.Log


import mu.KotlinLogging
import ui.MainWindow
import workers.DownloadWithProgressWorker
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
object Download {
    private val log = KotlinLogging.logger {}

	private const val compilerReleaseBaseUrl = "https://github.com/wurstscript/WurstScript/releases/download/nightly/"
	private const val bareboneUrl = "github.com/wurstscript/wurst-project-template/archive/master.zip"

    @Throws(IOException::class)
    private fun downloadFile(filePath: String, callback: (Path) -> Unit) {
        if (SetupApp.setup.isGUILaunch) {
			DownloadWithProgressWorker(filePath, MainWindow.ui.progressBar, callback).execute()
		} else {
			downloadDirect(filePath, callback)
        }
    }

	fun getHttpURLConnection(filePath: String): HttpURLConnection {
		val url = URL(filePath)
		val httpConnection = url.openConnection() as HttpURLConnection
		httpConnection.connectTimeout = 14000
		httpConnection.readTimeout = 20000
		httpConnection.addRequestProperty("User-Agent", "Chrome")
		return httpConnection
	}

    @Throws(IOException::class)
    fun downloadSetup(callback: (Path) -> Unit) {
        throw UnsupportedOperationException("Standalone grill updates are not currently published via GitHub releases.")
    }

    @Throws(IOException::class)
    fun downloadCompiler(callback: (Path) -> Unit) {
        downloadFile(compilerReleaseBaseUrl + getCompilerArchiveName(), callback)
    }

    @Throws(IOException::class)
    fun downloadBareboneProject(callback: (Path) -> Unit) {
        try {
            downloadFile("https://$bareboneUrl", callback)
        } catch (e: Exception) {
            log.warn( "downloadBareboneProject Exception caught", e)
            Log.println("Https error, falling back to unsafe http.")
            downloadFile("http://$bareboneUrl", callback)
        }
    }

	private fun downloadDirect(filePath: String, callback: (Path) -> Unit) {
			val httpConnection = getHttpURLConnection(filePath)
			val completeFileSize = httpConnection.contentLength
			val size = completeFileSize / 1024 / 1024
			log.info("\t\uD83D\uDCE5 (" + (if (size == 0) "<1" else size) + "MB)")
			val input = java.io.BufferedInputStream(httpConnection.inputStream)
			val downloadedFile = createDownloadTempFile(filePath)

			readStream(downloadedFile, input)

			input.close()
	        callback.invoke(downloadedFile)
	    }

		private fun readStream(destination: Path, input: BufferedInputStream) {
			FileOutputStream(destination.toFile()).use { fos ->
				BufferedOutputStream(fos, 1024).use {
					val data = ByteArray(1024)
					var x = input.read(data, 0, 1024)
					do {
						it.write(data, 0, x)
						x = input.read(data, 0, 1024)
					} while (x >= 0)
				}
			}
		}

    private fun createDownloadTempFile(filePath: String): Path {
        val fileName = filePath.substring(filePath.lastIndexOf("/") + 1)
        val suffix = when {
            fileName.endsWith(".zip", true) -> ".zip"
            fileName.endsWith(".jar", true) -> ".jar"
            else -> ".tmp"
        }
        val tempFile = Files.createTempFile("wurstsetup-", suffix)
        tempFile.toFile().deleteOnExit()
        return tempFile
    }

    private fun getCompilerArchiveName(): String {
        val os = System.getProperty("os.name").lowercase()
        val arch = System.getProperty("os.arch").lowercase()
        return when {
            os.contains("windows") -> "wurst-compiler-nightly-win-x64.zip"
            os.contains("mac") && (arch.contains("aarch64") || arch.contains("arm64")) -> "wurst-compiler-nightly-macos-arm64.zip"
            os.contains("mac") -> "wurst-compiler-nightly-macos-x64.zip"
            else -> "wurst-compiler-nightly-linux-x64.zip"
        }
    }
}
