package JavaConcurrencyAndThreading;

public class FizzBuzz {
    private int n;
    private Object sync;
    private int num;
    public FizzBuzz(int _n) {
        this.n = _n;
        num = 1;
        sync = new Object();
    }

    public void fizz() {
        synchronized (sync) {
            while (num <= n) {
                if(num%3 == 0 && num%5 != 0) {
                    System.out.println("Fizz" + " number is " + num);
                    num++;
                    sync.notifyAll();
                } else {
                    try {
                        sync.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void buzz() {
        synchronized (sync) {
            while (num <= n) {
                if(num%3 != 0 && num%5 == 0) {
                    System.out.println("Buzz" + " number is " + num);
                    num++;
                    sync.notifyAll();
                } else {
                    try {
                        sync.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void fizzBuzz() {
        synchronized (sync) {
            while (num <= n) {
                if(num%15 == 0) {
                    System.out.println("FizzBuzz" + " number is " + num);
                    num++;
                    sync.notifyAll();
                } else {
                    try {
                        sync.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void number() {
        synchronized (sync) {
            while (num <= n) {
                if(num%3 != 0 && num%5 != 0) {
                    System.out.println("number is " + num);
                    num++;
                    sync.notifyAll();
                } else {
                    try {
                        sync.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        final FizzBuzz fizzBuzz = new FizzBuzz(20);
        Thread t1 = new Thread(() -> fizzBuzz.fizz());
        Thread t2 = new Thread(() -> fizzBuzz.buzz());
        Thread t3 = new Thread(() -> fizzBuzz.fizzBuzz());
        Thread t4 = new Thread(() -> fizzBuzz.number());
//        t1.setDaemon(true);
//        t2.setDaemon(true);
//        t3.setDaemon(true);
//        t4.setDaemon(true);
        t1.start();
        t2.start();
        t3.start();
        t4.start();

    }
}
