package file

import mu.KotlinLogging
import java.nio.file.Files
import java.nio.file.Path


class SetupMain {
	private val log = KotlinLogging.logger {}

    var isGUILaunch = false

	var command = CLICommand.BUILD

	var commandArg = ""

	var projectRoot: Path = SetupApp.DEFAULT_DIR

    var requireConfirmation = false

	fun setProjectDir(dir: Path) {
		Files.createDirectories(dir)
		if (Files.exists(dir)) {
			projectRoot = dir
		}
	}

    fun doMain(args: Array<String>) {
        ExceptionHandler.setupExceptionHandler()
		val argsList = args.asList()
		if (argsList.isEmpty()) {
			isGUILaunch = true
		} else {
			parseCLIArgs(argsList)
		}
        SetupApp.handleArgs(this)
    }

	@Throws(IllegalArgumentException::class)
	private fun parseCLIArgs(argsList: List<String>) {
		val first = argsList[0]
		try {
			command = CLICommand.valueOf(first.toUpperCase())
			log.info("found $command")
			if (argsList.size > 1) {
				if (!argsList[1].startsWith("-")) {
					commandArg = argsList[1]
					parseGlobalArgs(argsList, 2)
				} else {
					parseGlobalArgs(argsList, 1)
				}

			}
		} catch(e: IllegalArgumentException) {
			log.error("Invalid grill command $first. Syntax: grill [install|update|remove|generate] <command argument>")
			throw e
		}
	}

	private fun parseGlobalArgs(argsList: List<String>, start: Int) {
		var skip = 0
		for (i in start until argsList.size) {
			if (skip > 0) {
				skip -= 1
				continue
			} else {
				GlobalOptions.values().forEach {
					if(it.optionName == argsList[start]) {
						it.runOption(this, argsList.subList(i, i + it.argCount))
					}
				}
			}
		}

	}

	companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SetupMain().doMain(args)
        }
    }
}




