package GrokingJavaConcurrency.BlockingQueue;

public class Demo {

    public static void main(String[] args) throws InterruptedException {
        final BlockingQueueWithoutMutex<Integer> pq = new BlockingQueueWithoutMutex<>(5);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 50; i++) {
                        pq.enqueue(new Integer(i));
                        System.out.println("enqueue " + i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i = 0; i < 25; i++) {
                        System.out.println("dequeue " + pq.dequeue());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 25; i++) {
                        System.out.println("dequeue " + pq.dequeue());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        Thread.sleep(4000);
        t2.start();
        t3.start();
        t3.join();
        t1.join();
        t2.join();
    }

}
