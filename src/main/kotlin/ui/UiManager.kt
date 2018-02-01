package ui

import file.SetupApp
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.SwingUtilities
import javax.swing.UIManager

object UiManager {

    fun initUI() {
        SwingUtilities.invokeLater({
            MainWindow.init()
            try {
                MainWindow.iconImage = ImageIO.read(UIManager::class.java.getResourceAsStream("/icon.png"))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        })
    }

    fun refreshComponents(verify: Boolean) {
        if (!SetupApp.setup.silent) {
            MainWindow.ui.refreshComponents(verify)
        }
    }

}
