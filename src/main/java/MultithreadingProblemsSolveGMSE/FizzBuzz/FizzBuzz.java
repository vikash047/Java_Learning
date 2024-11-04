package MultithreadingProblemsSolveGMSE.FizzBuzz;

public class FizzBuzz {
    int num;
    private volatile int i;

    public FizzBuzz(int n) {
        this.num = n;
        this.i = 1;
    }
    public void fizz() throws InterruptedException {
        synchronized (this) {
            while (i <= num) {
                if(i%3 == 0 && i%5 != 0) {
                    System.out.println("fizz");
                    i++;
                    notifyAll();
                } else {
                    wait();
                }
            }
        }
    }

    public void buzz() throws InterruptedException {
        synchronized (this) {
            while (i <= num) {
                if(i%3 != 0 && i%5 == 0) {
                    System.out.println("buzz");
                    i++;
                    notifyAll();
                } else {
                    wait();
                }
            }
        }
    }

    public void fizzBuzz() throws InterruptedException {
        synchronized (this) {
            while (i <= num) {
                if(i%3 == 0 && i%5 == 0) {
                    System.out.println("fizzBuzz");
                    i++;
                    notifyAll();
                } else {
                    wait();
                }
            }
        }
    }

    public void number() throws InterruptedException {
        synchronized (this) {
            while (i <= num) {
                if(i%3 != 0 && i%5 == 0) {
                    System.out.println(i);
                    i++;
                    notifyAll();
                } else {
                    wait();
                }
            }
        }
    }
}
