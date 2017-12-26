package file


import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.ZipFile

object ZipArchiveExtractor {

    /**
     *
     * @param archive
     * @param destDir
     * @return true if all files were successfully extracted
     * @throws Exception
     */
    @Throws(Exception::class)
    fun extractArchive(archive: Path, destDir: Path): Boolean {
        if (!Files.exists(destDir)) {
            Files.createDirectories(destDir)
        }

        val zipFile = ZipFile(archive.toFile())
        val entries = zipFile.entries()

        val buffer = ByteArray(16384)
        while (entries.hasMoreElements()) {
            val entry = entries.nextElement()

            val entryFileName = entry.name

            val dir = buildDirectoryHierarchyFor(entryFileName, destDir.toFile())
            if (!dir.exists()) {
                dir.mkdirs()
            }

            if (!entry.isDirectory) {
                val targetFile = File(destDir.toFile(), entryFileName)
                if (targetFile.exists() && !targetFile.canWrite()) {
                    zipFile.close()
                    return false
                }
                val bos = BufferedOutputStream(FileOutputStream(targetFile))

                val bis = BufferedInputStream(zipFile.getInputStream(entry))

                var len = bis.read(buffer)
                while (len > 0) {
                    bos.write(buffer, 0, len)
                    len = bis.read(buffer)
                }

                bos.flush()
                bos.close()
                bis.close()
            }
        }
        zipFile.close()
        return true
    }

    private fun buildDirectoryHierarchyFor(entryName: String, destDir: File): File {
        val lastIndex = entryName.lastIndexOf('/')
        val internalPathToEntry = entryName.substring(0, lastIndex + 1)
        return File(destDir, internalPathToEntry)
    }
}