package old

import kotlin.math.absoluteValue
import kotlin.math.min

/**
 * <p>
 * === NumberUtils ===
 * </p><p>
 * Date : December 04, 2018
 * </p><p>
 * TODO Description here
 * </p>
 * @author Michael van Dyk
 */

/**
 * Gives the binary representation of a given number.
 */
fun toBinaryString(num: Int, leading: Boolean = false): String {
    var found1 = false
    val stringBuilder = StringBuilder()

    for (i in 31 downTo 0) {
        val shift = (1 shl  i)
        if (num and shift == shift) {
            found1 = true
            stringBuilder.append('1')
        } else {
            if (leading || found1) stringBuilder.append('0')
        }
    }

    return stringBuilder.toString()
}

fun toHexCharacter(num: Int): Char {
    if (num !in 0..15) return '?'
    return when (num) {
        in 0..9 -> '0' + num
        else -> 'A' + (num - 10)
    }
}

fun toHexString(num: Int, leading: Boolean = false): String {
    var found1 = false
    val stringBuilder = StringBuilder()

    for (i in 3 downTo 0) {
        val currentByte = (num and (15 shl (i shl 2))) ushr (i shl 2)
        val currentChar = toHexCharacter(currentByte)
        if (!found1) found1 = currentChar != '0'
        if (leading || found1) stringBuilder.append(currentChar)
    }

    return stringBuilder.toString()
}

/**
 Reverse the digits of a base 'b' integer.
  */
fun reverseDigits(num: Int, base: Int): Int {
    var ret = 0
    var working = num

    while (working != 0) {
        ret *= base
        ret += working.rem(base)
        working /= base
    }

    return (ret)
}

fun gcd(a: Int, b: Int) = gcd(a.toLong(), b.toLong()).toInt()
fun gcd(a: Long, b: Long): Long {
    var an = a.absoluteValue
    var bn = b.absoluteValue
    var d = 0
    while (an and 1L == 0L && bn and 1L == 0L) {
        an = an ushr 1
        bn = bn ushr 1
        ++d
    }
    while (an != bn) {
        when {
            an and 1 == 0L -> an = an ushr 1
            bn and 1 == 0L -> bn = bn ushr 1
            an > bn -> an = (an - bn) ushr 1
            else -> bn = (bn - an) ushr 1
        }
    }
    return (an shl d)
}

fun lcm(a: Int, b: Int) = lcm(a.toLong(), b.toLong()).toInt()
fun lcm(a: Long, b: Long): Long = a.absoluteValue / gcd(a, b) * b.absoluteValue

fun sumOfNaturalNumbers(n: Int) = n * (n + 1) / 2
fun sumOfNaturalNumbers(n: Long) = n * (n + 1) / 2
fun sumOfSquaredNaturalNumbers(n: Int) = n * (n + 1) * (2 * n + 1) / 6
fun sumOfSquaredNaturalNumbers(n: Long) = n * (n + 1) * (2 * n + 1) / 6

fun sumMultiples(num: Int, limit: Int) = num * sumOfNaturalNumbers(limit / num)
fun sumMultiples(num: Long, limit: Long) = num * sumOfNaturalNumbers(limit / num)

infix fun Long.choose(k: Long): Long {
    return if (k == 0L) 1L
    else (this * (this - 1).choose(k - 1)) / k
}

inline fun sum(range: IntRange, toSum: (Int) -> Int): Int {
    var sum = 0
    for (i in range) sum += toSum(i)
    return (sum)
}

inline fun sum(range: LongRange, toSum: (Long) -> Long): Long {
    var sum = 0L
    for (i in range) sum += toSum(i)
    return (sum)
}

inline fun product(range: IntRange, toMultiply: (Int) -> Int): Int {
    var prod = 1
    for (i in range) prod *= toMultiply(i)
    return (prod)
}

inline fun product(range: LongRange, toMultiply: (Long) -> Long): Long {
    var prod = 1L
    for (i in range) prod *= toMultiply(i)
    return (prod)
}

fun chooseIter(n: Long, k: Long): Long {
    val min = min(k, n - k)
    return product(1..min) { i -> (n + 1 - i) } / product(1..min) { i -> i }
}

/**
 * Sum of divisors not including n
 */
fun sumOfDivisors(n: Long): Long {
    if (n < 2) return 0L
    var sum = 1L
    val root = Math.sqrt(n.toDouble()).toLong()
    val square =
            if (root * root == n) {
                sum += root
                true
            } else false
    for (d in 2..(root - (if (square) 1L else 0L))) {
        if (n.rem(d) == 0L)
            sum += d + n/d
    }
    return (sum)
}