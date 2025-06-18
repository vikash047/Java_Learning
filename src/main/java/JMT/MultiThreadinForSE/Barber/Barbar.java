package JMT.MultiThreadinForSE.Barber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Barbar {
     private int chairs;
     private int waitingCustomers;
     private final ReentrantLock lock = new ReentrantLock();
     private Semaphore barbarWakeUp;
     private Semaphore startHairCut;
     private Semaphore givenHairCut;
     private Semaphore customerLeave;
     public Barbar(int n) {
         this.chairs = n;
         this.waitingCustomers = 0;
         barbarWakeUp = new Semaphore(0);
         startHairCut = new Semaphore(0);
         givenHairCut = new Semaphore(0);
         customerLeave = new Semaphore(0);
     }

     public void customerEnters() throws InterruptedException {
         lock.lock();
         if(waitingCustomers == chairs) {
             System.out.println("get out from the shop " + waitingCustomers);
             lock.unlock();
             return;
         }
         waitingCustomers++;
         lock.unlock();
         barbarWakeUp.release();
         startHairCut.acquire();
         //System.out.println(" started the hair cut");
         lock.lock();
         waitingCustomers--;
         lock.unlock();
         givenHairCut.acquire();
         //customerLeave.release();
     }

     public void barber() throws InterruptedException {
         while (true) {
             barbarWakeUp.acquire();
             System.out.println(" woke up");
             startHairCut.release();
             System.out.println("giving hair cut");
             Thread.sleep(50);
             givenHairCut.release();
            // customerLeave.acquire();
         }
     }

     public static void main(String[] args) throws InterruptedException {
         Barbar barbar = new Barbar(5);
         Thread t = new Thread(() -> {
             try {
                 barbar.barber();
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }
         });
         t.start();
         int i = 0;
         List<Thread> all = new ArrayList<>();
         while (i < 100) {
             all.add(new Thread(() -> {
                 try {
                     barbar.customerEnters();
                 } catch (InterruptedException e) {
                     throw new RuntimeException(e);
                 }
             }));
             //all.get(i).start();
             //Thread.sleep(10);
             i++;
         }
         all.forEach(Thread::start);
         all.forEach(x -> {
             try {
                 x.join();
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }
         });
     }
}
