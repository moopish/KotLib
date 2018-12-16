package math

/**
 * <p>
 * === Quick ===
 * </p><p>
 * Date : December 10, 2018
 * </p><p>
 * TODO Description here
 * </p>
 * @author Michael van Dyk
 */


/**
 * Treats n as unsigned. O(b) time where d is the number of bits.
 * b for this is 32 as an Int in Kotlin is 32 bits
 * TODO fix
 * @return the highest power of 2 < N
 */
fun qLog2(n: Int): Int {
    var pos = 1 shl 31
    for (log in 31 downTo 1)
        if (n and pos != 0) return (log)
        else pos = pos ushr 1
    return (0)
}

/**
 * Treats n as unsigned. O(b) time where d is the number of bits.
 * b for this is 64 as an Long in Kotlin is 64 bits
 * TODO fix
 * @return the highest power of 2 < N
 */
fun qLog2(n: Long): Int {
    var pos = 1L shl 63
    for (log in 63 downTo 1)
        if (n and pos != 0L) return (log)
        else pos = pos ushr 1
    return (0)
}