package file

import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

class SetupMain {
    @Option(name = "-silent", usage = "execute tasks without opening UI")
    var silent = false

    @Option(name = "-force", usage = "force tasks without asking for user confirmation")
    var force = false

    @Option(name = "-remove", usage = "removes wurstscript from your machine")
    var remove = false

    @Option(name = "-update", usage = "updates your current wurst installation or project if projectDir present")
    var update = false

    @Option(name = "-generate", usage = "generates a new project at projectDir location")
    var generate = false

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
        val parser = CmdLineParser(this)
        try {
            parser.parseArgument(args.asList())
        } catch (e: CmdLineException) {
            // handling of wrong arguments
            System.err.println(e.message)
            parser.printUsage(System.err)
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




