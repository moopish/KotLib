package str

import bit.BitArray

/**
 * <p>
 * === CompressedString ===
 * </p><p>
 * Date : December 10, 2018
 * </p><p>
 * TODO Description here
 * </p>
 * @author Michael van Dyk
 */
class CompressedString internal constructor(private val string: BitArray, private val characterSet: CharacterSet) {
    companion object {
        fun fromString(string: String): CompressedString? {
            val characters = CharacterCounter(string).contents()
            //TODO
            return null
        }
    }


    override fun toString(): String {
        val sb = StringBuilder()
        val div = characterSet.minBits
        for (i in (string.size / div - 1) downTo 0)
            sb.append(characterSet[string.toInt(i * div, (i + 1) * div)])
        return (sb.toString())
    }
}
