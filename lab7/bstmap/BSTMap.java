package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private int size;

    private Node root;

    private class Node {
        public K key;
        public V value;
        public Node left, right;

        public Node() {
        }

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public void printInOrder() {

    }

    /**
     * 从此地图中删除所有映射。
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }



    /**
     * 如果此映射包含指定键的映射，则返回 true。
     */
    @Override
    public boolean containsKey(K key) {
        Node node = get(root, key);

        return node != null;
    }

    private Node get(Node node, K key) {
        if (node == null) {
            return null;
        }
        int compare = key.compareTo(node.key);
        if (compare < 0) {
            return get(node.left, key);
        } else if (compare > 0) {
            return get(node.right, key);
        } else {
            return node;
        }
    }

    /**
     * 返回指定键映射到的值，如果此映射不包含该键的映射，则返回 null。
     */
    @Override
    public V get(K key) {
        Node node = get(root, key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    /**
     * 返回此映射中键值映射的数量。
     */
    @Override
    public int size() {
        return size;
    }

    private Node add(Node node, K key, V value) {
        if (node == null) {
            ++size;
            return new Node(key, value);
        }
        int compare = key.compareTo(node.key);
        if (compare < 0) {
            node.left = add(node.left, key, value);
        } else if (compare > 0) {
            node.right = add(node.right, key, value);
        } else {
            node.value = value;
        }
        return node;
    }

    /**
     * 将指定的值与此映射中的指定键相关联。
     */
    @Override
    public void put(K key, V value) {
        if (root == null) {
            root = new Node(key, value);
            ++size;
        } else {
            add(root, key, value);
        }
    }

    /**
     * 返回此映射中包含的键的 Set 视图。实验室 7 不需要。如果您不实现此功能，请抛出 UnsupportedOperationException。
     */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /**
     * 如果存在，则从此映射中删除指定键的映射。实验室 7 不需要。如果您不实现此功能，请抛出 UnsupportedOperationException。
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * 仅当当前映射到指定值时，才删除指定键的条目。实验室 7 不需要。如果您不实现此功能，请抛出 UnsupportedOperationException。
     */
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    private int dg(K[] k, Node node, int js) {
        if (node == null) {
            return js;
        }

        js = dg(k, node.left, js);
        js = dg(k, node.right, js);
        k[js] = node.key;
        --js;
        return js;
    }

    @Override
    public Iterator<K> iterator() {

        throw new UnsupportedOperationException();
    }
}
