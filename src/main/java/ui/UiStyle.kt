package ui

import tablelayout.Table
import java.awt.Color
import javax.swing.BorderFactory
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.border.CompoundBorder
import javax.swing.border.EmptyBorder



object UiStyle {

    fun setStyle(table: Table) {
        for (i in 0 until table.components.size) {
            val component = table.components[i]
            if (component is JTextField) {
                component.setBackground(Color(46, 46, 46))
                component.setForeground(Color(255, 255, 255))
                val line = BorderFactory.createEtchedBorder()
                val pad = EmptyBorder(0, 5, 0, 0)
                val compoundBorder = CompoundBorder(line, pad)
                (component as JComponent).border = compoundBorder
            } else if (component is Table) {
                setStyle(component)
            }
            (component as? JLabel)?.foreground = Color.WHITE
        }
    }

}
