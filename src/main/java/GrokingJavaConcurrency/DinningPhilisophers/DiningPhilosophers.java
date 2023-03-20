package GrokingJavaConcurrency.DinningPhilisophers;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class DiningPhilosophers {
    private static Random random = new Random(System.currentTimeMillis());
    private Semaphore[] forks = new Semaphore[5];
    private Semaphore maxDinners = new Semaphore(4);

    public  DiningPhilosophers() {
        for(int i = 0; i < 5; i++) {
            forks[i] = new Semaphore(1);
        }
    }

    public void lifeCycle(int id) throws InterruptedException {
        while (true) {
            talk();
            eat(id);
        }
    }

   /* private void eat(int id) throws InterruptedException {
        maxDinners.acquire(); // used to breask the dead lock only 4 forks can be grabbed at any time and one always free
        // to proceeds eat function.
        forks[id].acquire();
        forks[(id + 4)%5].acquire();
        System.out.println("Philosopher " + id + " is eating");
        forks[id].release();
        forks[(id + 4)%5].release();
        maxDinners.release();
    }*/

   /* withour semaphore we can choose a random philosopher which one try to acquire reverse order resources if
   not able to acquire then other resource is free to grab the next to him such way we can break the lock.
   we are choosing id 3
    */
   private void eat(int id) throws InterruptedException {
       if(id == 3) {
           acquireRight(id);
       } else {
           acquireLeft(id);
       }
       System.out.println("Philosopher " + id + " is eating");
       forks[id].release();
       forks[(id + 4)%5].release();
   }

   private void acquireLeft(int id) throws InterruptedException {
       forks[id].acquire();
       forks[(id + 4)%5].acquire();
   }

   private void acquireRight(int id) throws InterruptedException {
       forks[(id + 4)%5].acquire();
       forks[id].acquire();
   }

    private void talk() throws InterruptedException {
        Thread.sleep(random.nextInt(500));
    }
}
