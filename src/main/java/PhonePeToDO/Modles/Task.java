package PhonePeToDO.Modles;

import java.util.Comparator;

public class Task  implements Comparable<Task>{
    private String id;
    private String name;
    private String dec;
    private TaskStatus taskStatus;

    private Long startTime;
    private Long endTime;


    public Task(String id, String name, String dec, Long startTime, Long endTime) {
        this.id = id;
        this.name = name;
        this.dec = dec;
        this.taskStatus = TaskStatus.NotStarted;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Task modifyTask(Task task) {
        this.setName(task.name);
        this.setDec(task.dec);
        this.setStartTime(task.startTime);
        this.setEndTime(task.endTime);
        this.setTaskStatus(task.taskStatus);
        return this;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDec() {
        return dec;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dec='" + dec + '\'' +
                ", taskStatus=" + taskStatus +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    Comparator<Task> comparator = new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            return 0;
        }
    };
    @Override
    public int compareTo(Task o) {
        return 0;
    }
}
