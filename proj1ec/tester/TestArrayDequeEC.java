package tester;

import static org.junit.Assert.*;

import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {
    @Test
    public void randomTest() {
        StudentArrayDeque<Integer> s = new StudentArrayDeque<>();
//        ArrayDequeSolution<Integer> s = new ArrayDequeSolution<>();
        ArrayDequeSolution<Integer> t = new ArrayDequeSolution<>();
        StringBuilder sb = new StringBuilder();
        int actual = StdRandom.uniform(100);
        int actual1 = StdRandom.uniform(10);
        for (int i = 0; i < 100; i++) {
            if (i % 4 == 0) {
                t.addFirst(i + actual1);
                s.addFirst(i + actual1);
                sb.append("addFirst(").append(i + actual1).append(")\n");
                assertEquals(sb.toString(), t.get(0), s.get(0));
            } else if (i % 3 == 0) {
                t.addLast(i + actual1);
                s.addLast(i + actual1);
                sb.append("addLast(").append(i + actual1).append(")\n");
                assertEquals(sb.toString(), t.get(t.size() - 1), s.get(s.size() - 1));
            } else if (i % 2 == 0 && t.size() != 0) {
                Integer integerT = t.removeFirst();
                Integer integerS = s.removeFirst();
                sb.append("removeFirst()\n");
                assertEquals(sb.toString(), integerT, integerS);
            } else if (i % 5 == 0 && t.size() != 0) {
                Integer integerT = t.removeLast();
                Integer integerS = s.removeLast();
                assertEquals(sb.toString(), integerT, integerS);
                sb.append("removeLast()\n");
            }
            actual1 = StdRandom.uniform(10);
        }
    }
}
