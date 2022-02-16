package JavaConcurrencyAndThreading;

public class FooBarAlternatePrint {
    static class FooBar{
        int n;
        int flag = 0;
        public FooBar(int _n) {
            this.n = _n;
        }

        public void foo() throws InterruptedException {
            for(int i = 0; i < n; i++) {
                synchronized (this) {
                    while (flag == 1) {
                        this.wait();
                    }
                    System.out.println("Foo");
                    this.notifyAll();
                    flag = 1;
                }
            }
        }

        public void bar() throws InterruptedException {
            for(int i = 0; i < n; i++) {
                synchronized (this) {
                    while (flag == 0) {
                        this.wait();
                    }
                    System.out.println("Bar");
                    this.notifyAll();
                    flag = 0;
                }
            }
        }
    }

    public static void main(String[] args) {
        final FooBar obj = new FooBar(2);
        Thread t1 = new Thread(() -> {
            try {
                obj.foo();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                obj.bar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
    }
}
