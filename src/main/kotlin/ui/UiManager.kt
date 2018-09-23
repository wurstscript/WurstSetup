package ui

import file.SetupApp
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.SwingUtilities
import javax.swing.UIManager

object UiManager {

    fun initUI() {
        SwingUtilities.invokeLater {
            MainWindow.init()
            try {
                val img = javaClass.getResourceAsStream("/icon.png")
                if (img != null) {
                    MainWindow.iconImage = ImageIO.read(img)
                }
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
