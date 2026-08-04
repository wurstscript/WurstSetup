package file

import config.ScriptMode
import logging.KotlinLogging
import java.nio.file.Files
import java.nio.file.Path


class SetupMain {
    private val log = KotlinLogging.logger {}
    var isGUILaunch = false
	var command = CLICommand.HELP

	var commandArg = ""

    var benchmarkForks = 3

    var benchmarkWarmup = 5

    var benchmarkIterations = 10

    var benchmarkFormat = BenchmarkFormat.HUMAN

    var benchmarkHelp = false

    var measure = false

    var devBuild = false

	var projectRoot: Path = SetupApp.DEFAULT_DIR

    var gamePath: Path? = null

    var requireConfirmation = false

    var noPJass = false

    var quiet = false

    var debug = false

    // Generate wizard options (defaults: non-interactive, Lua, Reforged, no extras)
    var addAgents: Boolean = false
    var addGithubWorkflow: Boolean = false
    var scriptMode: ScriptMode = ScriptMode.LUA
    var wc3Patch: String = CoreJassProvider.DEFAULT_PATCH

    /** Ids of curated dependencies (see [CuratedDependencies]) to seed into the generated project. */
    var curatedDependencyIds: MutableList<String> = mutableListOf()

    var gamePathExplicit: Boolean = false

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
			if (command == CLICommand.BENCHMARK) {
				parseBenchmarkArgs(argsList.drop(1))
				return
			}
			if (argsList.size > 1) {
				if (!argsList[1].startsWith("-")) {
					commandArg = argsList[1]
					parseGlobalArgs(argsList, 2)
				} else {
					parseGlobalArgs(argsList, 1)
				}
			}
		} catch(e: IllegalArgumentException) {
			log.error("❌ Unknown command <$first>.")
            log.info("Try: grill help")
            ExitHandler.exit(1)
		}
	}

	private fun parseBenchmarkArgs(argsList: List<String>) {
		var i = 0
		var filterSeen = false
		while (i < argsList.size) {
			when (val arg = argsList[i]) {
				"--help" -> {
					benchmarkHelp = true
					i++
				}
				"--forks" -> {
					benchmarkForks = parseBenchmarkPositiveInt(arg, benchmarkOptionValue(argsList, i, arg))
					i += 2
				}
				"--warmup" -> {
					benchmarkWarmup = parseBenchmarkNonNegativeInt(arg, benchmarkOptionValue(argsList, i, arg))
					i += 2
				}
				"--iterations" -> {
					benchmarkIterations = parseBenchmarkPositiveInt(arg, benchmarkOptionValue(argsList, i, arg))
					i += 2
				}
				"--format" -> {
					benchmarkFormat = parseBenchmarkFormat(arg, benchmarkOptionValue(argsList, i, arg))
					i += 2
				}
				else -> {
					val globalOption = GlobalOptions.values().firstOrNull { it.optionName == arg }
					if (globalOption != null) {
						val argEnd = i + 1 + globalOption.argCount
						if (argEnd > argsList.size) {
							benchmarkError("Option $arg requires ${globalOption.argCount} argument(s).")
						}
						globalOption.runOption(this, argsList.subList(i + 1, argEnd))
						i = argEnd
					} else if (arg.startsWith("-")) {
						benchmarkError("Unknown benchmark option <$arg>.")
					} else if (filterSeen) {
						benchmarkError("Unexpected extra benchmark argument <$arg>.")
					} else {
						commandArg = arg
						filterSeen = true
						i++
					}
				}
			}
		}
	}

	private fun benchmarkOptionValue(argsList: List<String>, optionIndex: Int, option: String): String {
		if (optionIndex + 1 >= argsList.size) {
			benchmarkError("Option $option requires an argument.")
		}
		return argsList[optionIndex + 1]
	}

	private fun parseBenchmarkPositiveInt(option: String, value: String): Int {
		val parsed = value.toIntOrNull()
		if (parsed == null || parsed <= 0) {
			benchmarkError("Option $option requires a positive integer.")
		}
		return parsed
	}

	private fun parseBenchmarkNonNegativeInt(option: String, value: String): Int {
		val parsed = value.toIntOrNull()
		if (parsed == null || parsed < 0) {
			benchmarkError("Option $option requires a non-negative integer.")
		}
		return parsed
	}

	private fun parseBenchmarkFormat(option: String, value: String): BenchmarkFormat {
		return when (value.lowercase()) {
			"human" -> BenchmarkFormat.HUMAN
			"json" -> BenchmarkFormat.JSON
			else -> {
				benchmarkError("Option $option accepts only human or json.")
			}
		}
	}

	private fun benchmarkError(message: String): Nothing {
		log.error("❌ $message")
		ExitHandler.exit(1)
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
