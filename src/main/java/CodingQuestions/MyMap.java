package CodingQuestions;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyMap {
    private Map<Integer, Integer> map;
    private PriorityQueue<Entry> expirationQueue;
    private long totalSum;
    private int count;
    private ReentrantLock mapLock;
    private ReentrantLock expirationLock;
    private Condition notEmpty;
    private Thread expirationThread;

    private static class Entry implements Comparable<Entry> {
        int key;
        int value;
        long expirationTime;

        Entry(int key, int value, long expirationTime) {
            this.key = key;
            this.value = value;
            this.expirationTime = expirationTime;
        }

        @Override
        public int compareTo(Entry other) {
            return Long.compare(this.expirationTime, other.expirationTime);
        }
    }

    public MyMap() {
        this.map = new HashMap<>();
        this.expirationQueue = new PriorityQueue<>();
        this.totalSum = 0L;
        this.count = 0;
        this.mapLock = new ReentrantLock();
        this.expirationLock = new ReentrantLock();
        this.notEmpty = expirationLock.newCondition();
        startExpirationThread();
    }

    private void startExpirationThread() {
        expirationThread = new Thread(() -> {
            expirationLock.lock();
            try {
                while (true) {
                    long now = System.currentTimeMillis();
                    while (!expirationQueue.isEmpty() && expirationQueue.peek().expirationTime <= now) {
                        Entry expiredEntry = expirationQueue.poll();
                        mapLock.lock();
                        try {
                            map.remove(expiredEntry.key);
                            totalSum -= expiredEntry.value;
                            count--;
                        } finally {
                            mapLock.unlock();
                        }
                    }
                    var time =  !expirationQueue.isEmpty() ? expirationQueue.peek().expirationTime - now : 1000;
                    notEmpty.await(time, TimeUnit.MILLISECONDS);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                expirationLock.unlock();
            }
        });
        expirationThread.setDaemon(true);
        expirationThread.start();
    }

    public void put(int key, int value, long ttl) {
        mapLock.lock();
        try {
            long expirationTime = System.currentTimeMillis() + ttl;
            map.put(key, value);
            totalSum += value;
            count++;
            expirationLock.lock();
            try {
                expirationQueue.offer(new Entry(key, value, expirationTime));
                notEmpty.signalAll();
            } finally {
                expirationLock.unlock();
            }
        } finally {
            mapLock.unlock();
        }
    }

    public int get(int key) {
        mapLock.lock();
        try {
            return map.getOrDefault(key, -1);
        } finally {
            mapLock.unlock();
        }
    }

    public double calculateAvg() {
        mapLock.lock();
        try {
            return (double) totalSum / count;
        } finally {
            mapLock.unlock();
        }
    }
}

/*
public class MyMap {
    private final Map<Integer, Node>[] segments;
    private final SegmentLock[] segmentLocks;
    private final int numSegments;
    private final ReentrantLock notEmptyLock;
    private final Condition notEmpty;

    public MyMap(int numSegments) {
        this.numSegments = numSegments;
        this.segments = new HashMap[numSegments];
        this.segmentLocks = new SegmentLock[numSegments];
        for (int i = 0; i < numSegments; i++) {
            segments[i] = new HashMap<>();
            segmentLocks[i] = new SegmentLock();
        }
        this.notEmptyLock = new ReentrantLock();
        this.notEmpty = notEmptyLock.newCondition();
    }

    public void put(int key, int value, long ttl) {
        int segment = getSegment(key);
        SegmentLock segmentLock = segmentLocks[segment];
        Node newNode = new Node(key, value, System.currentTimeMillis() + ttl);

        Node previous = null;
        Node current = null;
        segmentLock.lock();
        try {
            current = segments[segment].get(key);
            while (current != null && current.isExpired()) {
                // Remove expired node
                if (previous == null) {
                    segments[segment].remove(key);
                } else {
                    previous.setNext(current.getNext());
                }
                current = current.getNext();
            }
            if (current != null) {
                // Key exists and not expired, update value and expiration time
                current.setValue(value);
                current.setExpirationTime(System.currentTimeMillis() + ttl);
            } else {
                // Key does not exist or expired, insert new node
                newNode.setNext(segments[segment].get(key));
                segments[segment].put(key, newNode);
                notEmpty.signalAll();
            }
        } finally {
            segmentLock.unlock();
        }
    }

    public int get(int key) {
        int segment = getSegment(key);
        SegmentLock segmentLock = segmentLocks[segment];

        segmentLock.lock();
        try {
            Node current = segments[segment].get(key);
            while (current != null && current.isExpired()) {
                // Remove expired node
                segments[segment].remove(key);
                current = current.getNext();
            }
            if (current != null) {
                // Key exists and not expired, return value
                return current.getValue();
            } else {
                // Key does not exist or expired, return default value
                return 0;
            }
        } finally {
            segmentLock.unlock();
        }
    }

    public double calculateAvg() {
        double sum = 0;
        int count = 0;
        for (int i = 0; i < numSegments; i++) {
            SegmentLock segmentLock = segmentLocks[i];
            segmentLock.lock();
            try {
                for (Node node : segments[i].values()) {
                    if (!node.isExpired()) {
                        sum += node.getValue();
                        count++;
                    }
                }
            } finally {
                segmentLock.unlock();
            }
        }
        return count == 0 ? 0 : sum / count;
    }

    private int getSegment(int key) {
        return key % numSegments;
    }

    private class Node {
        private final int key;
        private int value;
        private long expirationTime;
        private Node next;

        public Node(int key, int value, long expirationTime) {


 */

