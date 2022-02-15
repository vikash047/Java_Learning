import JavaConcurrencyAndThreading.*;

public class Main {
   public static void main(String[] args) throws InterruptedException {
       long start = System.currentTimeMillis();
       /*SumUp s1 = new SumUp(0, Integer.MIN_VALUE/2);
       SumUp s2 = new SumUp(Integer.MAX_VALUE/2 + 1, Integer.MAX_VALUE);

       Thread t1 = new Thread(() -> {
           s1.sum();
       });

       Thread t2 = new Thread(() -> {
          s2.sum();
       });

       t1.start();
       t2.start();

       t1.join();
       t2.join();*/

       //RaceCondition.runTest();
       //DeadLoack deadLoack = new DeadLoack();
       //deadLoack.runTest();
      /* NonReentrantLock nre = new NonReentrantLock();
       nre.lock(1);
       System.out.println("Acquired first  lock");
       nre.lock(2);
       System.out.printf("Acquired second lock");*/
       //MissedSignaled.runTest();
       //DemoBlokingQueue.Demo();
       //DefferredCallbackExecutor.runTestTenCallBack();
       //ReaderWritter.Demo();
       //UberRideProblem.Demo();
       //DinningPhiloshper.demo();
       BarbarShop.demo();
       long elasped = System.currentTimeMillis() - start;
       System.out.println("  time spend " + elasped);
    }
}
