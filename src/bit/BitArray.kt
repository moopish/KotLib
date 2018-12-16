package bit

/**
 * <p>
 * === bit.BitArray ===
 * </p><p>
 * Date : December 10, 2018
 * </p><p>
 * TODO Description here
 * </p>
 * @author Michael van Dyk
 */
class BitArray(val size: Int, init: (Int)->Int = { 0 }) {
    private companion object {
        const val bitsInBlock = 32

        private class BitPosition constructor(val block: Int, val bitInBlock: Int)
        private fun bitPosition(index: Int) = BitPosition(index / bitsInBlock, index.rem(bitsInBlock))
    }

    private val remainder = size.rem(bitsInBlock)
    private val bits = Array((size - 1)/ bitsInBlock + 1, init)

    infix fun and(other: BitArray): BitArray {
        val ret = BitArray(if (size >= other.size) size else other.size)

        return (ret)
    }

    operator fun get(index: Int): Boolean {
        val bp = bitPosition(index)
        return (((bits[bp.block] and (1 shl bp.bitInBlock)) ushr bp.bitInBlock) == 1)
    }

    operator fun get(start: Int, end: Int) = SubBitArray(start, end - start)

    operator fun set(index: Int, value: Boolean) {
        val bp = bitPosition(index)
        bits[bp.block] = if (value)
            bits[bp.block] or (1 shl bp.bitInBlock)
        else
            bits[bp.block] and (1 shl bp.bitInBlock).inv()
    }

    fun toInt() = toInt(0, if (size <= bitsInBlock) size else bitsInBlock)

    fun toInt(start: Int, end: Int): Int {
        var ret = 0
        for (i in (end - 1) downTo start)
            ret = (ret shl 1) + (if (this[i]) 1 else 0)
        return ret
    }

    override fun toString() = toString(0, size)

    fun toString(start: Int, end: Int): String {
        val sb = StringBuilder()
        for (i in (end - 1) downTo start)
            sb.append(if (this[i]) '1' else '0')
        return (sb.toString())
    }

    inner class SubBitArray internal constructor(private val offset: Int, private val length: Int) {
        fun toInt() = toInt(offset, offset + length)
        override fun toString() = toString(offset, offset + length)
    }
}