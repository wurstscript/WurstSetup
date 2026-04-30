package file

import kotlin.system.exitProcess

object ExitHandler {
    var handler: (Int) -> Nothing = { exitProcess(it) }
    fun exit(code: Int): Nothing = handler(code)
}
