package JMT.MultiThreadingConceptsForSE.JavaConcurrencyReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;

public class ABAProblemInReference {
    public static class Node {
        public Node(int val) {
            this.val = val;
        }

        int val;
        Node next;
    }
    private static ConcurrentLinkedQueue<Node> availableNodes = new ConcurrentLinkedQueue<>();
    private static AtomicReference<Node> head = new AtomicReference<>(null);

    public static void main( String args[] ) throws Exception {
        Node currHead = null;
        Node node = null;
        List<List<Integer>> res = new ArrayList<>();
        LinkedList<Integer> ouble = new LinkedList<>();
        // nodes are inserted by the main thread with values
        // ranging from 0 to 9
        var lst = Arrays.asList(1, 2, 3);
        for (int i = 0; i < 10; i++) {
            node = new Node(i);
            node.next = currHead;
            head.compareAndSet(currHead, node);
            currHead = node;
        }

        System.out.println("Initial list : ");
        printNodes();

        // creating Thread1
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {


                // Thread1 reads-in the current head and its next node
                Node currentHead = head.get();
                Node nextHead = currentHead.next;

                System.out.println("Thread 1 sees head = " + currentHead.val + " and head.next = " + nextHead.val);

                // sleep Thread1
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ie) {
                    // ignore
                }

                System.out.println("Thread1 about to compare and set");

                if (head.compareAndSet(currentHead, nextHead)) {
                    System.out.println("Thread1 successfully updated head. List looks as follows: ");
                    printNodes();
                } else {
                    System.out.println("CAS failed in Thread1");
                }
            }
        });

        thread1.start();

        // set-up Thread2
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {

                // wait for Thread 1 to reach its sleep statement
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    // ignore
                }

                // dequeue first five nodes from the list and place them
                // in the free nodes list
                Node currHead = null;
                for (int i = 0; i < 5; i++) {
                    currHead = head.get();
                    head.compareAndSet(currHead, currHead.next);
                    currHead.val = -1; // set to -1 to denote the node is in recycle list
                    currHead.next = null;
                    availableNodes.add(currHead);
                }

                currHead = head.get();
                Node newHead = availableNodes.remove();
                newHead.val = 99; // set a new value
                newHead.next = currHead;
                if (head.compareAndSet(currHead, newHead)) {
                    System.out.println("Thread 2 successfully updates. List is as follows : ");
                    printNodes();
                }
            }
        });

        thread2.start();

        // wait for threads to exit
        thread1.join();
        thread2.join();
    }

    // helper method to print the list
    static void printNodes() {
        Node currHead = head.get();
        boolean start = true;
        while (currHead != null) {
            if (start) {
                start = false;
                System.out.print(currHead.val + " (head) -> ");
            } else {
                System.out.print(currHead.val + " -> ");
            }
            currHead = currHead.next;
        }
        System.out.println();
    }

}
