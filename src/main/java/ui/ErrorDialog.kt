package ui

import tablelayout.Table
import java.awt.Color
import java.awt.Component
import java.awt.Dialog
import java.awt.GridBagLayout
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.JDialog
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.WindowConstants


class ErrorDialog(message: String, forceClose: Boolean) : JDialog() {

    private val contentPane: JPanel
    private val contentTable = Table()

    private var buttonOk: SetupButton? = null

    init {
        contentPane = JPanel(GridBagLayout())
        title = "Warning"
        contentTable.setSize(340, 120)
        setSize(340, 120)

        setContentPane(contentPane)
        contentPane.add(contentTable)
        modalityType = Dialog.ModalityType.APPLICATION_MODAL
        try {
            setIconImage(ImageIO.read(ErrorDialog::class.java.getResourceAsStream("/icon.png")))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        uiLayout(message)
        uiStyle()
        getRootPane().defaultButton = buttonOk

        buttonOk!!.addActionListener { e ->
            if (forceClose) {
                System.exit(0)
            } else {
                dispose()
            }
        }

        setLocationRelativeTo(null)
        if (forceClose) {
            defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
            isAlwaysOnTop = true
        }

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
        buttonOk = SetupButton("Exit")
        buttonTable.addCell(buttonOk).pad(0f, 6f, 0f, 6f)

        contentTable.addCell(buttonTable).growX().padTop(6f)

    }

    private fun uiStyle() {
        contentPane.background = Color(36, 36, 36)

        UiStyle.setStyle(contentTable)

    }

}