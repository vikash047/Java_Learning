package Interview.IO.DistrubutedCache;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

public class temp implements Comparable, Iterator, Cloneable, Closeable {

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }

    @FunctionalInterface
    public interface Do {
        Integer function(Integer a, Integer b);
    }

    Do f = (a, b) -> a + b;
    public void function(Do object) {
    }
}
