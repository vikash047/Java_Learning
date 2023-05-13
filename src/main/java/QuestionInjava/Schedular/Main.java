package QuestionInjava.Schedular;

public class Main {

    class WorkImpl implements Work {

        @Override
        public void doWork() {

        }
    }
    public static void main(String[] args) {
        WorkerSchedulerImpl workerScheduler = new WorkerSchedulerImpl();
        Work w = null;
        /*var t1 = new Thread(() -> workerScheduler.Schedule(w);
        var t2 = new Thread(() -> workerScheduler.Schedule(w);
        var t3 = new Thread(() -> workerScheduler.Schedule(w);
        w*/
       // var t4 = new Thread(() -> workerScheduler.blockUntilCompleted()));

       // var t3 = new Thread(() -> workerScheduler.Schedule(w);


    }
}
