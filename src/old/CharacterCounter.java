package old;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * === CharacterCounter Data Structure ===
 * </p><p>
 * Date : March 28, 2016
 * </p><p>
 *  Used to store numerous characters but instead of a list
 * that holds each this stores how many of each character
 * is held with an integer value.
 * </p>
 * @author Michael van Dyk
 */
public final class CharacterCounter implements Iterable<Character> {

    /** Maps the characters stored to how many are currently stored **/
    private final Map<Character, Integer> char_map;

    /** Creates an empty CharacterCounter **/
    public CharacterCounter() {
        this.char_map = new HashMap<>();
    }

    /**
     *  Creates a CharacterCounter with the given characters stored within it.
     * @param chars the characters to store
     */
    public CharacterCounter(char[] chars) {
        this.char_map = new HashMap<>();
        for (char c : chars) {
            increment(c);
        }
    }

    /**
     *  Initializes to a copy of the given CharacterCounter.
     * @param chars the character counter to copy
     */
    public CharacterCounter(CharacterCounter chars) {
        this.char_map = new HashMap<>(chars.char_map);
    }

    /**
     *  Creates a CharacterCounter with the given characters stored within it.
     * @param chars the characters to store
     */
    public CharacterCounter(List<Character> chars) {
        this.char_map = new HashMap<>();
        chars.forEach(this::increment);
    }
    /**
     *  Creates a CharacterCounter with the given characters stored within it.
     * @param chars the characters to store
     */
    public CharacterCounter(String chars) {
        this(chars.toCharArray());
    }

    /**
     *  Reduces the count of the given character, performs an action,
     * then increases that character count back to the way it was before.
     * @see VoidMethod
     * @param c      the character to reduce
     * @param action the action to be performed when the character is reduced
     */
    public void actionWithReducedChar(char c, VoidMethod action) {
        if (decrement(c)) {
            action.method();
            increment(c);
        }
    }

    /**
     *  For each character stored, it reduce the count of the character,
     * performs an action, then increases that character count back to
     * the way it was before.
     * @see VoidMethod
     * @param action the action to be performed when the character is reduced
     */
    public void actionWithReducedCharAll(VoidMethod action) {
        forEach((c) -> actionWithReducedChar(c, action));
    }

    /**
     * @return list of the characters that have a count greater than 0
     */
    public List<Character> available() {
        return (char_map.keySet().stream().filter((c) -> char_map.get(c) > 0).collect(Collectors.toList()));
    }

    /**
     *  Decrease the count of the given character.
     * @param c the character to reduce the count of
     * @return true if it could be reduced, false if it could not be reduced
     */
    public boolean decrement(char c) {
        if (char_map.get(c) != null && char_map.get(c) > 0) {
            char_map.put(c, char_map.get(c) - 1);
            return (true);
        }
        return (false);
    }

    /**
     *  Decreases each character in the list, multiple instances
     * means it gets decreased multiple times.
     * @param chars the characters to decrease
     */
    public void decrement(List<Character> chars) {
        chars.forEach((c) -> {
            if (!decrement(c))
                throw new RuntimeException("decrement failed");
        });
    }

    /**
     * Empty the counter of all counts.
     */
    public void empty() {
        char_map.clear();
    }

    /**
     *  Increases the count of the given character.
     * @param c the character to increase the count of
     */
    public void increment(char c) {
        char_map.put(c, (char_map.get(c) == null) ? 1 : (char_map.get(c) + 1));
    }

    @Override
    public Iterator<Character> iterator() {
        return (available().iterator());
    }

    /**
     * @return the remaining characters in the counter, the old.sum of all the counts
     */
    public int remaining() {
        int hand = 0;
        for (char c : char_map.keySet()) {
            hand += char_map.get(c);
        }
        return (hand);
    }

    /** @return the count of the remaining characters that have a greater than zero count **/
    public int size() {
        return (available().size());
    }

    /**
     * @return the characters stored in a list
     */
    public List<Character> toList() {
        List<Character> list = new ArrayList<>();
        for (char c : char_map.keySet()) {
            for (int i=0; i<char_map.get(c); ++i) {
                list.add(c);
            }
        }
        return (list);
    }

    /**
     * A simple void method interface.
     */
    public interface VoidMethod {
        /** A simple void method. **/
        void method();
    }
}
