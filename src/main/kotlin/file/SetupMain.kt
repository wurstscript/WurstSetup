package file

import config.ScriptMode
import config.Wc3Patch
import logging.KotlinLogging
import java.nio.file.Files
import java.nio.file.Path


class SetupMain {
    private val log = KotlinLogging.logger {}
    var isGUILaunch = false
	var command = CLICommand.HELP

	var commandArg = ""

    var measure = false

	var projectRoot: Path = SetupApp.DEFAULT_DIR

    var requireConfirmation = false

    var noPJass = false

    var quiet = false

    // Generate wizard options (defaults: non-interactive, Lua, Reforged, no extras)
    var addAgents: Boolean = false
    var addGithubWorkflow: Boolean = false
    var scriptMode: ScriptMode = ScriptMode.LUA
    var wc3Patch: Wc3Patch = Wc3Patch.REFORGED

	fun setProjectDir(dir: Path) {
		Files.createDirectories(dir)
		if (Files.exists(dir)) {
			projectRoot = dir
		}
	}

    fun doMain(args: Array<String>) {
        ExceptionHandler.setupExceptionHandler()
        parseArgs(args.asList())
        SetupApp.handleArgs(this)
    }

    /** Parse args without executing — use in unit tests to inspect field values. */
    fun parseArgs(argsList: List<String>) {
        if (argsList.isEmpty()) {
            isGUILaunch = true
        } else {
            parseCLIArgs(argsList)
        }
    }

	@Throws(IllegalArgumentException::class)
	private fun parseCLIArgs(argsList: List<String>) {
		val first = argsList[0]
		try {
			command = CLICommand.valueOf(first.uppercase())
			log.debug("found $command")
			if (argsList.size > 1) {
				if (!argsList[1].startsWith("-")) {
					commandArg = argsList[1]
					parseGlobalArgs(argsList, 2)
				} else {
					parseGlobalArgs(argsList, 1)
				}
			}
		} catch(e: IllegalArgumentException) {
			log.error("🔥 Invalid grill command <$first> ! Available commands: [generate|install|remove|test|typecheck|outdated|build] <command argument>")
            ExitHandler.exit(1)
		}
	}

	private fun parseGlobalArgs(argsList: List<String>, start: Int) {
		var i = start
		while (i < argsList.size) {
			val opt = GlobalOptions.values().firstOrNull { it.optionName == argsList[i] }
			if (opt != null) {
				val argEnd = i + 1 + opt.argCount
				if (argEnd > argsList.size) {
					log.error("🔥 Option ${opt.optionName} requires ${opt.argCount} argument(s).")
					ExitHandler.exit(1)
					return
				}
				opt.runOption(this, argsList.subList(i + 1, argEnd))
				i += 1 + opt.argCount
			} else {
				i++
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
