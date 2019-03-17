package ui

import tablelayout.Table
import java.awt.Color
import java.awt.Component
import java.awt.GridBagLayout
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.JDialog
import javax.swing.JLabel
import javax.swing.JPanel

class UpdateFoundDialog(message: String) : JDialog() {

    private val contentPane: JPanel = JPanel(GridBagLayout())
    private val contentTable = Table()

    private val buttonUpdate = SetupButton("Update")
    private val buttonSnooze = SetupButton("Later")
    private val buttonDeny = SetupButton("Close")

    init {
        title = "Notification"
        contentTable.setSize(340, 120)
        setSize(340, 120)

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
        getRootPane().defaultButton = buttonUpdate

        buttonDeny.addActionListener { e ->
            System.exit(0)
        }

        buttonSnooze.addActionListener { e ->
            System.exit(0)
        }

        buttonUpdate.addActionListener { e ->
            UiManager.initUI()
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
        buttonTable.addCell(buttonUpdate).pad(0f, 6f, 0f, 6f)
        buttonTable.addCell(buttonSnooze).pad(0f, 6f, 0f, 6f)
        buttonTable.addCell(buttonDeny).pad(0f, 6f, 0f, 6f)

        contentTable.addCell(buttonTable).growX().padTop(6f)

    }

    private fun uiStyle() {
        contentPane.background = Color(36, 36, 36)

        UiStyle.setStyle(contentTable)

    }

}