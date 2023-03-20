package CodingQuestions;

import com.sun.source.tree.Tree;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.AbstractExecutorService;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class OpenLocksTest {


    private <K extends Comparable<K>> int lowerBound(List<K> lst, K key) {
        int l = 0, r = lst.size() - 1;
        while(l < r) {
            int mid = (l + r) >> 1;
            if(key.compareTo(lst.get(mid)) <= 0) {
                r  = mid;
            } else {
               l = mid + 1;
            }
        }
        System.out.println(lst.get(l));
        return lst.get(l).compareTo(key) >= 0 ? l : lst.size();
    }

    private <K extends Comparable<K>> int upperBound(List<K> lst, K key) {
        int l = 0, r = lst.size() - 1;
        while(l < r) {
            int mid = (l + r) >> 1;
            if(key.compareTo(lst.get(mid)) >= 0) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        //System.out.println(lst.get(l));
        return lst.get(l).compareTo(key) >= 0 ? l : lst.size();
    }


    @Test
    void testUpperBound() {
       /* Random r = new Random(0);
        for(int i = 0; i < 11; i++) {
            System.out.println(r.nextInt(10));
        }*/
        //Comparator<Integer> cmp = Comparator.comparingInt(x -> x)
        TreeMap<Integer, Integer> s = new TreeMap<>();
        s.pollFirstEntry();
        int[] a = {1, 2, 3, 3, 3, 4, 5, 5, 5, 5, 5, 5, 7, 9};
        var b = List.of(Arrays.stream(a).boxed().toArray(Integer[]::new));
        List<Double> doubles = new ArrayList<>();

        System.out.println(upperBound(b, 5));
    }

    @Test
    void testLowerBound() {
        int[] a = {1, 2, 3, 3, 3, 5, 5, 5, 5, 5, 5, 7, 9};
        var b = List.of(Arrays.stream(a).boxed().toArray(Integer[]::new));
        Arrays.binarySearch(a, 0, a.length, 5);
        System.out.println(lowerBound(b, 4));
    }
    @Test
    void openLock() {
        OpenLocks openLocks = new OpenLocks();
        String[] arr = {"0201","0101","0102","1212","2002"};
        System.out.println(openLocks.openLock(arr,"0202"));
        List<Integer> a = new ArrayList<>();
    }
}