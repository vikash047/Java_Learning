package JMT.Semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Problem2 {
    /*
       Create a program there is n thread when all n thread reach at point then all will enter in the critical section.
       Barrier
     */
    public class Barrier {
        private int n;
        private volatile int cnt;

        private Semaphore mutex;

        private Semaphore barrier;

        private Semaphore loopAround;

        public Barrier(int _n) {
            this.n = _n;
            this.cnt = 0;
            mutex = new Semaphore(1);
            barrier = new Semaphore(0);
            loopAround = new Semaphore(0);
        }

        public void acquire() {
            try {
                mutex.acquire();
                this.cnt = this.cnt + 1;
                if(this.cnt == n) barrier.release();
                mutex.release();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                barrier.acquire();
                System.out.println("Thread execution " + Thread.currentThread().getName());
                barrier.release();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        private void release() {
            for(int i = 0; i < n; i++) {
                loopAround.release();
            }
        }
        private void phase1() throws InterruptedException {
            mutex.acquire();
            this.cnt += 1;
            if(this.cnt == n) {
                barrier.release();
            }
            mutex.release();
            barrier.acquire();
            barrier.release();
            System.out.println("Thread execution " + Thread.currentThread().getName());
        }

        private void phase2() throws InterruptedException {
            mutex.acquire();
            this.cnt -= 1;
            if(this.cnt == 0) {
               // barrier.wait();
                release();
            }
            mutex.release();
            loopAround.acquire();
            System.out.println("Thread execution exit" + Thread.currentThread().getName());
        }

        public void reusableAcquire() {
            try {
                phase1();
                phase2();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        private int released = 0;
        private final Object object = new Object();
        private volatile boolean flag = false;
        private int gcnt = 0;
        public void reusableAcquire1() {
            synchronized (object) {
                while (flag) {
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                this.cnt += 1;
                if(this.cnt == n) {
                    flag = true;
                    released = n;
                    barrier.release();
                }
            }
            try {
               // System.out.println("Waiting Thread execution " + Thread.currentThread().getName());
                barrier.acquire();
                gcnt++;
                barrier.release();
               // System.out.println("Thread execution " + Thread.currentThread().getName() + " permits " + barrier.availablePermits());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (object) {
                released--;
                if(released == 0) {
                    this.cnt = 0;
                   // System.out.println("count become zero and permit is " + this.barrier.availablePermits());
                    this.flag = false;
                    object.notifyAll();
                    try {
                        this.barrier.acquire();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        var pr = new Problem2();
        var barr = pr.new Barrier(10);
        var factory = Thread.ofVirtual().name("Problem2-",  1).factory();
        try (var executor = Executors.newThreadPerTaskExecutor(factory)) {
            List<CompletableFuture<Void>> tasks = new ArrayList<>();
            for (var i = 0; i < 12; i++) {
                tasks.add(CompletableFuture.runAsync(() -> barr.reusableAcquire1(), executor));
            }
            Thread.sleep(1000);
            System.out.println("after sleep");
            for (var i = 0; i < 8; i++) {
                tasks.add(CompletableFuture.runAsync(() -> barr.reusableAcquire1(), executor));
            }
            CompletableFuture<Void> allOf = CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]));
            allOf.join();
            //executor.shutdown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("cnt " + barr.cnt);
        System.out.println("gcnt " + barr.gcnt);
        System.out.println("release " + barr.released);
        System.out.println("barrier permit " + barr.barrier.availablePermits());
    }
}
