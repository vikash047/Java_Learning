package Interview.IO.TaskSchedular;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {
    private AtomicInteger atomicInteger = new AtomicInteger();
    class TempTask implements Task {
        int task;
        AtomicInteger integer;
        public TempTask(int i, AtomicInteger integer) {
            this.integer = integer;
            this.task = i;
        }
        @Override
        public void apply() {
            try {
                Thread t = Thread.currentThread();
                int value = integer.incrementAndGet();
                Thread.sleep(1000);
                System.out.println(t.getName() + " task seq " + task +" executed value " + value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        public int getFinalValue() {
            return integer.get();
        }
        public int taskSeq() {
            return task;
        }
    }

    private TaskManager taskManager;
    @BeforeEach
    void setUp() {
        taskManager = new TaskManager(10);
        taskManager.start();
    }

    @Test
    void schedule() throws IllegalAccessException, InterruptedException {
        for(int i = 0; i < 100; i++) {
            taskManager.schedule(new TempTask(i, atomicInteger), i + 1, TimeUnit.MILLISECONDS);
        }
        Thread.sleep(Duration.ofSeconds(100).toMillis());
        try {
            taskManager.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(atomicInteger.get(), 100);
        Assertions.assertThrows(IllegalAccessException.class,() -> taskManager.schedule(new TempTask(101, atomicInteger), 10, TimeUnit.SECONDS));
    }

    @Test
    void scheduleAtFixedInterval() throws IllegalAccessException, InterruptedException {
        List<TempTask> taskList = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            var t = new TempTask(i, new AtomicInteger());
            taskList.add(t);
            taskManager.scheduleAtFixedInterval(t, 1);
        }
        Thread.sleep(Duration.ofSeconds(100).toMillis());
        try {
            taskManager.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        taskList.forEach(x -> System.out.println(" task seq " + x.taskSeq() + " final value is " + x.getFinalValue()));
        System.out.println(IllegalAccessException.class.getClass() + " name " + IllegalAccessException.class.getName());
        Assertions.assertThrows(IllegalAccessException.class, () -> taskManager.scheduleAtFixedInterval(new TempTask(101, new AtomicInteger()), 10));
    }
}