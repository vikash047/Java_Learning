package LLDPracctice.TaskPlanner;

import java.util.Date;
import java.util.List;

public class Sprint {
    private String id;
    private Date startDate;
    private Date endDate;
    private List<Task> tasks;

    public Sprint(String id, Date startDate, Date endDate, List<Task> tasks) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }
}
