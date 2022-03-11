package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst = 4;
    private int nextLast = 5;

    public ArrayDeque() {
        items = (T[]) new Object[8];
    }

    private boolean checkSize() {
        return items.length - size == 2;
    }

    private boolean checkSize2() {
        if (items.length <= 8) {
            return false;
        }
        return (double) size / (double) items.length < 0.25;
    }

    private void adjustSize(int newSize) {
        if (newSize < 8) {
            newSize = 8;
        }
        T[] newItems = (T[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            newItems[newItems.length / 2 + i] = items[changeNext(i, nextFirst + 1)];
        }
        items = newItems;
        nextFirst = items.length / 2 - 1;
        nextLast = changeNext(size + 1, nextFirst);
    }

    private int changeNext(int change, int next) {
        int newNext = next;
        newNext += change;
        if (newNext < 0) {
            return items.length + newNext;
        } else {
            return newNext % items.length;
        }
    }


    /**
     * 添加对象到最前面
     */
    public void addFirst(T item) {

        items[nextFirst] = item;
        nextFirst = changeNext(-1, nextFirst);
        ++size;
        if (checkSize()) {
            adjustSize(size * 2);
        }
    }

    /**
     * 添加对象到最后面
     */
    public void addLast(T item) {

        items[nextLast] = item;
        nextLast = changeNext(1, nextLast);
        ++size;
        if (checkSize()) {
            adjustSize(size * 2);
        }
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
        int index;
        for (int i = 0; i < size; i++) {

            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    /**
     * 删除并返回位于最前面的对象。如果不存在此类对象，则返回 null
     */
    public T removeFirst() {
        int index = changeNext(1, nextFirst);
        if (items[index] == null) {
            return null;
        }
        T res = items[index];
        items[index] = null;
        --size;
        nextFirst = index;
        if (checkSize2()) {
            adjustSize(size * 2);
        }
        return res;
    }

    /**
     * 删除并返回位于最后面面的对象。如果不存在此类对象，则返回 null
     */
    public T removeLast() {
        int index = changeNext(-1, nextLast);
        if (items[index] == null) {
            return null;
        }
        T res = items[index];
        items[index] = null;
        --size;
        nextLast = index;
        if (checkSize2()) {
            adjustSize(size * 2);
        }
        return res;
    }

    /**
     * 获取下标对应的对象
     */
    public T get(int index) {
        int index1 = changeNext(index, nextFirst + 1);
        return items[index1];
    }

    /**
     * 返回列表的迭代器
     */
    public Iterator<T> iterator() {


        return new Iterator<T>() {
            private int index;

            @Override
            public boolean hasNext() {

                return get(index) != null;
            }

            @Override
            public T next() {
                T res = get(index);
                index = changeNext(1, index);
                return res;
            }
        };
    }

    /**
     * 是否相等
     */
    public boolean equals(Object o) {
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
