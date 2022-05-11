package JavaConcepts;

import java.util.*;

public class JavaCollectionsPractices {
    public static void main(String[] args)
    {
        //ArrayListP();
        //stack();
        //queue();
        //priorityQueue();
        //deque();
        //set();
        map();
    }

//    public Integer lowerBound(ArrayList<Integer> arr, int key) {
//
//    }

    public static void ArrayListP() {
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(1);
        arr.add(2);
        arr.add(4);
        arr.add(8);
        arr.add(10);
        for(Integer e : arr) {
            System.out.println(e);
        }
        System.out.println(arr.get(4));
        int index = Collections.binarySearch(arr, 3);
        System.out.println(index);
        index = Collections.binarySearch(arr, 4);
        System.out.println(index);
        Collections.sort(arr, new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return  o2.compareTo(o1);
            }
        });
        for(Integer e : arr) {
            System.out.println(e);
        }
        //Collections.
    }

    public static void stack() {
        Stack<Integer> st = new Stack<>();
        st.add(10);
        st.add(2);
        st.add(4);
        st.add(6);
        st.add(23);
        System.out.println(st.peek());
        System.out.println(st.pop());
        st.push(3);
        System.out.println(st.peek());
        while (!st.isEmpty()) {
            System.out.println(st.pop());
        }
    }
     public static void queue() {
         Queue<Integer> pq = new LinkedList<>();
         pq.add(3);
         pq.add(5);
         pq.add(7);
         System.out.println(pq.size());
         System.out.println(pq.peek());
         System.out.println(pq.poll());
         System.out.println(pq.peek());
         pq.offer(2);
         System.out.println(pq.peek());
         while (!pq.isEmpty()) {
             System.out.println(pq.peek());
             pq.poll();
         }
         pq.offer(3);
     }
     public static void priorityQueue() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(3);
        pq.add(30);
        pq.add(4);
        System.out.println(pq.peek());
         System.out.println(pq.size());
         //System.out.println(pq.);
         pq = new PriorityQueue<>(new Comparator<Integer>() {
             public int compare(Integer o1, Integer o2) {
                 return o2 < o1 ? -1 : o2 == o1 ? 0 : 1;
             }
         });
         pq.add(30);
         pq.add(2);
         pq.add(3);
         System.out.println(pq.peek());
         pq.poll();
         pq.offer(2);
         System.out.println(pq.peek());
     }
     public static void deque() {
        Deque<Integer> pq = new LinkedList<>();
        pq.offer(3);
        pq.offer(4);
        pq.offer(5);
        pq.offer(6);
         System.out.println(pq.peekFirst());
         System.out.println(pq.peekLast());
         pq.pollFirst();
         System.out.println(pq.peekFirst());
     }
     public static void set() {
        Set<Integer> st = new HashSet<>();
        st.add(3);
        st.add(4);
        st.add(7);
        st.add(9);
        for(Integer e : st) {
            System.out.println(e);
        }
//        if(st.contains(4))  {
//            System.out.println(st.);
//        }
        TreeSet<Integer> tst =  new TreeSet<>();
        tst.add(4);
        tst.add(10);
        tst.add(3);
        tst.add(20);
        tst.add(1);
        Iterator<Integer> itr = tst.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
         System.out.println(tst.floor(4));
         System.out.println(tst.ceiling(4));
         System.out.println(tst.higher(4));
         System.out.println(tst.lower(4));
     }
     public static void map() {
        Map<Integer, Integer> mp = new LinkedHashMap<>();
        mp.put(1, 2);
        mp.put(2, 4);
        mp.put(3, 5);
        mp.replace(1, 5);
        for(Map.Entry<Integer, Integer> kv : mp.entrySet()) {
            System.out.println(kv.getKey() + " --> " + kv.getValue());
        }
        if(mp.containsKey(2)) {
            System.out.println(mp.get(2));
        }

        TreeMap<Integer, Integer> tmp = new TreeMap<>();
        tmp.put(2, 3);
        tmp.put(10, 4);
        tmp.put(3, 5);
        tmp.put(6, 8);
        tmp.put(1, 5);
        for(Map.Entry<Integer, Integer> kv : tmp.entrySet()) {
            System.out.println(kv.getKey() + " --> " + kv.getValue());
        }
        if(tmp.containsKey(10)) {
            System.out.println(tmp.get(10));
        }
         System.out.println(tmp.floorEntry(4).getValue());
         System.out.println(tmp.ceilingEntry(4).getValue());
         System.out.println(tmp.lowerEntry(3).getValue());
         System.out.println(tmp.higherEntry(3).getValue());
     }
}


