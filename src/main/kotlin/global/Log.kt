package global

import file.SetupApp
import ui.MainWindow
import javax.swing.SwingUtilities

object Log {
    fun print(text: String) {
        try {
            if (!SetupApp.setup.silent) {
                if (SwingUtilities.isEventDispatchThread()) {
					append(text)
				} else {
                    SwingUtilities.invokeAndWait {
						append(text)
                    }
                }
            }
        } catch (_e: UninitializedPropertyAccessException) {
        }
    }

	private fun append(text: String) {
		MainWindow.ui.jTextArea.append(text)
		MainWindow.ui.jTextArea.caretPosition = MainWindow.ui.jTextArea.text?.length?.minus(1) ?: 0
	}

	fun println(text: String) {
        print(text + "\n")
    }
}
