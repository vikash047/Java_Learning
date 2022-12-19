package JavaConcurrencyBaeldung.SenderReceiverPattern.ForkJoin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CustomeRecursiveTask extends RecursiveTask<Integer> {
    private int[] arr;
    private static final int ThresHold = 4;
    public CustomeRecursiveTask(int[] arr) {
        this.arr = arr;
    }
    @Override
    protected Integer compute() {
        if(arr.length > ThresHold) {
            return ForkJoinTask.invokeAll(createSubTask())
                    .stream()
                    .mapToInt(ForkJoinTask::join)
                    .sum();
        } else {
           return process();
        }
    }

    private List<CustomeRecursiveTask> createSubTask() {
        List<CustomeRecursiveTask> lst = new ArrayList<>();
        lst.add(new CustomeRecursiveTask(Arrays.copyOfRange(arr, 0, arr.length/2)));
        lst.add(new CustomeRecursiveTask(Arrays.copyOfRange(arr, arr.length/2, arr.length)));
        return lst;
    }
    private Integer process() {
        System.out.println("processed by " + Thread.currentThread().getId());
        return Arrays.stream(this.arr).map(x -> x*10).sum();
    }
}
