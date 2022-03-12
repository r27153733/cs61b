package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private int size;

    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        private T getRecursive(int index) {
            if (index < 0) {
                return null;
            }
            if (index == 0) {
                return this.value;
            }
            return next.getRecursive(index - 1);
        }

        private Node() {

        }

        private Node(T value) {
            this.value = value;
        }

        private Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }


    private Node<T> se;

    public LinkedListDeque() {
        se = new Node<>(null);
        se.prev = se;
        se.next = se;


    }

    /**
     * 添加对象到最前面
     */
    public void addFirst(T item) {

        Node<T> first = new Node<>(item, se.next, se);
        se.next.prev = first;
        se.next = first;
        size++;
    }

    /**
     * 添加对象到最后面
     */
    public void addLast(T item) {
        Node<T> last = new Node<>(item, se, se.prev);
        se.prev.next = last;
        se.prev = last;

        size++;
    }

    /**
     * 列表大小
     */
    public int size() {
        return size;
    }

    /**
     * 从头到尾打印列表中的项目，用空格分隔。打印完所有项目后，换行。
     */
    public void printDeque() {
        Node<T> print = se.next;
        if (print.value != null) {
            System.out.print(print.value + " ");
            print = print.next;
        }
        System.out.println();
    }

    /**
     * 删除并返回位于最前面的对象。如果不存在此类对象，则返回 null
     */
    public T removeFirst() {
        if (!isEmpty()) {
            T res = se.next.value;
            se.next = se.next.next;
            se.next.prev = se;
            size--;
            return res;
        }
        return null;
    }

    /**
     * 删除并返回位于最后面面的对象。如果不存在此类对象，则返回 null
     */
    public T removeLast() {
        if (!isEmpty()) {
            T res = se.prev.value;
            se.prev = se.prev.prev;
            se.prev.next = se;
            size--;
            return res;
        }
        return null;
    }

    /**
     * 获取下标对应的对象
     */
    public T get(int index) {
        Node<T> k = se.next;
        for (int i = 0; i < index; i++) {

            if (k.next.value == null) {
                return null;
            }
            k = k.next;
        }
        return k.value;
    }


    public T getRecursive(int index) {
        if (index < 0) {
            return null;
        }
        if ((index + 1) > size) {
            return null;
        }
        if (isEmpty()) {
            return null;
        }
        return se.next.getRecursive(index);
    }

    /**
     * 返回列表的迭代器
     */
    public Iterator<T> iterator() {


        return new Iterator<T>() {
            Node<T> p = se.next;

            @Override
            public boolean hasNext() {

                return p.value != null;
            }

            @Override
            public T next() {
                T res = p.value;
                p = p.next;

                return res;
            }
        };
    }

    /**
     * 是否相等
     */
    public boolean equals(Object o) {
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<T> k = (Deque<T>) o;
        if (size != k.size()) {
            return false;
        } else {
            for (int i = 0; i < size; i++) {
                if (get(i).equals(k.get(i))) {
                    return false;
                }
            }
        }
        return true;
    }
}
