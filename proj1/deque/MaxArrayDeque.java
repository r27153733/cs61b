package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> defaultComparator;

    public MaxArrayDeque(Comparator<T> c) {
        defaultComparator = c;
    }

    public T max() {
        return max(defaultComparator);

    }

    public T max(Comparator<T> c) {
        T max = get(0);
        for (int i = 1; i < size(); i++) {

            max = (c.compare(max, get(i)) > 0) ? max : get(i);
        }
        return max;
    }
}
