package deque;

import java.util.Iterator;

public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int firstIndex;
    private int startNull;
    public ArrayDeque() {
        items = (T[])new Object[50000000];
    }

    /**添加对象到最前面*/
    public void addFirst(T item){
        if(startNull == 0){
            if(isEmpty()){
                items[0] = item;
                size++;
                return;
            }
            items[items.length - 1 - firstIndex] = item;
            firstIndex++;

        } else {
            items[startNull - 1] = item;
            startNull--;
        }
        size++;
    }
    /**添加对象到最后面*/
    public void addLast(T item){
        items[size + startNull - firstIndex] = item;
        size++;
    }
    /**列表是否为空*/
    public boolean isEmpty(){
        return size == 0;
    }
    /**列表大小*/
    public int size(){
        return size;
    }
    /**从头到尾打印列表中的项目，用空格分隔。打印完所有项目后，换行。*/
    public void printDeque(){
        for (int i = firstIndex; i > 0; i--) {
            System.out.print(items[size - firstIndex] + " ");
        }
        for (int i = startNull; i < size - firstIndex + startNull; i++) {
            System.out.println(items[i] + " ");
        }
        System.out.println();
    }
    /**删除并返回位于最前面的对象。如果不存在此类对象，则返回 null*/
    public T removeFirst(){
        if(size != 0){
            T res;
            if(firstIndex == 0){
                res = items[startNull];
                items[startNull] = null;
                startNull++;

            } else {
                res = items[items.length - firstIndex];
                firstIndex--;
                items[items.length - firstIndex] = null;
            }

            size--;
            return res;
        }
        return null;
    }
    /**删除并返回位于最后面面的对象。如果不存在此类对象，则返回 null*/
    public T removeLast(){
        if(size != 0){
            T res;
            if(firstIndex == 0) {
                res = items[startNull + size - 1];
                items[startNull + size - 1] = null;
            } else {
                res = items[size - firstIndex];
                items[size - firstIndex] = null;
            }


            size--;
            return res;
        }
        return null;
    }
    /**获取下标对应的对象*/
    public T get(int index){
        if(firstIndex != 0 && index <= firstIndex){
            return items[items.length - firstIndex + index];
        } else {
            return items[startNull + index - firstIndex];
        }


    }
    /**返回列表的迭代器*/
    public Iterator<T> iterator(){
        return null;
    }
    /**是否相等*/
    public boolean equals(Object o){
        return false;
    }
}
