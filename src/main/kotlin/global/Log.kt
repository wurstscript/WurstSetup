package global

import ui.MainWindow
import javax.swing.SwingUtilities

object Log {
    fun print(text: String) {
        SwingUtilities.invokeLater({
            MainWindow.ui.jTextArea.append(text)
            MainWindow.ui.jTextArea.caretPosition = MainWindow.ui.jTextArea.text!!.length - 1
        })
    }

    fun println(text: String) {
        print(text + "\n")
    }
}