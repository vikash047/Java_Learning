package PhonePeToDO.Exceptions;

import java.util.*;

public class SynchronizedBlockExample {
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        // Create a thread that will manipulate a shared resource
        Thread workerThread = new Thread(() -> {
            try {
                performTask();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread workerThread2 = new Thread(() -> {
            try {
                performTask();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Deque<Integer> pq = new ArrayDeque<>();

        // Start the worker thread
        workerThread.start();
        workerThread2.start();
        workerThread2.join();
        workerThread.join();
    }

    private static void performTask() throws InterruptedException {
        synchronized (lock) {
            System.out.println("Thread " + Thread.currentThread().getId() + " acquired the lock.");
            // Simulate some work
            Thread.sleep(1000);
            String s = "abc";
            List<Integer> list = new ArrayList<>();
            //list.set(1, 2)
            // Attempt to re-enter the lock (will not work)
            TreeSet<Integer> tree = new TreeSet<>();
            tree.retainAll(tree);
            StringBuilder br = new StringBuilder();
            synchronized (lock) {
                System.out.println("Thread " + Thread.currentThread().getId() + " tried to re-enter the lock but cannot.");
            }
        }
        System.out.println("Thread " + Thread.currentThread().getId() + " released the lock.");
    }
}
