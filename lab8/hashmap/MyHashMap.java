package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    private class IteratorHash<K> implements Iterator<K> {
        K[] k = (K[]) new Object[size];
        int j;
        public IteratorHash() {
            int i = 0;
            for (Collection<Node> nodes : buckets) {
                for (Node node : nodes){
                    k[i++] = (K) node.key;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return j < k.length;
        }

        @Override
        public K next() {
            return k[j++];
        }

    }

    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return key.equals(node.key) && value.equals(node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int size;

    private int initialSize = 8;

    private double maxLoad = 0.75;
    /** Constructors */
    public MyHashMap() {
        buckets = createTable(8);
    }

    public MyHashMap(int initialSize) {
        this.initialSize = initialSize;
        buckets = createTable(initialSize);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.initialSize = initialSize;
        this.maxLoad = maxLoad;
        buckets = createTable(initialSize);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] nodes = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            nodes[i] = createBucket();
        }
        return nodes;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    private int hash(int hashcode){
        return Math.floorMod(hashcode, buckets.length);
    }

    private int hash(int hashcode, Collection<Node>[] buckets){
        return Math.floorMod(hashcode, buckets.length);
    }

    @Override
    public void clear() {
        size = 0;
        buckets = createTable(initialSize);
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        Collection<Node> bucket = buckets[hash(key.hashCode())];
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    private void rePut(K key, V value, Collection<Node>[] buckets) {
        Node node = createNode(key, value);
        Collection<Node> bucket = buckets[hash(node.key.hashCode(), buckets)];
        for (Node node1 : bucket) {
            if (node1.key.equals(key)) {
                node1.value = value;
                return;
            }
        }
        bucket.add(node);
    }

    private void checkSize() {
        if ((double)size / buckets.length >= maxLoad) {
            resize(buckets.length * 2);
        }
    }

    private void resize(int newSize) {
        Collection<Node>[] newBuckets = createTable(newSize);
        for (K key : this) {
            rePut(key, get(key), newBuckets);
        }
        buckets = newBuckets;
    }

    @Override
    public void put(K key, V value) {
        Node node = createNode(key, value);
        Collection<Node> bucket = buckets[hash(node.key.hashCode())];
        for (Node node1 : bucket) {
            if (node1.key.equals(key)) {
                node1.value = value;
                return;
            }
        }
        bucket.add(node);
        ++size;
        checkSize();
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (K myHashMap : this) {
            set.add(myHashMap);
        }
        return set;
    }

    @Override
    public V remove(K key) {
        V v = get(key);

        return remove(key, v);
    }

    @Override
    public V remove(K key, V value) {

        if (buckets[hash(key.hashCode())].remove(new Node(key, value))) {
            --size;
            return value;
        }

        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new IteratorHash<>();
    }

}
