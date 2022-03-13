package randomizedtest;

import afu.org.checkerframework.checker.igj.qual.I;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove(){
        AListNoResizing<Integer> aListNoResizing = new AListNoResizing<>();
        BuggyAList<Integer> buggyAList = new BuggyAList<>();
        aListNoResizing.addLast(114);
        buggyAList.addLast(114);
        aListNoResizing.addLast(514);
        buggyAList.addLast(514);
        aListNoResizing.addLast(1919810);
        buggyAList.addLast(1919810);
        assertEquals(aListNoResizing.size(), buggyAList.size());

        assertEquals(aListNoResizing.removeLast(), buggyAList.removeLast());
        assertEquals(aListNoResizing.removeLast(), buggyAList.removeLast());
        assertEquals(aListNoResizing.removeLast(), buggyAList.removeLast());

    }
    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
//                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int sizeB = B.size();
//                System.out.println("size: " + size);
                assertEquals(size, sizeB);
            } else if (operationNumber == 2 && L.size() != 0) {
                // getLast
                int randVal = L.getLast();
                int randValB = B.getLast();
//                System.out.println("getLast(" + randVal + ")");
                assertEquals(randVal, randValB);
            } else if (operationNumber == 3 && L.size() != 0) {
                // removeLast
                int randVal = L.removeLast();
                int ranValB = B.removeLast();
//                System.out.println("removeLast(" + randVal + ")");
                assertEquals(randVal, ranValB);
            }
        }
    }
}
