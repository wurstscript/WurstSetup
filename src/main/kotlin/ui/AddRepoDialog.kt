package ui

import tablelayout.Table
import workers.DependencyVerifierWorker
import java.awt.Color
import java.awt.Component
import java.awt.GridBagLayout
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.JDialog
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class AddRepoDialog : JDialog() {

    private val contentPane: JPanel = JPanel(GridBagLayout())
    private val contentTable = Table()

    private var addButton: SetupButton = SetupButton("Add")
    private var cancelButton: SetupButton = SetupButton("Cancel")

    init {
        title = "Add git dependency"
        contentTable.setSize(340, 120)
        setSize(340, 120)

        setContentPane(contentPane)
        contentPane.add(contentTable)
        modalityType = ModalityType.APPLICATION_MODAL
        try {
            setIconImage(ImageIO.read(AddRepoDialog::class.java.getResourceAsStream("/icon.png")))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        uiLayout("Enter a custom git repository link")
        uiStyle()
        getRootPane().defaultButton = addButton

        cancelButton.addActionListener {
            isVisible = false
        }

        addButton.addActionListener {
            DependencyVerifierWorker(jTextField.text).execute()
            isVisible = false
        }

        setLocationRelativeTo(null)
        isAlwaysOnTop = true

        isVisible = true
    }

    private lateinit var jTextField: JTextField

    private fun uiLayout(message: String) {
        val welcomeLabel = JLabel("<html><div style='text-align: center;'>$message</div></html>")
        welcomeLabel.alignmentX = Component.CENTER_ALIGNMENT
        welcomeLabel.foreground = Color.WHITE
        contentTable.top()
        contentTable.addCell(welcomeLabel).width(300f).top().pad(-2f, 5f, 5f, 5f)
        contentTable.row()
        jTextField = JTextField()
        contentTable.addCell(jTextField).width(300f).top().pad(-2f, 5f, 5f, 5f)
        contentTable.row()

        val buttonTable = Table()
        buttonTable.addCell(addButton).pad(0f, 6f, 0f, 6f)
        buttonTable.addCell(cancelButton).pad(0f, 6f, 0f, 6f)

        contentTable.addCell(buttonTable).growX().padTop(6f)
    }

    private fun uiStyle() {
        contentPane.background = Color(36, 36, 36)

        UiStyle.setStyle(contentTable)

    }

}