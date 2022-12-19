package JavaConcurrencyBaeldung.SenderReceiverPattern.ForkJoin;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolConcept {
    public static ForkJoinPool forkJoinPoolGlobal = new ForkJoinPool(2);

    public void frokJoinEx() {
        //ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        /*CustomRecursiveAction customRecursiveAction = new CustomRecursiveAction(
                new StringBuffer("Vikash is very bad engineer in world. can anybody image about him how bas he is exactly"));
        customRecursiveAction.compute();*/
        int[] arr = new int[100];
        for(int i = 1; i <= 100; i++) arr[i-1] = i;
        CustomeRecursiveTask customeRecursiveTask = new CustomeRecursiveTask(arr);
        forkJoinPoolGlobal.execute(customeRecursiveTask);
        int result = customeRecursiveTask.join();
        System.out.println( "final result " + result);
    }
}

