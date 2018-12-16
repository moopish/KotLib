package bit

/**
 * <p>
 * === Bits ===
 * </p><p>
 * Date : December 13, 2018
 * </p><p>
 * TODO Description here
 * </p>
 * @author Michael van Dyk
 */

const val BYTE_MASK = 0xFF
const val SHORT_MASK = 0xFFFF

//TODO bit functions for bytes, shorts etc.
infix fun Byte.bit(index: Int) = ((this.toInt() and BYTE_MASK) bit index).toByte()
infix fun Short.bit(index: Int) = ((this.toInt() and SHORT_MASK) bit index).toShort()
infix fun Int.bit(index: Int) = (this and (1 shl index)) ushr index
infix fun Long.bit(index: Int) = (this and (1L shl index)) ushr index

infix fun Float.bit(index: Int) = this.toRawBits() bit index
infix fun Double.bit(index: Int) = this.toRawBits() bit index

infix fun Int.clearBit(index: Int) = this and (1 shl index).inv()
infix fun Long.clearBit(index: Int) = this and (1L shl index).inv()

infix fun Float.clearBit(index: Int) = Float.fromBits(this.toRawBits() clearBit index)
infix fun Double.clearBit(index: Int) = Double.fromBits(this.toRawBits() clearBit index)

infix fun Int.flipBit(index: Int) = this xor (1 shl index)
infix fun Long.flipBit(index: Int) = this xor (1L shl index)

infix fun Float.flipBit(index: Int) = Float.fromBits(this.toRawBits() flipBit index)
infix fun Double.flipBit(index: Int) = Double.fromBits(this.toRawBits() flipBit index)

infix fun Int.setBit(index: Int) = this or (1 shl index)
infix fun Long.setBit(index: Int) = this or (1L shl index)

infix fun Float.setBit(index: Int) = Float.fromBits(this.toRawBits() setBit index)
infix fun Double.setBit(index: Int) = Double.fromBits(this.toRawBits() setBit index)