package JavaConcurrencyAndThreading;

public class DemoBlokingQueue {
    public static void Demo() throws InterruptedException {
        final BlockingQueue<Integer> q = new BlockingQueue<>(5);

        Thread  t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i = 0; i < 50; i++) {
                        q.enqueue(i);
                        System.out.println("enqueued " + i);
                    }
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 50; i++) {
                    try {
                        System.out.println("Thread 1 dequeued " + q.dequeue());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 25; i++) {
                    try {
                        System.out.println("Thread 2 dequeued " + q.dequeue());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();
        //Thread.sleep(4000);
        t2.start();
       // t3.start();
        t1.join();
        t2.join();
        //t3.join();

    }

}
