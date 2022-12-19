// TreeMap
// Priority Queue
// Comparable
// Comprator
// Queue
// Dequeue
// ArrayList
// InPut and output
//Hash Map
// Concurrent hashMap
// Locking CAS
// Set
// string operation

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

public class JavaCollectionsPractice {

    public  void CallFunctions()
    {
        //ArrayListP();
        //StackP()QueueP();
        //PriorityQueueP();
       // DequeP();
        //SetAllType();
        MapAllType();
        Random rm = new Random();
        int id = rm.nextInt(100);
    }
    public void ArrayListP()
    {
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(1);
        arr.add(2);
        arr.add(6);
        arr.add(3);
        arr.add(9);
        arr.add(8);
        for(Integer e : arr) {
            System.out.print(e + " ");
        }
        System.out.println(arr.get(4));
        Collections.sort(arr);
        for(Integer e : arr) {
            System.out.print(e + " ");
        }
        int index = Collections.binarySearch(arr, 2);
        System.out.println(index);
        Collections.sort(arr, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });
        for(Integer e : arr) {
            System.out.print(e + " ");
        }
    }
    public void StackP()
    {
        Stack<Integer> st = new Stack<>();
        st.add(1);
        st.add(2);
        st.add(3);
        st.add(4);
        System.out.println(st.peek());
        st.pop();
        System.out.println(st.peek());
    }
    public void QueueP()
    {
        Queue<Integer> pq = new ArrayDeque<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        pq.add(4);
        System.out.println(pq.size());
        System.out.println(pq.peek());
        pq.offer(1);
        System.out.println(pq.poll());
        System.out.println(pq.peek());
        System.out.println(pq.peek());

    }
    public void PriorityQueueP()
    {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(1);
        pq.offer(2);
        pq.offer(10);
        pq.offer(3);
        System.out.println(pq.peek());
        pq.poll();
        System.out.println(pq.peek());
        System.out.println(pq.size());
        PriorityQueue<Integer> mpq = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 < o1 ? -1 : (o2 == o1 ? 0 : 1);
            }
        });
        mpq.add(1);
        mpq.offer(2);
        mpq.offer(4);
        mpq.offer(6);
        mpq.offer(6);
        System.out.println(mpq.size());
        System.out.println(mpq.peek());
        mpq.poll();
        System.out.println(mpq.peek());
    }
    public void DequeP()
    {
        int[] nums = new int[45];
        System.out.println(nums.length);
        Deque<Integer> pq = new LinkedList<>();
        pq.offer(1);
        pq.addFirst(1);
        pq.addLast(2);
        pq.addFirst(3);
        System.out.println(pq.size());
        System.out.println(pq.peek());
        System.out.println(pq.getLast());
        pq.offerFirst(3);
        System.out.println(pq.peekFirst());
        pq.offerLast(4);
        System.out.println(pq.peekLast());
    }
    public void SetAllType() {
        Set<Integer> st = new HashSet<>();
        st.add(2);
        st.add(3);
        st.add(6);
        System.out.println(st.size());
        for(Integer e : st) {
            System.out.println(e);
        }
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(4);
        treeSet.add(6);
        treeSet.add(8);
        treeSet.add(1);
        treeSet.add(3);
        for(Integer e : treeSet) {
            System.out.println(e);
        }
        Iterator<Integer> itr = treeSet.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
        System.out.println(treeSet.floor(7));
        System.out.println(treeSet.ceiling(8));
        System.out.println(treeSet.higher(8));
        System.out.println(treeSet.lower(8));
    }
    public void MapAllType()
    {
        Map<Integer, Integer> mp = new HashMap<>();
        mp.put(1, 2);
        mp.put(2, 3);
        mp.put(3, 4);
        mp.put(4, 5);
        for(Map.Entry<Integer, Integer> k : mp.entrySet()) {
            System.out.println(k.getKey() + " " + k.getValue());
        }
//        for(Integer key : mp.keySet()) {
//            System.out.println(mp.get(key));
//        }
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        treeMap.put(1, 2);
        treeMap.put(8, 9);
        treeMap.put(3, 5);
        treeMap.put(6, 9);
        treeMap.put(11, 2);
        System.out.println(treeMap.ceilingKey(6));
        System.out.println(treeMap.floorKey(4));
        System.out.println(treeMap.higherKey(6));
        System.out.println(treeMap.lowerKey(6));
        for(Map.Entry<Integer, Integer> entry : treeMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        treeMap.put(6, 6);

        System.out.println(treeMap.ceilingEntry(6).getValue());

        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        LinkedTransferQueue<Integer> transferQueue = new LinkedTransferQueue<>();
        transferQueue.offer(15, 5, TimeUnit.SECONDS);
    }
}
