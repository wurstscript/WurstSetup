package file

import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option
import java.io.File
import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter
import javax.swing.JOptionPane
import javax.swing.JTextArea
import javax.swing.UIManager
import javax.swing.UnsupportedLookAndFeelException

class SetupMain {

    @Option(name = "-silent", usage = "check for updates without opening UI")
    var silent = false

    @Option(name = "-checkWurstUpdate", usage = "check for updates without opening UI")
    var checkWurstUpdate = false

    private var projectDir: File? = null

    @Option(name = "-projectdir", usage = "sets the project dir to check dependencies for")
    fun setDir(dir: File) {
        if (dir.exists()) {
            projectDir = dir
        }
    }

    @Throws(CmdLineException::class)
    fun doMain(args: Array<String>) {
        setupExceptionHandler()
        val parser = CmdLineParser(this)
        try {
            parser.parseArgument(*args)
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

        private fun setupExceptionHandler() {
            Thread.setDefaultUncaughtExceptionHandler { thread, exception ->
                exception.printStackTrace()
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                } catch (e: InstantiationException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: UnsupportedLookAndFeelException) {
                    e.printStackTrace()
                }

                val sw = StringWriter()
                val pw = PrintWriter(sw)
                exception.printStackTrace(pw)
                val jTextField = JTextArea()
                jTextField.text = "Please report this crash with the following info:\nVersion: " + CompileTimeInfo.version + "\n" + sw.toString()
                jTextField.isEditable = false
                JOptionPane.showMessageDialog(null, jTextField, "Sorry, Exception occured :(", JOptionPane.ERROR_MESSAGE)
            }
        }
    }
}


