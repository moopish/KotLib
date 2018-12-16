package old;

import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileReader;
        import java.io.IOException;
        import java.nio.charset.Charset;
        import java.nio.file.Files;
        import java.nio.file.Path;
        import java.nio.file.Paths;
        import java.util.*;

/**
 * <p>
 * === Trie Data Structure ===
 * </p><p>
 * Created: Feb. 20, 2016
 * </p><p>
 *  This is a Trie data type implementation. It was
 * built for an implementation of a Scrabble AI.
 * </p><p>
 *  The Trie data type words well for storing a
 * dictionary of words. It was built to minimize
 * the amount of memory taken up by the storing all
 * of the words by having words with the same leading
 * characters be apart of the chain for those leading
 * characters.
 * </p>
 * @author Michael van Dyk
 */@SuppressWarnings("unused")
public final class Trie {

    /**
     *  The character used to represent a single character
     * wildcard, i.e. in word matching 'c&n' can match with
     * 'can' and 'con'. This must match to a character,
     * cannot be empty i.e. a space
     */
    public static final char SINGLE_WILD_CARD = '&';

    /**
     *  The character used to represent a multi-character
     * wildcard, i.e. 'c*n' can match with 'can', 'con',
     * 'clan', etc.
     */
    public static final char MULTI_WILD_CARD = '*';

    /**
     *  The character used to represent a potentially empty.
     * Allows for varying words that are at most a certain
     * length, i.e. '%%a%' can match with words like 'pan',
     * 'can', 'clan', 'hat', 'meal', etc.
     */
    public static final char NULLABLE_WILD_CARD = '%';

    /**
     *  If there is no limit on the characters used for a
     * particular search.
     */
    public static final int NO_LIMIT = -22;

    /**
     *  The root of the trie. This node is special as it
     * should be the only node with a null character value.
     * All other nodes are reached through this node.
     */
    private final Node root;

    /**
     *  The default constructor of the Trie. Initializes
     * the tree to simply the root 'null character' node.
     */
    public Trie() {
        root = new Node();
    }

    /**
     *  Initializes the Trie to have the specified dictionary stored.
     * @throws RuntimeException when word cannot be added or there is an issue with the dictionary file
     * @param dictionary the word list that will fill the Trie
     */
    public Trie(String dictionary) {
        this();

        try(BufferedReader br = new BufferedReader(new FileReader(new File(dictionary)))) {
            for(String line; (line = br.readLine()) != null; ) {
                String split[] = line.split(",");
                for (String s : split) {
                    if (s.length() <= 0 || !add(s.toUpperCase().trim())) {
                        System.err.println("Could not add: " + s.toUpperCase().trim());
                        //throw new RuntimeException("TRIE FAILED - Word not added: " + line);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("TRIE FAILED - Issue with dictionary file.");
        }
    }

    /**
     *  Adds a word to the Trie.
     * @param word the word to add to the Trie
     * @return true if the word was added, false
     * if the string is not a word (has character
     * not in the Latin alphabet) or if the word
     * has already been added
     */
    public boolean add(String word) {
        word = word.toUpperCase();

        if (!check_upper_alpha(word))
            return (false);

        Node add = root;

        for (int i=0; i<word.length(); ++i)
            add = add.add(word.charAt(i));

        if (add.word)
            return (false);
        add.word = true;
        return (true);
    }

    /**
     *  The character score specified by the values given by
     * the Scrabble board game.
     * @param c the character to get the value of
     * @return the value of the character
     */
    public static int charScore(char c) {
        switch (c) {
            case 'A' :
            case 'E' :
            case 'I' :
            case 'L' :
            case 'N' :
            case 'O' :
            case 'R' :
            case 'S' :
            case 'T' :
            case 'U' :
                return (1);

            case 'D' :
            case 'G' :
                return (2);

            case 'B' :
            case 'C' :
            case 'M' :
            case 'P' :
                return (3);

            case 'F' :
            case 'V' :
            case 'W' :
            case 'H' :
            case 'Y' :
                return (4);

            case 'K' :
                return (5);

            case 'J' :
            case 'X' :
                return (8);

            case 'Q' :
            case 'Z' :
                return (10);

            default :
                return (0);
        }
    }

    /**
     *  Checks if all the characters are part of the Latin alphabet.
     * @param word the word to check
     * @return if the word only contains characters in the Latin alphabet
     */
    private boolean check_upper_alpha(String word) {
        for (int i=0; i<word.length(); ++i) {
            if (!isUpperAlpha(word.charAt(i))) {
                return (false);
            }
        }
        return (true);
    }

    /**
     *  See if the specified word is contained in the Trie.
     * @param word the word to check
     * @return if the word is contained in the Trie
     */
    public boolean contains(String word) {
        word = word.toUpperCase();

        if (!check_upper_alpha(word))
            return (false);

        Node get = root;

        for (int i=0; i<word.length(); ++i) {
            get = get.child(word.charAt(i));
            if (get == null)
                return (false);
        }

        return (get.word);
    }

    /**
     *  Checks to see if the given words are contained by the Trie. Note
     * that if the list is empty, true is returned.
     * @param words the words to check if contained by the Trie
     * @return true if all words are contained, false otherwise
     */
    public boolean contains(String... words) {
        for (String word : words) {
            if (!contains(word)) {
                return (false);
            }
        }
        return (true);
    }

    /**
     *  Checks to see if the given words are contained by the Trie. Note
     * that if the list is empty, true is returned.
     * @param words the words to check if contained by the Trie
     * @return true if all words are contained, false otherwise
     */
    public boolean contains(List<String> words) {
        for (String word : words) {
            if (!contains(word)) {
                return (false);
            }
        }
        return (true);
    }

    /**
     *  Finds the depth of the Trie. How far the deepest branch is from the root.
     * @return the max depth
     */
    public int depth() {
        return (root.node_depth());
    }

    /**
     *  Checks if a character is an upper case Latin character.
     * @param c the character to check
     * @return if the character is an upper case Latin character
     */
    public static boolean isUpperAlpha(char c) {
        return (c >= 'A' || c <= 'Z');
    }

    /**
     *  Finds the number of nodes in the Trie.
     * @return The number of nodes in this Trie
     */
    public int size() {
        return (root.node_count());
    }

    /**
     * @return the words stored in a list
     */
    public List<String> toList() {
        List<String> found = new ArrayList<>();
        toList(found, root);
        return (found);
    }

    /**
     * The meat of the {@link #toList()} method.
     * @param found the words found
     * @param n     the current node in the exploration explored
     */
    private void toList(List<String> found, Node n) {
        if (n != null) {
            if (n.word) {
                found.add(n.build());
            }

            for (char c : n.children.keySet()) {
                toList(found, n.child(c));
            }
        }
    }

    /**
     *  Saves the dictionary to a file.
     * @param filename the name of the save file
     */
    public void toFile(String filename) {
        try {
            List<String> lines = toList();
            Path file = Paths.get("comp4106project/res/" + filename + ".dic");
            Files.write(file, lines, Charset.forName("ASCII"));
        } catch (IOException e) {
            System.err.println();
            e.printStackTrace();
            throw new RuntimeException("TRIE FAIL : could not save dictionary file");
        }
    }

    /**
     *  Finds the total number of words stored in the Trie
     * @return The number of words in the Trie
     */
    public int words() {
        return (root.word_count());
    }

    /**
     *  Finds the score of a given word.
     * @param word the word to find the score of
     * @return the score of the word
     */
    public static int wordScore(String word) {
        int score = 0;
        for (int i=0; i<word.length(); ++i) {
            score += charScore(word.charAt(i));
        }
        return (score);
    }

    /**
     *  Gets the words that can be created with
     * the given characters that are contained
     * by the Trie. The character '&' can be used
     * as a wild card character, as in it represents
     * any character 'A' to 'Z'. No character is
     * used twice so if need for a word with two or
     * more the same character it must be included
     * as multiple times.
     * @param chars the characters to find words built from
     * @return the list of words sorted to highest Scrabble score
     */
    public List<String> wordsFromCharacters(String chars) {
        chars = chars.toUpperCase();
        List<Character> list = new ArrayList<>();
        for (int i=0; i<chars.length(); ++i)
            list.add(chars.charAt(i));
        return (wordsFromCharacters(list));
    }

    /**
     *  Gets the words that can be created with
     * the given characters that are contained
     * by the Trie. The character '&' can be used
     * as a wild card character, as in it represents
     * any character 'A' to 'Z'. No character is
     * used twice so if need for a word with two or
     * more the same character it must be included
     * as multiple times.
     * @param chars the characters to find words built from
     * @return the list of words sorted to highest Scrabble score
     */
    public List<String> wordsFromCharacters(List<Character> chars) {
        CharacterCounter char_map = new CharacterCounter(chars);

        /** Checks if accepted characters **/
        for (Character c : chars) {
            if (!(isUpperAlpha(c) || c == SINGLE_WILD_CARD))
                return (null);
            char_map.increment(c);
        }

        return (wordsFromCharacters(char_map));
    }

    /**
     *  Gets the words that can be created with
     * the given characters that are contained
     * by the Trie. The character '&' can be used
     * as a wild card character, as in it represents
     * any character 'A' to 'Z'. Characters are used
     * as at most the initial count in the
     * CharacterCounter.
     * @param chars the characters to use to build the words
     * @return the words found
     */
    public List<String> wordsFromCharacters(CharacterCounter chars) {
        Queue<Node> words = new PriorityQueue<>(Node::compareScore);

        /** Makes the call to the method that actually determine words **/
        wordsFromCharacters(root, chars, words);

        List<String> ret = new ArrayList<>();

        /** Builds the list of words, ordered by score **/
        while (!words.isEmpty()) {
            ret.add(words.poll().build());
        }

        return (ret);
    }

    /**
     *  Recursive function that performs the required action of {@link Trie#wordsFromCharacters(List)}.
     * @param n the current node to be considered
     * @param chars the characters and how many of each remain to be used
     * @param words the words that have been found
     */
    private void wordsFromCharacters(Node n, CharacterCounter chars, Queue<Node> words) {
        if (n.word) {
            /** If the current node represents a word it
             can be built with the given characters
             thus it is added to the found words        **/
            words.add(n);
        }

        /** Looks at each of the characters available **/
        chars.forEach((c) ->
                /** Reduces the character from the counter
                 and performs the search again at child nodes **/
                chars.actionWithReducedChar(c, () -> {
                    if (c == SINGLE_WILD_CARD) {
                        /** If the character is a wild card it goes through
                         each of the child nodes since it can represent any
                         of them                                            **/
                        for (Node child : n.children.values())
                            wordsFromCharacters(child, chars, words);
                    } else {
                        Node child = n.children.get(c);
                        if (child != null)
                            wordsFromCharacters(child, chars, words);
                    }
                })
        );
    }

    /**
     *  Builds a list of the words contained by the Trie that match
     * with the given pattern string. If the given string is a word,
     * only that word will be returned. If a search string with the
     * {@link Trie#SINGLE_WILD_CARD} is used then any character can
     * be in that space. Similarly if {@link Trie#MULTI_WILD_CARD}
     * is used then any number of characters can appear in that
     * place.
     * @param search The pattern string to match
     * @return the list of words that match the pattern
     */
    public List<String> wordsMatchString(String search) {
        Queue<Node> words = new PriorityQueueTreeSet<>(Node::compareScore, Node::compareWord);

        /** Allows for lower case Latin characters to be used **/
        search = search.toUpperCase();

        /** Checks to see if the characters in the search string are valid **/
        for (int i=0; i<search.length(); ++i) {
            char c = search.charAt(i);
            if (!(isUpperAlpha(c) || c == SINGLE_WILD_CARD || c == MULTI_WILD_CARD || c == NULLABLE_WILD_CARD))
                return (null);
        }

        /** makes the call to run the actual search **/
        wordsMatchString(root, search, 0, words);

        List<String> ret = new ArrayList<>();

        /** Builds the list of words, ordered by score **/
        while (!words.isEmpty()) {
            ret.add(words.poll().build());
        }

        return (ret);
    }

    /**
     *  Does the actual work of the {@link Trie#wordsMatchString(String)}.
     * Recursively finds the words that match with the pattern.
     * @param n      the current node to examine
     * @param search the characters of the search string
     * @param pos    the current position to focus on in the search string
     * @param words  the currently found words that match the given search string
     */
    private void wordsMatchString(Node n, String search, int pos, Queue<Node> words) {
        if (n != null) {
            if (search.length() == pos) {
                if (n.word) {
                    words.add(n);
                }
            } else {
                char c = search.charAt(pos);

                switch (c) {
                    case MULTI_WILD_CARD :
                        for (Node child : n.children.values()) {
                            wordsMatchString(child, search, pos, words);
                            wordsMatchString(child, search, pos + 1, words);
                        }
                        break;

                    case SINGLE_WILD_CARD :
                        for (Node child : n.children.values()) {
                            wordsMatchString(child, search, pos + 1, words);
                        }
                        break;

                    case NULLABLE_WILD_CARD:
                        if (n == root) {
                            wordsMatchString(root, search, pos + 1, words);
                        }
                        for (Node child : n.children.values()) {
                            if (n.word) {
                                words.add(n);
                            }
                            wordsMatchString(child, search, pos + 1, words);
                        }
                        break;

                    default :
                        wordsMatchString(n.child(c), search, pos + 1, words);
                }
            }
        }
    }

    /**
     *  Never finished this, does not get used, just ignore. Another
     * method handles what I wanted to do here.
     * @param search the string to match with
     * @param chars  the limited character set
     * @return unknown, not tested, hopefully the words that matched the given search string
     */
    @Deprecated @SuppressWarnings("all")
    public List<String> wordsMatchStringWithCharacters(String search, char[] chars) {
        if (chars == null || chars.length == 0) {
            return (wordsMatchString(search));
        } else {
            Queue<Node> words = new PriorityQueue<>(Node::compareScore);
            CharacterCounter char_map = new CharacterCounter();

            search = search.toUpperCase();

            for (int i=0; i<search.length(); ++i) {
                char c = search.charAt(i);
                if (!(isUpperAlpha(c) || c == SINGLE_WILD_CARD || c == MULTI_WILD_CARD || c == NULLABLE_WILD_CARD))
                    return (null);
            }

            for (Character c : chars) {
                if (!(isUpperAlpha(c) || c == SINGLE_WILD_CARD))
                    return (null);
                char_map.increment(c);
            }

            wordsMatchStringWithCharacters(root, search, 0, char_map, words, false);

            List<String> ret = new ArrayList<>();

            while (!words.isEmpty()) {
                ret.add(words.poll().build());
            }

            return (ret);
        }
    }

    /**
     *  Never finished this, does not get used, just ignore. Another
     * method handles what I wanted to do here.
     * @param n       current node
     * @param search  the search string
     * @param pos     the position in the search string
     * @param chars   the characters to use
     * @param words   the words found
     * @param is_word if the word is actually a word
     */
    @Deprecated @SuppressWarnings("all")
    private void wordsMatchStringWithCharacters(Node n, String search, int pos, CharacterCounter chars, Queue<Node> words, boolean is_word) {
        if (n != null) {
            if (search.length() == pos) {
                if (n.word && !words.contains(n)) {
                    words.add(n);
                }
            } else {
                char c = search.charAt(pos);

                switch (c) {
                    case MULTI_WILD_CARD :

                        //wordsMatchStringWithCharacters(n, search, pos + 1, len, chars, words, is_word);
                        throw new RuntimeException("This should not happen/not implemented");
                        //NOT FINISHING THIS AS UNNEEDED

                        //break;

                    case SINGLE_WILD_CARD :
                        chars.forEach((key) ->
                                chars.actionWithReducedChar(key, () -> {
                                    if (key == SINGLE_WILD_CARD) {
                                        for (Node child : n.children.values())
                                            wordsMatchStringWithCharacters(child, search, pos + 1, chars, words, true);
                                    } else {
                                        wordsMatchStringWithCharacters(n.child(key), search, pos + 1, chars, words, true);
                                    }
                                })
                        );

                        break;

                    case NULLABLE_WILD_CARD:
                        chars.forEach((key) ->
                                chars.actionWithReducedChar(key, () -> {
                                    if (key == SINGLE_WILD_CARD) {
                                        for (Node child : n.children.values())
                                            wordsMatchStringWithCharacters(child, search, pos + 1, chars, words, true);
                                    } else {
                                        wordsMatchStringWithCharacters(n.child(key), search, pos + 1, chars, words, true);
                                    }
                                })
                        );
                        wordsMatchStringWithCharacters(n, search, pos + 1, chars, words, is_word);

                        break;

                    default :
                        wordsMatchStringWithCharacters(n.child(c), search, pos + 1, chars, words, true);

                }
            }
        }
    }

    /**
     *  Finds words that fit the search string that can be built with the
     * given characters. No limit on how many of those characters are used.
     * @param search the search match string
     * @param chars  the characters to use
     * @return all results of the matching
     */
    public List<SearchResult> findWords(String search, char[] chars) {
        return (findWords(search, NO_LIMIT, chars));
    }

    /**
     *  Finds words that fit the search string that can be built with the
     * given characters.
     * @param search the search match string
     * @param limit  the max number of characters to use from the given characters
     * @param chars  the characters to use
     * @return all results of the matching, limited to using a certain amount of characters from 'chars'
     */
    public List<SearchResult> findWords(String search, int limit, char[] chars) {
        CharacterCounter char_map = new CharacterCounter(chars);
        return (findWords(search, limit, char_map));
    }

    /**
     *  Finds words that fit the search string that can be built with the
     * given characters. No limit on how many of those characters are used.
     * @param search the search match string
     * @param chars  the characters to use
     * @return all results of the matching
     */
    public List<SearchResult> findWords(String search, CharacterCounter chars) {
        return (findWords(search, NO_LIMIT, chars));
    }

    /**
     *  Finds words that fit the search string that can be built with the
     * given characters.
     * @param search the search match string
     * @param limit  the max number of characters to use from the given characters
     * @param chars  the characters to use
     * @return all results of the matching
     */
    public List<SearchResult> findWords(String search, int limit, CharacterCounter chars) {
        if (chars == null || chars.remaining() == 0) {
            return (new ArrayList<>());
        } else {
            Queue<SearchResult> words = new PriorityQueueTreeSet<>(SearchResult::compareByScore, SearchResult::compareByWord);

            search = search.toUpperCase();

            for (int i = 0; i < search.length(); ++i) {
                char c = search.charAt(i);
                if (!(isUpperAlpha(c) || c == SINGLE_WILD_CARD || c == NULLABLE_WILD_CARD))
                    return (null);
            }

            for (Character c : chars) {
                if (!(isUpperAlpha(c) || c == SINGLE_WILD_CARD))
                    return (null);
            }

            int len = -1;
            for (int i=0; i<search.length(); ++i) {
                if (search.charAt(i) == NULLABLE_WILD_CARD) {
                    continue;
                }
                len = i;
                break;
            }

            for (int i=0; i<=len; ++i) {
                findWords(root, search, i, i, words, chars, 0, limit, false);
            }

            List<SearchResult> ret = new ArrayList<>();

            while (!words.isEmpty()) {
                ret.add(words.poll());
            }

            return (ret);
        }
    }

    /**
     *  The meat of the {@link #findWords(String, char[])},
     * {@link #findWords(String, int, char[])},
     * {@link #findWords(String, CharacterCounter)} and
     * {@link #findWords(String, int, CharacterCounter)}
     * methods. This does the actual searching and finds
     * and sets up the results.
     *
     * @param n       the current node of the search
     * @param search  the search string
     * @param start   the initial position, for stating where the results
     *               found in this call start in the search string
     * @param pos     the current position considered in the search string
     * @param words   the words that have been found so far
     * @param chars   the characters that can be used
     * @param used    the number of characters used so far
     * @param limit   the max number of characters that can be used
     * @param addable if the current call of this search can add words to the results
     */
    private void findWords(Node n, String search, int start, int pos, Queue<SearchResult> words, CharacterCounter chars, int used, int limit, boolean addable) {
        /** If the node is null then no words exist at this point **/
        if (n != null) {

            /** If the search has reached it's terminating point in the search string **/
            if (search.length() == pos) {

                /** If the node actually represents a node **/
                if (addable && n.word) {
                    SearchResult searchResult = new SearchResult(n, start);
                    words.add(searchResult);
                }
            } else {
                /** The currently considered character in the search string **/
                char c = search.charAt(pos);

                switch (c) {
                    /** If the character can be ignored or if
                     it can be any character 'A' to 'Z'      **/
                    case NULLABLE_WILD_CARD :
                        if (addable && n.word) {
                            SearchResult searchResult = new SearchResult(n, start);
                            words.add(searchResult);
                        }
                        //NEEDS TO DO THE SAME AS FOR THE SINGLE_WILD_CARD AFTER THIS
                        //SO DON'T REMOVE ME MIKE

                        /** If the character can be any character 'A' to 'Z' **/
                        /** NOTE: Has to be from the chars counter **/
                    case SINGLE_WILD_CARD :
                        /** If the character limit has not been reached **/
                        if (used != limit) {
                            /** Look at each possible character **/
                            chars.forEach((c_next) ->
                                    /** Reduce by that character and continue the search **/
                                    chars.actionWithReducedChar(c_next, () -> {
                                        if (c_next == SINGLE_WILD_CARD) {
                                            /** Do for all characters 'A' to 'Z' **/
                                            for (Character next_node : n.children.keySet()) {
                                                findWords(n.child(next_node), search, start, pos + 1, words, chars, used + 1, limit, addable);
                                            }
                                        } else {
                                            /** continue the search with the given character **/
                                            findWords(n.child(c_next), search, start, pos + 1, words, chars, used + 1, limit, addable);
                                        }
                                    })
                            );
                        }

                        break;

                    default :
                        /** just do the search on the next node to continue the search **/
                        findWords(n.child(c), search, start, pos + 1, words, chars, used, limit, true);

                }
            }
        }
    }

    /**
     *  The nodes that represent a character. The
     * level of the node determines where the
     * character lies in the word, i.e. if the node
     * is the third node away from the root, it is
     * the third letter in the word.
     */
    private static final class Node implements Comparable<Node> {

        /** The score at the current node, matters only if a word **/
        private final int score;

        /** True if the current node represents the end of a word, false otherwise **/
        private boolean word;

        /** The character that the node represents **/
        private final char character;

        /** The parent node of the current node **/
        private final Node parent;

        /** The children nodes of the current node **/
        private final Map<Character, Node> children;

        /**
         *   Initializes the root node, only should be
         * used on the creation of the root.
         */
        private Node() {
            this.score = 0;
            this.character = '\0';
            this.word = false;
            this.parent = null;
            this.children = new TreeMap<>();
        }

        /**
         *  Creates a new node with the given character and parent.
         * @param character the character that the new node represents
         * @param parent the parent of the new node
         */
        public Node(char character, Node parent) {
            this.word = false;
            this.character = character;
            this.parent = parent;
            this.children = new TreeMap<>();
            this.score = charScore(character) + parent.score;
        }

        /**
         *   Adds a child node IFF a child representing that character
         * does not exist. Returns the child representing that character
         * regardless of if it was just created or already was created.
         * @param c the character to create and get a child node for
         * @return the child node representing that character
         */
        public Node add(char c) {
            Node ret = children.get(c);

            if (ret == null) {
                ret = new Node(c, this);
                children.put(c, ret);
            }

            return (ret);
        }

        /**
         *  Builds the string that the path from the root to the current node represents.
         * @return the string represented by the path from the root to the current node
         */
        public String build() {
            StringBuilder s = new StringBuilder("");
            Node curr = this;

            while (curr != null && curr.character != 0) {
                s.insert(0, curr.character);
                curr = curr.parent;
            }

            return (s.toString());
        }

        /**
         *  Gets the child represented by the given character.
         * @param c the character to get a child that represents
         * @return the child node of the given character *or null if does not exist*
         */
        public Node child(char c) {
            return (children.get(c));
        }

        /**
         *  Compares the score of two nodes, i.e. the score of the words represented
         * at those nodes.
         * @param a the first node to compare
         * @param b the second node to compare
         * @return negative if a < b, zero if a == b, positive if a > b
         */
        public static int compareScore(Node a, Node b) {
            return (b.score - a.score);
        }

        /**
         *  Compares the words represented at the node. Like comparing two strings.
         * @param a the first node to compare
         * @param b the second node to compare
         * @return negative if a < b, zero if a == b, positive if a > b
         */
        public static int compareWord(Node a, Node b) {
            boolean a_null = (a == null);
            boolean b_null = (b == null);

            if (a_null && b_null) {
                return (0);
            } else if (a_null) {
                return (1);
            } else if (b_null) {
                return (-1);
            } else if (a.character == b.character) {
                return (compareWord(a.parent, b.parent));
            }
            return (a.character - b.character);
        }

        /**
         * {@inheritDoc}
         * <p>
         *     Compares the words of this node with the given node.
         * </p>
         * @param o the node to compare to this node
         * @return the comparison value
         */
        @Override
        public int compareTo(Node o) {
            return (compareWord(this, o));
        }

        /**
         *  Gets the total number of nodes in the Trie.
         * @return the total number of nodes in this Trie
         */
        public int node_count() {
            int count = 1;
            for(Node n : children.values())
                count += n.node_count();
            return (count);
        }

        /**
         *   Gets the maximum depth of the Trie, i.e. the distance
         * between the root and the deepest leaf.
         * @return the max depth
         */
        public int node_depth() {
            int max = 0;
            for(Node n : children.values()) {
                int t = n.node_depth();
                if (t > max)
                    max = t;
            }
            return (max + 1);
        }

        /**
         *   The score of the current node. If the current node is
         * not the last character of a word then '0' is given.
         * @return the score of the current node, '0' if not a word
         */
        public int score() {
            return (word ? score : 0);
        }

        /**
         *   Finds the total number of words stored in the Trie.
         * @return the number of words stored by the Trie
         */
        public int word_count() {
            int count = (word ? 1 : 0);
            for (Node n : children.values()) {
                count += n.word_count();
            }
            return (count);
        }
    }

    /**
     * Meant to help find the location of a word in the string to place it in a move.
     */
    public static final class SearchResult implements Comparable<SearchResult> {

        /** Where the result starts in the search string. **/
        private final int start;
        /** The node that represent the word found. **/
        private final Node word;

        /**
         *  Sets up the search result.
         * @param word  see {@link #word}
         * @param start see {@link #start}
         */
        public SearchResult(Node word, int start) {
            this.word = word;
            this.start = start;
        }

        /**
         *  Compares by the score of the result.
         * @param a the first result to compare
         * @param b the second result to compare
         * @return a < b then -1, a == b then 0, a > b then 1
         */
        public static int compareByScore(SearchResult a, SearchResult b) {
            int result = Node.compareScore(a.word, b.word);
            return ((result == 0) ? (a.start - b.start) : result);
        }

        /**
         *  Compares by the words of the result.
         * @param a the first result to compare
         * @param b the second result to compare
         * @return a < b then -1, a == b then 0, a > b then 1
         */
        public static int compareByWord(SearchResult a, SearchResult b) {
            int result = Node.compareWord(a.word, b.word);
            return ((result == 0) ? (a.start - b.start) : result);
        }

        /**
         * @return the position that the result word starts in the search string
         */
        public int getStart() {
            return (start);
        }

        /**
         * @return the word stored by this result
         */
        public String getWord() {
            return (word.build());
        }

        @Override
        public int compareTo(SearchResult o) {
            return (compareByWord(this, o));
        }

        @Override
        public String toString() {
            return (start + " " + word.build());
        }
    }
}
