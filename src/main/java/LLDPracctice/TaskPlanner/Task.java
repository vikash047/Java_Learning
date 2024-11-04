package LLDPracctice.TaskPlanner;

import java.util.Date;

public class Task {
    private int id;
    private String desc;
    private int daysRequired;

    private TaskStatus taskStatus;

    private TaskType taskType;

    private User assignedUser;

    private Date taskStartedDate;
    private Date taskCreatedDate;

    public Task(int id, String desc, int daysRequired, TaskStatus taskStatus, TaskType type, User assignedUser, Date taskCreatedDate) {
        this.id = id;
        this.desc = desc;
        this.daysRequired = daysRequired;
        this.taskStatus = taskStatus;
        this.taskType = type;
        this.assignedUser = assignedUser;
        this.taskCreatedDate = taskCreatedDate;
    }
    public void changeTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
    public void assignedTask(User user) {
        this.assignedUser = user;
    }
}
