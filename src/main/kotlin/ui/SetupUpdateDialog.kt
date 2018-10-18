package ui

import file.Download
import global.InstallationManager
import tablelayout.Table
import java.awt.Color
import java.awt.Component
import java.awt.Desktop
import java.awt.GridBagLayout
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import javax.imageio.ImageIO
import javax.swing.JDialog
import javax.swing.JLabel
import javax.swing.JPanel


class SetupUpdateDialog(message: String) : JDialog() {

    private val contentPane: JPanel = JPanel(GridBagLayout())
    private val contentTable = Table()

    private val buttonVisit = SetupButton("Open Website")
    private val buttonSnooze = SetupButton("Download Now")
    private val buttonDeny = SetupButton("Close")

    init {
        title = "Notification"
        contentTable.setSize(340, 140)
        setSize(340, 140)

        setContentPane(contentPane)
        contentPane.add(contentTable)
        modalityType = ModalityType.APPLICATION_MODAL
        try {
            setIconImage(ImageIO.read(javaClass.classLoader.getResource("icon.png")))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        uiLayout(message)
        uiStyle()
        getRootPane().defaultButton = buttonVisit

        buttonDeny.addActionListener {
            dispose()
            UiManager.initUI()
        }

        buttonSnooze.addActionListener { _ ->
            val url = InstallationManager::class.java.protectionDomain.codeSource.location
            val ownFile = Paths.get(url.toURI())
            if (ownFile != null && Files.exists(ownFile) && ownFile.toString().endsWith(".jar")) {
                Files.move(ownFile, ownFile.resolveSibling(ownFile.fileName.toString() + "_old"))
            }
            Download.downloadSetup {
                Files.move(it, ownFile)
                Runtime.getRuntime().exec("java -jar " + ownFile.fileName.toAbsolutePath())
                dispose()
            }
        }

        buttonVisit.addActionListener {
            openWebpage(URL("https://wurstlang.org/"))
            System.exit(0)
        }

        setLocationRelativeTo(null)
        isAlwaysOnTop = true

        isVisible = true
    }

    private fun uiLayout(message: String) {
        val welcomeLabel = JLabel("<html><div style='text-align: center;'>$message</div></html>")
        welcomeLabel.alignmentX = Component.CENTER_ALIGNMENT
        welcomeLabel.foreground = Color.WHITE
        contentTable.top()
        contentTable.addCell(welcomeLabel).width(300f).top().pad(-2f, 5f, 5f, 5f)
        contentTable.row()

        val buttonTable = Table()
        buttonTable.addCell(buttonVisit).pad(0f, 6f, 0f, 6f)
        buttonTable.addCell(buttonSnooze).pad(0f, 6f, 0f, 6f)
        buttonTable.addCell(buttonDeny).pad(0f, 6f, 0f, 6f)

        contentTable.addCell(buttonTable).growX().padTop(6f)
    }

    private fun uiStyle() {
        contentPane.background = Color(36, 36, 36)

        UiStyle.setStyle(contentTable)

    }

    private fun openWebpage(uri: URI) {
        val desktop = if (Desktop.isDesktopSupported()) Desktop.getDesktop() else null
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun openWebpage(url: URL) {
        try {
            openWebpage(url.toURI())
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

    }

}