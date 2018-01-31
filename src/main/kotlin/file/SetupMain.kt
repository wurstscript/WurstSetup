package file

import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

class SetupMain {

    @Option(name = "-silent", usage = "check for updates without opening UI")
    var silent = false

    @Option(name = "-force", usage = "force updates the installation and/or project without asking")
    var force = false

    @Option(name = "-removeInstallation", usage = "removes wurstscript from your machine")
    var removeInstallation = false

    @Option(name = "-updateInstallation", usage = "updates your current wurst installation")
    var updateInstall = false

    @Option(name = "-createProject", usage = "creates a project at the projectDir location")
    var createProject = false

    @Option(name = "-updateProject", usage = "updates the project at the projectDir location")
    var updateProject = false

    var projectDir: Path? = null

    @Option(name = "-projectDir", usage = "sets the project dir to check dependencies for")
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

}

@Throws(IOException::class, CmdLineException::class)
fun main(args: Array<String>) {
    SetupMain().doMain(args)
}


