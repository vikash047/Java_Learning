package MultithreadingProblemsSolveGMSE.Molecule;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MoleculeMachine {
    private final String[] molecule;
    private ReentrantLock lock;
    private Condition full;

    private int cnt;
    public MoleculeMachine() {
        molecule = new String[3];
        this.cnt = 0;
        this.lock = new ReentrantLock();
        this.full  = lock.newCondition();
    }

    public void h() throws InterruptedException {
        lock.lock();
        while (Collections.frequency(Arrays.asList(molecule), "H") == 2) {
            full.await();
        }
        molecule[cnt] = "H";
        cnt++;
        if(cnt == 3) {
            for(var el : molecule) {
                System.out.print(el+",");
            }
            System.out.println();
            cnt = 0;
            Arrays.fill(molecule, null);
            full.signalAll();
        }
        lock.unlock();
    }

    public void o() throws InterruptedException {
        lock.lock();
        while (Collections.frequency(Arrays.asList(molecule), "O") == 1) {
            full.await();
        }
        molecule[cnt] = "O";
        cnt++;
        if(cnt == 3) {
            for(var el : molecule) {
                System.out.print(el + ",");
            }
            System.out.println();
            cnt = 0;
            Arrays.fill(molecule, null);
            full.signalAll();
        }
        lock.unlock();
    }
}
