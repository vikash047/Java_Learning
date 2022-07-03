package JavaConcepts;

import java.util.*;

public class JavaCollectionsExamples {
    public static void main(String[] args) {
        int[] arr = new int[2];
        List<Integer> list = new LinkedList<>();
        ArrayList<Integer> arrayList =   new ArrayList<>();
        LinkedList<Integer> linkedList = new LinkedList<>();
        Arrays.fill(args, -1);
        Set<Integer> hashSet = new HashSet<>();
        TreeSet<Integer> sortedSet = new TreeSet<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        Map<Integer, String> mp = new HashMap<>();
        SortedMap<Integer, String> smp = new TreeMap<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Deque<Integer> spq = new ArrayDeque<>();
        Stack<Integer> st = new Stack<>();
        Stack<Character> cst = new Stack<>();
        //st.
       // Map<Integer, Integer> mmp = new HashMultiMap<>

    }
}
