package global

import file.SetupApp
import ui.MainWindow
import javax.swing.SwingUtilities

object Log {
    fun print(text: String) {
        try {
            if (!SetupApp.setup.silent) {
                if (SwingUtilities.isEventDispatchThread()) {
                    SwingUtilities.invokeAndWait {
                        MainWindow.ui.jTextArea.append(text)
                        MainWindow.ui.jTextArea.caretPosition = MainWindow.ui.jTextArea.text!!.length - 1
                    }
                } else {
                    MainWindow.ui.jTextArea.append(text)
                    MainWindow.ui.jTextArea.caretPosition = MainWindow.ui.jTextArea.text!!.length - 1
                }
            }
        } catch (_e: UninitializedPropertyAccessException) {
        }
    }

    fun println(text: String) {
        print(text + "\n")
    }
}