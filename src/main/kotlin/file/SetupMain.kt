package file

import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

class SetupMain {
    var isGUILaunch = false

    @Option(name = "--req-confirm", usage = "requires confirmation by the user before executing tasks")
    var requireConfirmation = false

	@Option(name = "-install", usage = "Install wurst on this system or add a depenency to your project")
	var install = "%unset%"

	@Option(name = "-remove", usage = "removes wurstscript from your machine")
	var remove = "%unset%"

	@Option(name = "-update", usage = "updates the current project, or wurst installation if ")
	var update = "%unset%"

    @Option(name = "-generate", usage = "generates a new project at projectDir location")
    var generate = "%unset%"

	var projectDir: Path = SetupApp.DEFAULT_DIR

	@Option(name = "-projectDir", usage = "sets the root folder of the wurst project")
	fun setDir(file: File) {
		val dir = file.toPath()
		Files.createDirectories(dir)
		if (Files.exists(dir)) {
			projectDir = dir
		}
	}

    @Throws(CmdLineException::class)
    fun doMain(args: Array<String>) {
        ExceptionHandler.setupExceptionHandler()
		val argsList = args.asList()
		if (argsList.isEmpty()) {
			isGUILaunch = true
		} else {
			val parser = CmdLineParser(this)
			try {
				parser.parseArgument(argsList)
			} catch (e: CmdLineException) {
				// handling of wrong arguments
				System.err.println(e.message)
				parser.printUsage(System.err)
			}
		}
        SetupApp.handleArgs(this)
    }

    companion object {
        @Throws(IOException::class, CmdLineException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            SetupMain().doMain(args)
        }
    }
}




