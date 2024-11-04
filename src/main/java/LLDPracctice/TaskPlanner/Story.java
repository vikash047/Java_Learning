package LLDPracctice.TaskPlanner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Story extends Task {
    List<Task> taskList;
    public Story(int id, String desc, int daysRequired, TaskStatus taskStatus, TaskType type, User assignedUser, Date taskCreatedDate) {
        super(id, desc, daysRequired, taskStatus, type, assignedUser, taskCreatedDate);
        this.taskList = new ArrayList<>();
    }

    public void addTask(Task task) {
        taskList.add(task);
    }
    public void removeTask(Task task) {
        taskList.remove(task);
    }
}
