package ext

/**
 * <p>
 * === RangeExtensions ===
 * </p><p>
 * Date : December 10, 2018
 * </p><p>
 * TODO Description here
 * </p>
 * @author Michael van Dyk
 */

operator fun IntRange.contains(range: IntRange) = range.first in this && range.last in this
fun IntRange.overlaps(range: IntRange) = this.first in range || this.last in range || range in this
operator fun IntRange.compareTo(other: IntRange): Int {
    val check = first.compareTo(other.first)
    return if (check == 0) last.compareTo(other.last)
    else check
}

operator fun LongRange.contains(range: IntRange) = range.first in this && range.last in this
operator fun LongRange.contains(range: LongRange) = range.first in this && range.last in this