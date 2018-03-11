package me.shafran.actiontracker

class AssertFailsException(message: String) : RuntimeException(message)

inline fun <reified T : Throwable> assertFailsWith(block: () -> Unit) {
    try {
        block()

        throw AssertFailsException("Code completed successfully, but expected ${T::class}")
    } catch (e: Throwable) {
        if (e !is T) {
            throw AssertFailsException("Code threw $e, but excepted ${T::class}")
        }
    }
}
