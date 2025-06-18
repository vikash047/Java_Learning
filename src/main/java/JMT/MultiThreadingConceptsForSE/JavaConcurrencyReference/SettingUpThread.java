package JMT.MultiThreadingConceptsForSE.JavaConcurrencyReference;

public class SettingUpThread {
    public static void main(String args[]) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("say hello");
            }
        });
        t.start();
        ExecuteMe me = new ExecuteMe();
        Thread t1 = new Thread(me);
        // daemon runs in background but as soon as main application thread exits all daemon threads are killed by jvm.
        t1.setDaemon(true);
        t1.start();
        ExecuteMeInThreadSubClass meInThreadSubClass = new ExecuteMeInThreadSubClass();
        meInThreadSubClass.start();
        meInThreadSubClass.join();
        t1.join();
    }
}

class ExecuteMe implements Runnable {

    @Override
    public void run() {
        while (true) {
            System.out.println("say hello in execute me");
            try {
                // thread sleep for 500ms but do not lose the monitor so it is not used for the thread synchronization.
                Thread.sleep(500);
            } catch (InterruptedException ex) {

            }
        }

    }
}

class ExecuteMeInThreadSubClass extends Thread {
    public void run() {
        System.out.println("execute me in thread subclass");
    }
}
