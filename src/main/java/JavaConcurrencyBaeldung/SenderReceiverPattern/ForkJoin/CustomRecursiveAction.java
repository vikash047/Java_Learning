package JavaConcurrencyBaeldung.SenderReceiverPattern.ForkJoin;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class CustomRecursiveAction extends RecursiveAction {
    private static final int THRESHOLD = 4;
    private static Logger logger = Logger.getLogger(CustomRecursiveAction.class);
    private StringBuffer workLoad;

    public CustomRecursiveAction(StringBuffer workLoad) {
        this.workLoad = new StringBuffer(workLoad);
    }

    @Override
    protected void compute() {
        if(workLoad.length() > THRESHOLD) {
            ForkJoinTask.invokeAll(createSubTask());
        } else {
            process();
        }
    }

    private List<CustomRecursiveAction> createSubTask() {
        List<CustomRecursiveAction> lst = new ArrayList<>();
        lst.add(new CustomRecursiveAction(new StringBuffer(workLoad.substring(0, workLoad.length()/2))));
        lst.add(new CustomRecursiveAction(new StringBuffer(workLoad.substring(workLoad.length()/2))));
        return lst;
    }
    private void process() {
        System.out.println("This is result - (" + workLoad + ") - was processed by " + Thread.currentThread().getId());
    }
}
