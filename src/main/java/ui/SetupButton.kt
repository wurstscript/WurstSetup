package ui

import java.awt.Color
import java.awt.Graphics
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.border.CompoundBorder
import javax.swing.border.EmptyBorder

class SetupButton(buttonTag: String) : JButton(buttonTag) {
    private val textColor = Color.WHITE
    private val backgroundColor = Color(18, 18, 18)
    private val overColor = Color(120, 20, 20)
    private val pressedColor = Color(240, 40, 40)

    init {
        background = backgroundColor
        foreground = textColor
        isContentAreaFilled = false
        isFocusPainted = false

        val line = BorderFactory.createLineBorder(Color(80, 80, 80))
        val empty = EmptyBorder(4, 4, 4, 4)
        val border = CompoundBorder(line, empty)
        setBorder(border)
    }

    override fun paintComponent(g: Graphics) {
        if (getModel().isPressed) {
            g.color = pressedColor
        } else if (getModel().isRollover) {
            g.color = overColor
        } else {
            g.color = background
        }
        g.fillRect(0, 0, width, height)
        super.paintComponent(g)
    }
}