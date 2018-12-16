package old;

/**
 * <p><b>
 *     <tt>bit.BitArray</tt> class
 * </b></p>
 *
 * <p>
 *     Date: Dec. 30, 2015
 * </p>
 *
 * <p>
 *     Holds an array of bits. The array of bits
 * is represented by an array of integers and each
 * bit of the integers (unless excess) are used in
 * the representation of the bit array. This class
 * is immutable and any calculations like 'and' and
 * 'or' will return a new instance of the class with
 * the calculated value.
 * </p>
 *
 * <p><b>NOTE: array indices begin at zero like other arrays</b></p>
 *
 * @author Michael van Dyk
 * @version 1.0
 */
public final class BitArray implements Comparable<BitArray> {

    /**
     * Each in represents a bit of the array.
     *
     *   The first int represents bits 0 to 31, second
     * represents 32 to 63, etc.
     */
    private final int[] bits;

    /**
     * The number of bits in the array.
     */
    private final int length;

    /**
     * <p>
     *     Creates an array of bits represented by an
     *    array of integers.
     * </p>
     * <p><b>
     *     To use this class most efficiently, create
     *    with lengths that are multiples of 32 as ints
     *    in java have 32 bits.
     * </b></p>
     * @param length the number of bits in the newly created array
     */
    public BitArray(int length) {
        if (length <= 0)
            throw new RuntimeException();
        bits = new int[(int)Math.ceil(length/(double)Integer.SIZE)];
        this.length = length;
    }

    /**
     *  Creates a new bit.BitArray from a given bit string. If
     * any of the characters given are neither '0' nor '1'
     * then an exception is thrown.
     * @param bit_string the bit string to base the array from
     */
    public BitArray(String bit_string) {
        this(bit_string.length());
        for (int i=1; i<=length; ++i) {
            switch (bit_string.charAt(length - i)) {
                case '0' :
                    break;
                case '1' :
                    set(i - 1);
                    break;
                default :
                    throw new RuntimeException();
            }
        }
    }

    /**
     *  Returns a new bit.BitArray of the value (this & o)
     * @param o the other bit.BitArray in the and
     * @return the new bit.BitArray
     */
    public BitArray and(BitArray o) {
        BitArray ret = new BitArray(length < o.length ? length : o.length);
        for (int i=0; i<ret.bits.length; ++i)
            ret.bits[i] = bits[i] & o.bits[i];
        return (ret);
    }

    /**
     *  Sets the value at the given index to what
     * value is given.
     * @param index the index to set the value of
     * @param value the value to set to
     */
    public void assign(int index, boolean value) {
        if (value)
            set(index);
        else
            clear(index);
    }

    /**
     *  Sets the bit at the given index to 0/false.
     * If the index is out of range an exception is thrown.
     * @param index the given index
     */
    public void clear(int index) {
        if (index >= length || index < 0)
            throw new RuntimeException();
        bits[index / Integer.SIZE] = bits[index / Integer.SIZE] & ~(1 << (index % Integer.SIZE));
    }

    /**
     *  Compares this bit.BitArray and the other given bit.BitArray
     * @param o the bit.BitArray to compare with
     * @return negative, zero, positive if less than, equal, greater than
     */
    @Override
    public int compareTo(BitArray o) {
        if (length == o.length) {
            for (int i = 0; i < bits.length; ++i)
                if (bits[i] != o.bits[i])
                    return (bits[i] - o.bits[i]);
            return (0);
        }
        return (length - o.length);
    }

    /**
     *  Creates a new array with the given array added on to the end of the
     * former array. this has o added to the end.
     *     //TODO not done
     * @param o the array to concatenate to the end
     * @return the concatenated array
     */
    public BitArray concat(BitArray o) {
        BitArray ret = new BitArray(length + o.length);
        //TODO
        return (ret);
    }

    /**
     *  Makes a deep copy of the bit.BitArray that is equal
     * to the bit.BitArray this method is called upon.
     * @return The copy of the bit.BitArray
     */
    public BitArray copy() {
        BitArray ret = new BitArray(length);
        System.arraycopy(bits, 0, ret.bits, 0, bits.length);
        return (ret);
    }

    public boolean equals(BitArray o) {
        return (compareTo(o) == 0);
    }

    /**
     *  Creates a new bit.BitArray from a given bit string. If
     * any of the characters given are neither '0' nor '1'
     * then an exception is thrown.
     * @param bit_string the bit string to base the array from
     * @return the new bit.BitArray from the given bit string
     */
    public static BitArray fromBinary(String bit_string) {
        return (new BitArray(bit_string));
    }

    //TODO NOT DONE SO COMMENT NOT DONE
    /**
     *  Makes bit.BitArray from hex string
     * @param hex_string the hex string
     * @return the bit.BitArray
     */
    public static BitArray fromHex(String hex_string) {
        BitArray ret = new BitArray(hex_string.length() * 4);

        for (int i=hex_string.length()-1; i>=0; --i) {
            //TODO DO NERD
        }
        return (ret);
    }

    /**
     *  Get the bit value at the given index.
     * If the index is out of range an exception is thrown.
     * @param index the given index
     * @return the value of the bit in boolean form
     */
    public boolean get(int index) {
        if (index >= length || index < 0)
            throw new RuntimeException();
        return (((bits[index / Integer.SIZE] & (1 << (index % Integer.SIZE))) >>> (index % Integer.SIZE)) == 1);
    }

    //TODO finish not done, does not work
    public BitArray leftShift(int n) {
        if (n < 0)
            return (rightShift(-n));
        if (n == 0)
            return (this);
        BitArray ret = new BitArray(n + length);
        System.arraycopy(bits, 0, ret.bits, (int)Math.ceil((ret.length - length)/(double)Integer.SIZE), bits.length);

        int dif = ((n + length) % Integer.SIZE) - (length % Integer.SIZE);

        if (dif < 0) {
            for (int i=0; i<bits.length; ++i){
                //TODO
            }
        } else if (dif > 0) {
            //TODO NOT DONE
            int op = Integer.SIZE - dif;
            for (int i=1; i<bits.length; ++i){
                ret.bits[ret.bits.length - i] = (bits[bits.length - i] << dif) | (bits[bits.length - i - 1] >>> op);
            }
            //ret.bits[ret.bits.length - bits.length] = (bits[0] << dif);
        }
        return (ret);
    }

    //TODO finish same as leftShift
    public BitArray rightShift(int n) {
        if (n < 0)
            return (leftShift(-n));
        if (n == 0)
            return (this);
        if (length - n <= 0)
            return (new BitArray(1));
        BitArray ret = new BitArray(length - n);
        System.arraycopy(bits, bits.length - ret.bits.length, ret.bits, 0, ret.bits.length);

        int dif = (length % Integer.SIZE) - (bits.length % Integer.SIZE);
        if (dif < 0) {

        } else if (dif > 0) {

        }

        return (ret);
    }

    public BitArray rightShiftSigned(int n) {
        if (n < 0)
            return (leftShift(-n));
        if (n == 0)
            return (this);
        if (length - n <= 0)
            return ((get(length - 1)) ? new BitArray(length).negate() : new BitArray(length));

        BitArray ret = new BitArray(length);
        if (get(length - 1)) {
            int end = (length + n - length)/Integer.SIZE;
            for (int i=1; i<=end; ++i) {

            }
        }

        return (null);
    }

    /**
     * The number of bits stored in the array
     * @return the number of bits in the array
     */
    public int length() {
        return (length);
    }

    /**
     *  Returns a negated version of the bit.BitArray. Each
     * bit is the opposite of what it is in the original
     * BirArray.
     * @return the negated bit.BitArray
     */
    public BitArray negate() {
        BitArray ret = new BitArray(length);
        for (int i=0; i<bits.length-1; ++i)
            ret.bits[i] = ~bits[i];
        ret.bits[bits.length-1] = ~(bits[bits.length-1] & (~0 << (length & Integer.SIZE)));
        return (ret);
    }

    /**
     *  Returns a new bit.BitArray of the value (this | o)
     * @param o the other bit.BitArray in the or
     * @return the new bit.BitArray
     */
    public BitArray or(BitArray o) {
        BitArray ba;
        if (length >= o.length) {
            ba = new BitArray(length);
            for (int i=0; i<o.bits.length; ++i)
                ba.bits[i] = bits[i] | o.bits[i];
            System.arraycopy(bits, o.bits.length, ba.bits, o.bits.length, bits.length - o.bits.length);
        } else {
            ba = o.or(this);
        }
        return (ba);
    }

    /**
     *  Sets the bit at the given index to 1/true
     * @param index the index to set
     */
    public void set(int index) {
        if (index >= length || index < 0)
            throw new RuntimeException();
        bits[index / Integer.SIZE] = bits[index / Integer.SIZE] | (1 << (index % Integer.SIZE));
    }

    public byte[] toBytes() {
        byte[] bytes = new byte[(int)Math.ceil(length/Byte.SIZE)];

        for (int i=0; i<bytes.length; ++i) {

        }

        return (bytes);
    }

    /**
     * Gets the bit string of the bit.BitArray
     * @return the bit string of the bit.BitArray
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        for (int i=length-1; i>=0; --i)
            sb.append(get(i) ? '1' : '0');
        return (sb.toString());
    }

    /**
     *  Returns a new bit.BitArray of the value (this ^ o)
     * @param o the other bit.BitArray in the xor
     * @return the new bit.BitArray
     */
    public BitArray xor(BitArray o) {
        BitArray ba;
        if (length >= o.length) {
            ba = new BitArray(length);
            for (int i=0; i<o.bits.length; ++i)
                ba.bits[i] = bits[i] ^ o.bits[i];
            System.arraycopy(bits, o.bits.length, ba.bits, o.bits.length, bits.length - o.bits.length);
        } else {
            ba = o.or(this);
        }
        return (ba);
    }
}
