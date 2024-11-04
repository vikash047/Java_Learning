package MultithreadingProblemsSolveGMSE.Dining;

import java.util.concurrent.Semaphore;

public class DiningPhilosopher {
    private final Semaphore[] forks;
    private final Semaphore maxDinner;
    public DiningPhilosopher() {
        forks = new Semaphore[5];
        for(int i = 0; i < 5; i++) {
            forks[i] = new Semaphore(1);
        }
        this.maxDinner = new Semaphore(4); // avoid dead lock 4 max philopher can eat using 5 forks.
    }

    public void lifeCycle(int id) {
        while (true) {
            talk();
            eat(id);
        }
    }
    public void talk() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void eat(int id) {
        try {
            maxDinner.acquire();
            forks[id].acquire();
            forks[(id + 4)%5].acquire();
            System.out.println("eating food id " + id);
            forks[id].release();
            forks[(id + 4)%5].release();
            maxDinner.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void eatByBreakingLock(int id) {
        try {
            // choose random any who can lift the fork in reverse order
            if(id == 3) {
                leftRight(id);
            }
            rightLeft(id);
            System.out.println("eating food id " + id);
            forks[id].release();
            forks[(id+4)%5].release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void leftRight(int id) throws InterruptedException {
        forks[(id+4)%5].acquire();
        forks[id].acquire();
    }
    public void rightLeft(int id) throws InterruptedException {
        forks[id].acquire();
        forks[(id+4)%5].acquire();
    }
}
