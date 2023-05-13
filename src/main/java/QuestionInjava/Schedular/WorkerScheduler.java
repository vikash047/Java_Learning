package QuestionInjava.Schedular;


interface Work {
    void doWork();
}
public interface WorkerScheduler {
    void Schedule(Work w);
    void blockUntilCompleted();
}
