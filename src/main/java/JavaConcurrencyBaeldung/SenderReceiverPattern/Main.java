package JavaConcurrencyBaeldung.SenderReceiverPattern;

import JavaConcurrencyBaeldung.SenderReceiverPattern.CountDownLatch.TestExample;
import JavaConcurrencyBaeldung.SenderReceiverPattern.ForkJoin.ForkJoinPoolConcept;
import JavaConcurrencyBaeldung.SenderReceiverPattern.Mutex.CyclicBarrierConcept;
import JavaConcurrencyBaeldung.SenderReceiverPattern.Mutex.CyclicVsCountDownLatch;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();
    public static void main(String[] args) throws ExecutionException, InterruptedException {
       /* Data data = new Data();
        Thread t1 = new Thread(new Sender(data));
        Thread t2 = new Thread(new Reciever(data));
        t1.start();
        t2.start();*/
        /*SequenceGenerator seg = new SequenceGenerator();
        seg.Generate();*/
        /*CompleteAbleFutureInJava cfr = new CompleteAbleFutureInJava();
        cfr.runFunction();*/
        /*ExeccutorServiceConcept execcutorServiceConcept = new ExeccutorServiceConcept();
        execcutorServiceConcept.ScheduledService();*/
        /*ForkJoinPoolConcept forkJoinPoolConcept =  new ForkJoinPoolConcept();
        forkJoinPoolConcept.frokJoinEx();*/
        /*TestExample ex = new TestExample();
        ex.test();*/
        /*CyclicBarrierConcept cyclicBarrierConcept = new CyclicBarrierConcept();
        cyclicBarrierConcept.DemoTest(2, 5);*/
        CyclicVsCountDownLatch cyclicVsCountDownLatch = new CyclicVsCountDownLatch();
        cyclicVsCountDownLatch.cyclicBarrierTest();
    }
}
