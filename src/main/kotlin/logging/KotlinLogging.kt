package logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object KotlinLogging {
    fun logger(initializer: () -> Unit = {}): Logger {
        initializer()
        return LoggerFactory.getLogger(resolveCaller())
    }

    private fun resolveCaller(): Class<*> {
        return STACK_WALKER.walk { frames ->
            frames
                .map { it.declaringClass }
                .filter { it != KotlinLogging::class.java }
                .findFirst()
                .orElse(KotlinLogging::class.java)
        }
    }

    private val STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
}
