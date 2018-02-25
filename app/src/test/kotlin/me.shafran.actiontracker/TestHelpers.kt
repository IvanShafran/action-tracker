package me.shafran.actiontracker

/**
 * Returns second element.
 * @throws [NoSuchElementException] if the list size less than two.
 */
fun <T> List<T>.second(): T {
    if (size < 2)
        throw NoSuchElementException("List size less than two.")
    return this[1]
}
