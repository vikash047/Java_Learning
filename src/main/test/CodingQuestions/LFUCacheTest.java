package CodingQuestions;

import com.google.common.collect.TreeMultimap;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

class LFUCacheTest implements Comparable<Integer> {

    @Test
    void add() {
       Map.Entry<Integer, Integer> e1 = new AbstractMap.SimpleEntry<>(1, 1);
        Map.Entry<Integer, Integer> e2 = Map.entry(1, 1);
        Set<Map.Entry<Integer, Integer>> s = new HashSet<>();
        s.add(e1);
        s.add(e2);
        System.out.println(s.size());
        List<Integer> list = new ArrayList<>();
        Collections.binarySearch(list, 1);
        var intlist = list.stream().mapToInt(x -> x).toArray();
        List<String> stringList = new ArrayList<>();
        var e = stringList.toArray(String[] :: new);
        int[] a = {0, 1};
        int[] c = {1, 0};
        var tass =  Arrays.stream(a).boxed().toArray(Integer[]::new);
        List<Integer[]> b = new ArrayList<>();
        b.add(Arrays.stream(a).boxed().toArray(Integer[]::new));
        b.add(Arrays.stream(a).boxed().toArray(Integer[]::new));
        var d = b.stream().toArray(Integer[][]::new);
        System.out.println(d.length + " " + d[0].length);
        System.out.println(e1.equals(e2) + "new case is " + (e1 == e2));
        List<Integer> ans = new LinkedList<>();
        ans.remove(2);
        Character.toString('a');
        List<Character> characterList = new ArrayList<>();
        int[] res = ans.stream().mapToInt(x -> (Integer)x).toArray();
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2.compareTo(o1));
        pq.remove(1);
        TreeMap<Integer, Integer> ts = new TreeMap<>();
        Integer.compare(1, 2);
        ts.remove(1);
        ts.entrySet();
        var entry = Map.entry(1, 1);
        TreeMultimap<Integer, Integer> multimap = TreeMultimap.create();
        multimap.asMap().firstKey();
        ConcurrentSkipListSet<Integer> concurrentSkipListSet = new ConcurrentSkipListSet<>();
        int[] ba = {2, 3, 6, 10};
        var arrayToList = Arrays.stream(ba).boxed().collect(Collectors.toList());
        int index = Arrays.binarySearch(ba, 0, 4, 12);
       System.out.println(index);
       /* for (var e : s) {
            System.out.println(e);
        }*/
        StringBuilder str = new StringBuilder();
        str.appendCodePoint(1);
        str.append(a);
        str.hashCode();
        String st = str.toString();
        st.substring(0);
        st.toCharArray();
        st.charAt(2);
        StringTokenizer tokenizer = new StringTokenizer(st, "/");
        while (tokenizer.hasMoreTokens()) {
               var tt = tokenizer.nextToken();
        }
        var carr = str.toString().toCharArray();
        Stack<Integer> stack = new Stack<>();
        //stack.empty();
        Deque<Integer> deque = new LinkedList<>();
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        Queue<Integer> queue = new ArrayDeque<>();
        TreeMap<Integer, Integer> mp = new TreeMap<>();
        Map.Entry<Integer, Integer> entry1 = Map.entry(1, 1);
        Random rnd = new Random();
        rnd.nextInt(10, 2);
        Map<Integer, Integer> map = new HashMap<>();
        map.get(1);
        Map.Entry<Integer, Integer>[] entries = new Map.Entry[2];
        map.computeIfAbsent(1, k -> 2);
        Objects.hash(map, rnd);
        map.clear();
        List<List<Integer>> listList = new ArrayList<>();
        Comparator<Integer> cmp = (Comparator<Integer>) Comparator.naturalOrder().reversed();
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.floor(1);
    }

 @Override
 public int compareTo(Integer o) {
  return 0;
 }

 class Interval implements Comparable<Interval> {
  int left, right, n;

  public Interval(int l, int r, int n) {
   this.left = l;
   this.right = r;


   this.n = n;
  }



  @Override
  public int compareTo(Interval o) {
   return 0;
  }
 }
}