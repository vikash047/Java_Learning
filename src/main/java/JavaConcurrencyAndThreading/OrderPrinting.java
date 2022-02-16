package JavaConcurrencyAndThreading;

public class OrderPrinting {
    private int count;
    public OrderPrinting() {
        count = 1;
    }
    public void printFirst() throws InterruptedException {
        synchronized (this) {
            System.out.println("First print");
            count++;
            this.notifyAll();
        }
    }

    public void printSecond() throws InterruptedException {
        synchronized (this) {
            while (count != 2) {
                this.wait();
            }
            System.out.println("Second Print");
            count++;
            this.notifyAll();
        }
    }

    public void printThird() throws InterruptedException {
        synchronized (this) {
            while (count != 3) {
                this.wait();
            }
            System.out.println("Third print");
            count++;
        }
    }

    public static class OrderPrintingThread extends Thread {
        private OrderPrinting obj;
        private String method;
        public OrderPrintingThread(OrderPrinting _obj, String _method) {
            this.obj = _obj;
            this.method = _method;
        }
        public void run() {
            if("first".equalsIgnoreCase(method)) {
                try {
                    obj.printFirst();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if("second".equalsIgnoreCase(method)) {
                try {
                    obj.printSecond();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if("third".equalsIgnoreCase(method)) {
                try {
                    obj.printThird();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        final OrderPrintingUsingCountDownLatch obj = new OrderPrintingUsingCountDownLatch();
        Thread t1 = new Thread(() -> {
            try {
                obj.printFirst();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                obj.printSecond();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                obj.printThird();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t3.start();
        t2.start();

    }
}
