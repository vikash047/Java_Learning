package MultithreadingProblemsSolveGMSE.FizzBuzz;

import java.util.concurrent.Semaphore;

public class FizzBuzzSemaphore extends FizzBuzz{

    private Semaphore fizzSem;
    private Semaphore buzzSem;
    private Semaphore fizzBuzzSem;
    private Semaphore numberSem;

    private int curr;
    public FizzBuzzSemaphore(int n) {
        super(n);
        this.curr = 1;
        this.buzzSem = new Semaphore(0);
        this.fizzSem = new Semaphore(0);
        this.fizzBuzzSem = new Semaphore(0);
        this.numberSem = new Semaphore(1);
    }

    private void release() {
        this.curr++;
        if(this.curr <= num) {
            if(curr%3 == 0 && curr%5 != 0) {
                fizzSem.release();
            } else if(curr%3 == 0 && curr%5 == 0) {
                buzzSem.release();
            } else if(curr%3 == 0 && curr%5 == 0) {
                fizzBuzzSem.release();
            } else {
                numberSem.release();
            }
        } else {
            fizzSem.release();
            buzzSem.release();
            fizzBuzzSem.release();
            numberSem.release();
        }
    }

    @Override
    public void buzz() throws InterruptedException {
        while (this.curr <= num) {
            buzzSem.acquire();
            if(this.curr <= num) {
                System.out.println("buzz");
            }
            release();
        }
    }

    @Override
    public void fizz() throws InterruptedException {
        while (this.curr <= num) {
            fizzSem.acquire();
            if(this.curr <= num) {
                System.out.println("fizz");
            }
            release();
        }
    }

    @Override
    public void fizzBuzz() throws InterruptedException {
        while (this.curr <= num) {
            fizzBuzzSem.acquire();
            if(this.curr <= num) {
                System.out.println("fizzBuzz");
            }
            release();
        }
    }

    @Override
    public void number() throws InterruptedException {
        while (this.curr <= num) {
            fizzBuzzSem.acquire();
            if(this.curr <= num) {
                System.out.println(this.curr);
            }
            release();
        }
    }
}
