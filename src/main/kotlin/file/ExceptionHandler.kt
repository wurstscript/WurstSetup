package file

import java.io.PrintWriter
import java.io.StringWriter
import javax.swing.JOptionPane
import javax.swing.JTextArea
import javax.swing.UIManager

object ExceptionHandler {

    fun setupExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler { _, exception ->
            exception.printStackTrace()
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
            } catch (e: Exception) {
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
