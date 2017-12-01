package ui

import sun.swing.FilePane
import java.awt.Container
import javax.swing.JFileChooser
import javax.swing.LookAndFeel
import javax.swing.UIManager
import javax.swing.UnsupportedLookAndFeelException

class JSystemFileChooser : JFileChooser() {
    override fun updateUI() {
        var old: LookAndFeel? = UIManager.getLookAndFeel()
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        } catch (ex: Throwable) {
            old = null
        }

        super.updateUI()

        if (old != null) {
            val filePane = findFilePane(this)
            if (filePane != null) {
                filePane.viewType = FilePane.VIEWTYPE_DETAILS
                filePane.viewType = FilePane.VIEWTYPE_LIST
            }

            val background = UIManager.getColor("Label.background")
            setBackground(background)
            isOpaque = true

            try {
                UIManager.setLookAndFeel(old)
            } catch (ignored: UnsupportedLookAndFeelException) {
            }
            // shouldn't get here
        }
    }


    private fun findFilePane(parent: Container): FilePane? {
        for (comp in parent.components) {
            if (FilePane::class.java.isInstance(comp)) {
                return comp as FilePane
            }
            if (comp is Container) {
                if (comp.componentCount > 0) {
                    val found = findFilePane(comp)
                    if (found != null) {
                        return found
                    }
                }
            }
        }

        return null
    }
}