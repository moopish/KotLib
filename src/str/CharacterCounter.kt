package str

/**
 * <p>
 * === CharacterCounter ===
 * </p><p>
 * Date : December 10, 2018
 * </p><p>
 * TODO Description here
 * </p>
 * @author Michael van Dyk
 */
class CharacterCounter {
    private val charMap = HashMap<Char, Int>()

    constructor(string: String) {
        for (c in string) plus(c)
    }

    fun uniqueCount() = charMap.size
    fun contents() = charMap.keys.toTypedArray()

    operator fun plus(char: Char) {
        charMap[char] = charMap[char]?.plus(1) ?: 1
    }

    operator fun minus(char: Char) {
        val value = charMap[char]
        when (value) {
            null -> {}
            1 -> charMap.remove(char)
            else -> charMap[char] = value - 1
        }
    }
}