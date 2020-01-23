package file

enum class CLICommand {
    HELP,
	INSTALL,
	REMOVE,
	GENERATE,
	TEST,
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
    };

	abstract fun runOption(setupMain: SetupMain, args: List<String>)
}
