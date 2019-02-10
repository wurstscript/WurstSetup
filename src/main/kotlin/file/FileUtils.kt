package file

import mu.KotlinLogging
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

val log = KotlinLogging.logger {}

fun clearFolder(dir: Path) {
	log.info("clearing: $dir")
	Files.walk(dir).forEach {
		clearPathInternal(it, dir)
	}
}

fun clearFile(it: Path) {
	try {
		Files.delete(it)
	} catch (_e: Exception) {
		if (_e.message?.contains("it is being used by another process") == true) {
			log.warn("It seems like wurst is still running. some files might not be removed.")
		} else {
			log.error("Exception: ", _e)
		}
	}
}

fun copyFolder(src: Path, dest: Path) {
	try {
		Files.walk(src)
			.forEach { source ->
				try {
					val target = dest.resolve(src.relativize(source))
					copyPath(source, target)
				} catch (e: Exception) {
					e.printStackTrace()
				}
			}
	} catch (ex: Exception) {
		ex.printStackTrace()
	}
}

private fun copyPath(source: Path?, target: Path?) {
	if (Files.isDirectory(source)) {
		if (!Files.exists(target)) {
			Files.createDirectory(target)
		}
	} else {
		Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING)
	}
}


private fun clearPathInternal(it: Path, dir: Path) {
	if (it != dir) {
		if (Files.isDirectory(it)) {
			clearFolder(it)
		} else {
			clearFile(it)
		}
	}
}
