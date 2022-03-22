package bstmap;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class BSTMapTest {
    @Test
    public void test() {
        BSTMap<Integer, String> integers = new BSTMap<>();
        integers.put(1, "a");
        assertEquals("a", integers.get(1));
    }
    @Test
    public void ssda() {
        BSTMap<String, String> integers = new BSTMap<>();
        Iterator<String> iterator = integers.iterator();

    }
}
