package old;

import java.util.ArrayList;
import java.util.List;

/**
 * <p><b>
 *     <tt>PrimeSet</tt> class
 * </b></p>
 *
 * <p>
 *     Date: Dec. 30, 2015
 * </p>
 *
 * <p>
 *     Holds a check for primes under the given
 * value at creation. Currently cannot be resized.
 * Works with primes under 2,147,400,000. More testing
 * required.
 * </p>
 *
 * @author Michael van Dyk
 * @version 1.0
 */
public final class PrimeSet {

    /**
     *  The set, stores the bits that represent
     * if a number is prime or not.
     */
    private final BitArray set;
    private final int limit;

    /**
     *  Creates a prime set that stores a check of whether
     * a value is prime. All values under the given limit
     * are stored.
     * @param limit all values under this have a stored check value
     */
    public PrimeSet(int limit) {
        set = new BitArray(limit);
        this.limit = limit;

        set.set(0);
        set.set(1);
        int i = 2;
        int stop = (int)Math.sqrt(limit);

        while ((i += 2) <= stop)
            set.set(i);

        i = 1;
        while ((i += 2) <= stop) {
            if (!set.get(i)) {
                for (int j = 2; j * i < limit; ++j) {
                    set.set(i * j);
                }
            }
        }
    }

    /**
     * Checks if the given value is prime
     * @param n the value to check
     * @return if the value is prime
     */
    public boolean prime(long n) {
        if (n < limit) {
            return (n >= 0 && !set.get((int)n));
        } else {
            if (n % 2L == 0 || n % 3L == 0) return false;
            int m = 4, i = 5;
            int len = set.length();

            long sqrt = (long)Math.sqrt(n);

            while (i <= sqrt) {
                if (!set.get(i))
                    if (n % i == 0)
                        return false;
                i += m ^= 6;
            }
            return (true);
        }
    }

    /**
     * Sums the value of the stored primes
     * @return the old.sum of the stored primes
     */
    public long sum_up() {
        long sum = 5;
        int m = 4, i = 5;
        int len = set.length();

        while (i < len) {
            if (!set.get(i))
                sum += i;
            i += m ^= 6;
        }

        return (sum);
    }

    /**
     * Creates a list of the stored primes
     * @return the list of the stored primes
     */
    public List<Integer> toList() {
        ArrayList<Integer> ret = new ArrayList<>(50251452);

        ret.add(2);
        ret.add(3);

        int m = 4;
        int i = 5;
        int len = set.length();

        while (i < len) {
            if (!set.get(i))
                ret.add(i);

            /**
             * Alternates adding 2 and 4 so the numbers
             * checked are of the form 6n +/- 1 (all primes
             * after 2 and 3 have this form, note not all
             * numbers of this sequence are prime)
             *
             *   proof :
             *   m = 4 xor 6
             *     = 100 xor 110
             *     = 010
             *     = 2
             *
             *   m = 2 xor 6
             *     = 010 xor 110
             *     = 100
             *     = 4
             */
            i += m ^= 6;
        }

        return (ret);
    }


    public List<String> primesThatMatch(MatchPattern p) {
        ArrayList<String> primes = new ArrayList<>();

        if (p.matching(2)) primes.add("2");
        if (p.matching(3)) primes.add("3");

        int m = 4, i = 5;
        int len = set.length();

        while (i < len) {
            if (!set.get(i) && p.matching(i))
                primes.add(String.valueOf(i));
            i += m ^= 6;
        }

        return (primes);
    }

    public interface MatchPattern {
        boolean matching(int i);
    }
}
