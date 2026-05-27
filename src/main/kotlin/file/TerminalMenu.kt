package file

import org.jline.keymap.BindingReader
import org.jline.keymap.KeyMap
import org.jline.terminal.Terminal
import org.jline.terminal.TerminalBuilder
import org.jline.utils.InfoCmp
import kotlin.math.max
import kotlin.math.min

object TerminalMenu {
    data class Choice<T>(val value: T, val label: String)

    private const val UP = "up"
    private const val DOWN = "down"
    private const val LEFT = "left"
    private const val RIGHT = "right"
    private const val ENTER = "enter"
    private const val CANCEL = "cancel"

    fun canUseInteractive(): Boolean {
        return System.console() != null
    }

    fun <T> choose(title: String, choices: List<Choice<T>>, defaultIndex: Int = 0): T? {
        if (!canUseInteractive() || choices.isEmpty()) {
            return null
        }

        try {
            TerminalBuilder.builder().system(true).dumb(false).build().use { terminal ->
                val oldAttributes = terminal.enterRawMode()
                val reader = BindingReader(terminal.reader())
                val keyMap = menuKeyMap(terminal)
                val writer = terminal.writer()
                var selected = defaultIndex.coerceIn(0, choices.lastIndex)
                var firstVisible = 0
                var result: T? = null
                var done = false

                fun render() {
                    val visibleRows = max(3, min(12, terminal.height - 5))
                    if (selected < firstVisible) {
                        firstVisible = selected
                    } else if (selected >= firstVisible + visibleRows) {
                        firstVisible = selected - visibleRows + 1
                    }

                    clearScreen(terminal)
                    writer.print("\u001B[?25l")
                    writer.println(title)
                    writer.println("Use Up/Down, Enter to select, Esc to cancel.")
                    val end = min(choices.size, firstVisible + visibleRows)
                    for (index in firstVisible until end) {
                        val marker = if (index == selected) "> " else "  "
                        writer.println(marker + choices[index].label)
                    }
                    if (firstVisible > 0 || end < choices.size) {
                        writer.println("  ${firstVisible + 1}-$end of ${choices.size}")
                    }
                    writer.flush()
                }

                try {
                    while (!done) {
                        render()
                        when (reader.readBinding(keyMap)) {
                            ENTER -> {
                                result = choices[selected].value
                                done = true
                            }
                            UP -> selected = if (selected == 0) choices.lastIndex else selected - 1
                            DOWN -> selected = if (selected == choices.lastIndex) 0 else selected + 1
                            LEFT -> selected = max(0, selected - 10)
                            RIGHT -> selected = min(choices.lastIndex, selected + 10)
                            CANCEL -> done = true
                            else -> Unit
                        }
                    }
                } finally {
                    terminal.attributes = oldAttributes
                    clearScreen(terminal)
                    writer.print("\u001B[?25h")
                    writer.flush()
                }
                return result
            }
        } catch (_: Exception) {
            return null
        }
    }

    private fun menuKeyMap(terminal: Terminal): KeyMap<String> {
        val keyMap = KeyMap<String>()
        keyMap.bindKey(UP, KeyMap.key(terminal, InfoCmp.Capability.key_up))
        keyMap.bindKey(DOWN, KeyMap.key(terminal, InfoCmp.Capability.key_down))
        keyMap.bindKey(LEFT, KeyMap.key(terminal, InfoCmp.Capability.key_left))
        keyMap.bindKey(RIGHT, KeyMap.key(terminal, InfoCmp.Capability.key_right))
        keyMap.bind(UP, "\u001B[A", "k", "w")
        keyMap.bind(DOWN, "\u001B[B", "j", "s")
        keyMap.bind(LEFT, "\u001B[D", "\u001B[5~", "h", "a")
        keyMap.bind(RIGHT, "\u001B[C", "\u001B[6~", "l", "d")
        keyMap.bind(ENTER, "\r", "\n")
        keyMap.bindKey(ENTER, KeyMap.key(terminal, InfoCmp.Capability.key_enter))
        keyMap.bind(CANCEL, "\u001B", "q", KeyMap.ctrl('C'))
        return keyMap
    }

    private fun clearScreen(terminal: Terminal) {
        val cleared = terminal.puts(InfoCmp.Capability.clear_screen)
        val homed = terminal.puts(InfoCmp.Capability.cursor_home)
        if (!cleared || !homed) {
            terminal.writer().print("\u001B[2J\u001B[H")
        }
    }

    private fun KeyMap<String>.bindKey(action: String, sequence: String?) {
        if (!sequence.isNullOrEmpty()) {
            bind(action, sequence)
        }
    }
}
