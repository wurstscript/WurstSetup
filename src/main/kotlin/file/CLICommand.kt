package file

import config.ScriptMode
import config.Wc3Patch

enum class CLICommand {
    HELP,
	INSTALL,
	REMOVE,
	GENERATE,
	TEST,
    TYPECHECK,
    OUTDATED,
    BUILD,
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
            setupMain.wc3Patch = when (args[0].lowercase()) {
                "pre1.29" -> Wc3Patch.PRE_129
                else -> Wc3Patch.REFORGED
            }
        }
    };

	abstract fun runOption(setupMain: SetupMain, args: List<String>)
}
