package ui

import file.SetupApp
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.SwingUtilities

object UiManager {

    fun initUI() {
        SwingUtilities.invokeLater {
            MainWindow.init()
            try {
                MainWindow.iconImage = ImageIO.read(javaClass.classLoader.getResource("icon.png"))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun refreshComponents() {
        if (!SetupApp.setup.silent) {
            MainWindow.ui.refreshComponents()
        }
    }

}
