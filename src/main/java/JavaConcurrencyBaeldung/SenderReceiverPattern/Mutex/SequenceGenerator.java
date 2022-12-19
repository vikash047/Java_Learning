package JavaConcurrencyBaeldung.SenderReceiverPattern.Mutex;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class SequenceGenerator {
    private Logger logger = Logger.getLogger(SequenceGenerator.class);
    // unsafe method multiple thread can enter in the critical section.
    public class Sequence{
        private int counter = 0;
        public int getNextCounter() {
            counter = counter + 1;
            return counter;
        }
    }
    // safe method using synchronized method.
    public class SequenceSynch extends Sequence{
        public synchronized int getNextCounter() {
            return super.getNextCounter();
        }
    }
    // safe method using object.
    public class SequenceSynchObject extends Sequence{
        private Object object = new Object();
        public int getNextCounter() {
            synchronized (object) {
                return super.getNextCounter();
            }
        }
    }
    // safe method using ReentrantLock
    public class SequenceReetLock extends Sequence{
        private ReentrantLock mutex = new ReentrantLock();
        public int getNextCounter() {
            try {
                mutex.lock();
                return super.getNextCounter();
            } finally {
                mutex.unlock();
            }
        }
    }

    // safe method using semaphore
    public class SequenceSemaphore extends Sequence {
        private Semaphore mutex = new Semaphore(1);
        public int getNextCounter() {
            try {
                mutex.acquire();
                return super.getNextCounter();
            } catch (InterruptedException ex) {
                logger.trace(ex.getMessage());
            } finally {
                mutex.release();
            }
            return -1;
        }
    }
    public void Generate() throws ExecutionException, InterruptedException {
        int count = 1000;
        Set<Integer> set = GenerateSeq(new SequenceSemaphore(), count);
        System.out.println(set.size());
        /*for(Integer e : set) {
            System.out.println("value is " + e);
        }*/
    }

    private Set<Integer> GenerateSeq(Sequence sequence, int count) throws ExecutionException, InterruptedException {
        ExecutorService ex = Executors.newFixedThreadPool(3);
        Set<Integer>  useq = new LinkedHashSet<>();
        List<Future<Integer>> futures = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            futures.add(ex.submit(sequence::getNextCounter));
        }
        for (Future<Integer> fr : futures) {
            useq.add(fr.get());
        }
        ex.awaitTermination(1, TimeUnit.SECONDS);
        ex.shutdown();
        return useq;
    }
}
