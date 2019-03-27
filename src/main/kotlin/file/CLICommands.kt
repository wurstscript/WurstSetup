package file

enum class CLICommands {
	INSTALL,
	REMOVE,
	UPDATE,
	GENERATE,
	BUILD
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
	};

	abstract fun runOption(setupMain: SetupMain, args: List<String>)
}
