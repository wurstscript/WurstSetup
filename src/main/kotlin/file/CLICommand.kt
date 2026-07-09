package file

import config.ScriptMode
import logging.KotlinLogging

enum class CLICommand {
    HELP,
	INSTALL,
	REMOVE,
	GENERATE,
	TEST,
    TYPECHECK,
    OUTDATED,
    BUILD,
    EXPORTOBJECTS,
    SELF_UPDATE
}

enum class GlobalOptions(val optionName: String = "", val argCount: Int = 0) {
	REQ_CONFIRM("--request-confirmation") {
		override fun runOption(setupMain: SetupMain, args: List<String>) {
			setupMain.requireConfirmation = true
		}
	},
	PROJECT_DIR("-projectDir", 1) {
		override fun runOption(setupMain: SetupMain, args: List<String>) {
			setupMain.setProjectDir(SetupApp.DEFAULT_DIR.resolve(args[0]))
		}
	},
    NO_PJASS("--noPJass") {
        override fun runOption(setupMain: SetupMain, args: List<String>) {
            setupMain.noPJass = true
        }
    },
    MEASURE("--measure") {
        override fun runOption(setupMain: SetupMain, args: List<String>) {
            setupMain.measure = true
        }
    },
    DEV_BUILD("--dev") {
        override fun runOption(setupMain: SetupMain, args: List<String>) {
            setupMain.devBuild = true
        }
    },
    WITH_AGENTS("--with-agents") {
        override fun runOption(setupMain: SetupMain, args: List<String>) {
            setupMain.addAgents = true
        }
    },
    NO_AGENTS("--no-agents") {
        override fun runOption(setupMain: SetupMain, args: List<String>) {
            setupMain.addAgents = false
        }
    },
    WITH_CI("--with-ci") {
        override fun runOption(setupMain: SetupMain, args: List<String>) {
            setupMain.addGithubWorkflow = true
        }
    },
    NO_CI("--no-ci") {
        override fun runOption(setupMain: SetupMain, args: List<String>) {
            setupMain.addGithubWorkflow = false
        }
    },
    QUIET("--quiet") {
        override fun runOption(setupMain: SetupMain, args: List<String>) {
            setupMain.quiet = true
        }
    },
    DEBUG("--debug") {
        override fun runOption(setupMain: SetupMain, args: List<String>) {
            setupMain.debug = true
        }
    },
    SCRIPT_MODE("--script-mode", 1) {
        override fun runOption(setupMain: SetupMain, args: List<String>) {
            setupMain.scriptMode = when (args[0].lowercase()) {
                "jass" -> ScriptMode.JASS
                else -> ScriptMode.LUA
            }
        }
    },
    WC3_PATCH("--wc3-patch", 1) {
        override fun runOption(setupMain: SetupMain, args: List<String>) {
            setupMain.wc3Patch = CoreJassProvider.normalizePatchInput(args[0])
        }
    },
    WC3_PATH("--wc3-path", 1) {
        override fun runOption(setupMain: SetupMain, args: List<String>) {
            setupMain.gamePath = java.nio.file.Paths.get(args[0])
            setupMain.gamePathExplicit = true
        }
    },
    WITH_DEP("--with-dep", 1) {
        override fun runOption(setupMain: SetupMain, args: List<String>) {
            val requested = args[0].trim()
            val curated = CuratedDependencies.findById(requested)
            if (curated == null) {
                log.error("❌ Unknown curated dependency: $requested")
                log.info("Available: ${CuratedDependencies.ids.joinToString(", ")}")
                ExitHandler.exit(1)
            }
            if (!setupMain.curatedDependencyIds.contains(curated.id)) {
                setupMain.curatedDependencyIds.add(curated.id)
            }
        }
    };

	abstract fun runOption(setupMain: SetupMain, args: List<String>)

	companion object {
		private val log = KotlinLogging.logger {}
	}
}
