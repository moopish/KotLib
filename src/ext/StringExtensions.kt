package ext

/**
 * <p>
 * === StringExtensions ===
 * </p><p>
 * Date : December 10, 2018
 * </p><p>
 * TODO Description here
 * </p>
 * @author Michael van Dyk
 */

/**
 *
 */
operator fun String.get(startIndex: Int, endIndex: Int) = this.substring(startIndex, endIndex)
operator fun String.get(range: IntRange) = this.substring(range)

fun String.isPalindrome(): Boolean {
    for (i in 0 until length/2)
        if (this[i] != this[length - 1 - i])
            return false
    return true
}

fun String.range() = 0 until this.length

fun String.swap(a: Int, b: Int): String {
    return when {
        a !in range() -> throw IndexOutOfBoundsException("$a is out of bounds.")
        b !in range() -> throw IndexOutOfBoundsException("$b is out of bounds.")
        a == b -> this
        a > b -> swap(b, a)
        else -> "${this[0, a]}${this[b]}${this[a + 1, b]}${this[a]}${this[b + 1, length]}"
    }
}

fun String.swap(
        lowA: Int, highA: Int,
        lowB: Int, highB: Int,
        inclusive: Boolean = false
): String {
    return when {
        lowA > highA -> swap(highA, lowA, lowB, highB, inclusive)
        lowB > highB -> swap(lowA, highA, highB, lowB, inclusive)
        inclusive -> swap(lowA..highA, lowB..highB)
        else -> swap(lowA until highA, lowB until highB)
    }
}

fun String.swap(rangeA: IntRange, rangeB: IntRange): String {
    return when {
        rangeA > rangeB -> swap(rangeB, rangeA)
        rangeA !in range() -> throw IndexOutOfBoundsException()
        rangeB !in range() -> throw IndexOutOfBoundsException()
        rangeA.overlaps(rangeB) -> throw IndexOutOfBoundsException()
        else -> "${this[0, rangeA.first]}${this[rangeB]}" +
                this[rangeA.last + 1, rangeB.first] +
                "${this[rangeA]}${this[rangeB.last + 1, length]}"
    }
}
