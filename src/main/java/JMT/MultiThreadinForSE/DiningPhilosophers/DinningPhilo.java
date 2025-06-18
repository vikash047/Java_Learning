package JMT.MultiThreadinForSE.DiningPhilosophers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class DinningPhilo {
    private static Random random = new Random(System.currentTimeMillis());
    private Semaphore[] forks;
    private final Semaphore maxDinner;
    public DinningPhilo() {
        forks = new Semaphore[5];
        for(int i = 0; i < 5; i++) {
            forks[i] = new Semaphore(1);
        }
        maxDinner = new Semaphore(4);
    }
    public void philosopherActivity(int id) throws InterruptedException {
        int i = 0;
        while (i < 10) {
            talk();
            eat(id);
            i++;
        }
    }

    private void eat(int id) throws InterruptedException {
        // maxDinner only allow to  to pick up the fork to break the dead lock.
        int fork1 = (4 + id)%5;
        int fork2 = id%5;
        /*maxDinner.acquire();

        forks[fork1].acquire();
        forks[fork2].acquire();*/
        // another way one dinner acquire the right fork first so another dinner won't acquire right until get left so it would break
        // the lock.
        getForks(id);
        System.out.println(" eating the meal id: " + id);
        forks[fork1].release();
        forks[fork2].release();
    }

    private void getForks(int id) throws InterruptedException {
        int fork1 = (4 + id)%5;
        int fork2 = id%5;
        if(id == 3) {
            forks[fork2].acquire();
            forks[fork1].acquire();
        } else {
            forks[fork1].acquire();
            forks[fork2].acquire();
        }
    }

    private void talk() throws InterruptedException {
        Thread.sleep(random.nextInt(500));
    }

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        DinningPhilo dinningPhilo = new DinningPhilo();
        for(int i = 0; i < 5; i++) {
            final int id = i;
            threads.add(new Thread(() -> {
                try {
                    dinningPhilo.philosopherActivity(id);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }));
            threads.get(i).start();
        }
        for(int i = 0; i < 5; i++) {
            threads.get(i).join();
        }
    }
}
