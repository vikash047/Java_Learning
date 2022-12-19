package JavaConcurrencyAndThreading;

import java.util.Arrays;
import java.util.Collections;

public class H2OMolecule {
    static class WaterForm{
        private Object sync;
        private String[] mol;
        private int count;
        public WaterForm() {
            sync = new Object();
            mol = new String[3];
            count = 0;
        }

        public void HydrogenAtom() {
            synchronized (sync) {
                while (Collections.frequency(Arrays.asList(mol), "H") == 2) {
                    try {
                        sync.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mol[count] = "H";
                count++;
                if(count == 3) {
                    for(String m : mol) {
                        System.out.printf(m);
                    }
                    System.out.println();
                    Arrays.fill(mol, null);
                    count = 0;
                }
                sync.notifyAll();
            }
        }

        public void OxygenAtom() {
            synchronized (sync) {
                while (Collections.frequency(Arrays.asList(mol), "O") == 1) {
                    try {
                        sync.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mol[count] = "O";
                count++;
                if(count == 3) {
                    for(String m : mol) {
                        System.out.printf(m);
                    }
                    System.out.println();
                    Arrays.fill(mol, null);
                    count = 0;
                }
                sync.notifyAll();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        final WaterForm waterForm = new WaterForm();
        Thread t1 = new Thread(() -> {while(true)waterForm.HydrogenAtom();});
        Thread t2 = new Thread(() -> {while(true)waterForm.HydrogenAtom();});
        Thread t3 = new Thread(() -> { while(true)waterForm.OxygenAtom();});
        Thread t4 = new Thread(() -> { while(true)waterForm.OxygenAtom();});
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();
    }
}
