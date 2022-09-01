package GrokingJavaConcurrency.BarberShop;

import java.util.HashSet;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        final BarberShopProblem barberShopProblem = new BarberShopProblem(5);
        HashSet<Thread> threads = new HashSet<>();
        Thread barber = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    barberShopProblem.barber();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        barber.start();

        for(int i = 0; i < 20; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        barberShopProblem.customerWalksIn();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            threads.add(t);
        }
        for(Thread t : threads) {
            t.start();
        }
        for(Thread t : threads) {
            t.join();
        }
        Thread.sleep(800);
        threads.clear();
        System.out.println("Start second thread inning");
        for(int i = 0; i < 10; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        barberShopProblem.customerWalksIn();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            threads.add(t);
        }
        for(Thread t : threads) t.start();
        for(Thread t : threads) t.join();
        barber.join();
    }
}
