package JavaConcurrencyAndThreading;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;

public class PrintNumbers {
    static class PrintNumberSeries{
        private int n;
        private Semaphore zero, odd, even;
        Condition cd;
        public PrintNumberSeries(int _n) {
            this.n = _n;
            zero = new Semaphore(1);
            odd = new Semaphore(0);
            even = new Semaphore(0);
        }

        public void printZero() throws InterruptedException {
            for(int i = 0; i < n; i++) {
                zero.acquire();
                System.out.println("0");
                (i%2 == 0 ? odd : even).release();
            }
        }
        public void printOdd() throws InterruptedException {
            for(int i = 1; i <= n; i += 2) {
                odd.acquire();
                System.out.println(i);
                zero.release();
            }
        }
        public void printEven() throws InterruptedException {
            for(int i = 2; i <= n; i += 2) {
                even.acquire();
                System.out.println(i);
                zero.release();
            }
        }
    }

    public static void main(String[] args) {
        final PrintNumberSeries pns = new PrintNumberSeries(4);
        Thread t1 = new Thread(() -> {
            try {
                pns.printZero();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                pns.printOdd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                pns.printEven();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        t3.start();
    }
}
