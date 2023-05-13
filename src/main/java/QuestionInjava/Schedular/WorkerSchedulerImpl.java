package QuestionInjava.Schedular;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class WorkerSchedulerImpl implements WorkerScheduler{

    private final ReentrantLock lock, pendingTaskLock;
    private final List<Work> workList;
    private final List<Work> pendingTask;
    private final Condition taskEmpty;
    private volatile int task;
    private volatile Boolean isAllTaskCompleted;

    public WorkerSchedulerImpl() {
        this.lock = new ReentrantLock();
        this.pendingTaskLock = new ReentrantLock();
        this.workList = new ArrayList<>();
        this.pendingTask = new ArrayList<>();
        this.taskEmpty = lock.newCondition();
        this.task = 0;
        this.isAllTaskCompleted = false;
        Thread t = new Thread(() -> backGroundProcessor());
        t.start();
    }

    private void backGroundProcessor() {
        while (true) {
            lock.lock();
            Work w = null;
            try {
                if(task == 0 && isAllTaskCompleted) {
                    taskEmpty.signalAll();
                    //break;
                }
                w = workList.remove(0);
                task--;
                isAllTaskCompleted = task == 0 && workList.isEmpty();
            } finally {
                lock.unlock();
            }
            if(w != null)
                w.doWork();
        }
    }
    @Override
    public void Schedule(Work w) {
        if(lock.tryLock() && !isAllTaskCompleted) {
            try {
                if(!pendingTask.isEmpty()) {
                    workList.addAll(new ArrayList<>(pendingTask));
                    task += pendingTask.size();
                    pendingTask.clear();
                }
                workList.add(w);
                task++;
            } finally {
                lock.unlock();
            }
        } else if(!isAllTaskCompleted) {
            pendingTaskLock.lock();
            pendingTask.add(w);
            pendingTaskLock.unlock();
        }
    }

    @Override
    public void blockUntilCompleted() {
        lock.lock();
        try {
            while(task > 0) {
                taskEmpty.await();
            }
            if(task == 0) {
                while (!pendingTask.isEmpty() && !pendingTaskLock.isLocked()) {
                    pendingTask.stream().forEach(x -> x.doWork());
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
