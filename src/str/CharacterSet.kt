package str

import math.qLog2

/**
 * <p>
 * === CharacterSet ===
 * </p><p>
 * Date : December 10, 2018
 * </p><p>
 * TODO Description here
 * </p>
 * @author Michael van Dyk
 */

class CharacterSet(private val characters: Array<Char>) {
    val minBits = qLog2(characters.size) + 1
    val bitMask = (1 shl minBits) - 1

    operator fun get(index: Int) = characters[index]
}