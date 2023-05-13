package GrokingJavaConcurrency.DefferedCallback;

import java.util.HashSet;
import java.util.Set;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        Set<Thread> threadSet = new HashSet<>();
        final DefferedCallBackExecutor defferedCallBackExecutor = new DefferedCallBackExecutor();
        Thread service = Thread.ofVirtual().name("consumer").start(new Runnable() {
            @Override
            public void run() {
                try {
                    defferedCallBackExecutor.start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        service.start();

        for(int i = 0; i < 10; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    CallBack cb = new CallBack(1, "Hello this is " + Thread.currentThread().getName());
                    defferedCallBackExecutor.registerCallback(cb);
                }
            });
            t.setName("Thread_" + (i + 1));
            t.start();
            threadSet.add(t);
            Thread.sleep(1000);
        }
        for(Thread t : threadSet) {
            t.join();
        }
    }
}
