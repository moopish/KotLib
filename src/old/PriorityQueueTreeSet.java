package old;

import java.util.*;

/**
 *  <p>
 *  == Priority Queue Tree Set ==
 *  </p><p>
 *  Date : March 10, 2016
 * </p><p>
 *   This was made simple to reduce the look up time on seeing if an element already exists in the
 * PriorityQueue. This came from the fact that look ups into a PriorityQueue are very slow. In no
 * way is this class well developed and was only made out of my need for quick look ups and sorted
 * order. Their maybe a better data structure to deal with my situation but I made this.
 * </p><p>
 * NOTE: This likely takes up double the space in memory but reduces the look up time by quite a lot.
 * </p>
 * @param <E> if the type is not {@link Comparable} then use the
 * {@link PriorityQueueTreeSet#PriorityQueueTreeSet(Comparator)} or the
 * {@link PriorityQueueTreeSet#PriorityQueueTreeSet(Comparator, Comparator)} constructor
 *
 * @author Michael van Dyk
 */ @SuppressWarnings("unused")
public final class PriorityQueueTreeSet<E> extends AbstractQueue<E> {

    /** The queue of the data structure **/
    private final Queue<E> queue;
    /** The set of the data structure **/
    private final Set<E> set;

    /**
     * Initializes the structure, uses the type compare method
     */
    public PriorityQueueTreeSet() {
        this.queue = new PriorityQueue<>();
        this.set = new TreeSet<>();
    }

    /**
     *  Initializes the structure, uses the given comparator for ordering
     * @param comparator the ordering
     */
    public PriorityQueueTreeSet(Comparator<E> comparator) {
        this(comparator, comparator);
    }

    /**
     *  Initializes the structure, uses the given comparators for the ordering.
     * This allows for separate ordering in the queue and set.
     * @param queue_comp the queue ordering
     * @param set_comp   the set ordering
     */
    public PriorityQueueTreeSet(Comparator<E> queue_comp, Comparator<E> set_comp) {
        queue = new PriorityQueue<>(queue_comp);
        set = new TreeSet<>(set_comp);
    }

    @Override
    public boolean add(E item) {
        return (set.add(item) && queue.add(item));
    }

    @Override
    public Iterator<E> iterator() {
        return (queue.iterator());
    }

    @Override
    public boolean offer(E e) {
        return (queue.offer(e) && set.add(e));
    }

    @Override
    public E peek() {
        return (queue.peek());
    }

    @Override
    public E poll() {
        E item = queue.poll();
        set.remove(item);
        return (item);
    }

    @Override
    public int size() {
        return (set.size());
    }
}
