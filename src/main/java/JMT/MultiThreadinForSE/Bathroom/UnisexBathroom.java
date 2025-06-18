package JMT.MultiThreadinForSE.Bathroom;

/*
A bathroom is being designed for the use of both males and females in an office but requires the following constraints to be maintained:

There cannot be men and women in the bathroom at the same time.
There should never be more than three employees in the bathroom simultaneously.
The solution should avoid deadlocks. For now, though, don’t worry about starvation.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

/*
solution:
The astute reader would immediately realize that we’ll need to guard the variable inUseBy
since it can possibly be both read and written to by different threads at the same time.
 Does that mean we should mark our methods as synchronized?
 The wary reader would know that doing so would essentially make the threads serially access the methods, i.e.,
 if one male thread is accessing the bathroom, then another one can’t access the bathroom even though the problem says
 that more than one male should be able to use the bathroom. This requires us to take synchronization to a finer granular level
 rather than implementing it at the method level.
 */
public class UnisexBathroom {
    public static enum Gender{
        NONE,
        WOMEN,
        MEN
    }
    private Gender inUseBy = Gender.NONE;
    private int numberOfUser;
    private final Semaphore maxParallelUse = new Semaphore(3);
    private void maleUseBathroom(String name) throws InterruptedException {
        System.out.println(inUseBy + " using by " + name + " number of in " + numberOfUser);
        Thread.sleep(1000);
        System.out.println("leaving " + name);
    }
    private void femaleUseBathroom(String name) throws InterruptedException {
        System.out.println(inUseBy + " using by " + name + " number of in " + numberOfUser);
        Thread.sleep(1000);
        System.out.println("leaving " + name);
    }

    public void enterMale(String name) throws InterruptedException {
        synchronized (this) {
            while (inUseBy.equals(Gender.WOMEN)) {
                this.wait();
            }
            maxParallelUse.acquire();
            numberOfUser++;
            inUseBy = Gender.MEN;
        }
        //maxParallelUse.acquire();
        maleUseBathroom(name);
        maxParallelUse.release();
        synchronized (this) {
            numberOfUser--;
            if(numberOfUser == 0) {
                inUseBy = Gender.NONE;
            }
            this.notifyAll();
        }
    }

    public void enterFemale(String name) throws InterruptedException {
        synchronized (this) {
            while (inUseBy.equals(Gender.MEN)) {
                this.wait();
            }
            maxParallelUse.acquire();
            numberOfUser++;
            inUseBy = Gender.WOMEN;
        }
        //maxParallelUse.acquire();
        femaleUseBathroom(name);
        maxParallelUse.release();
        synchronized (this) {
            numberOfUser--;
            if(numberOfUser == 0) {
                inUseBy = Gender.NONE;
            }
            notifyAll();
        }
    }

    public static void main(String[] args) {
        UnisexBathroom bathroom = new UnisexBathroom();
        List<CompletableFuture<Void>> list = new ArrayList<>();
        for(int i = 0; i < 15; i++) {
            final int k = i;
            list.add(CompletableFuture.runAsync(() -> {
                if(k%2 == 0) {
                    try {
                        bathroom.enterMale("bob" + k);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        bathroom.enterFemale("Lisa" + k);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }));
        }
        CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).join();
    }

}
