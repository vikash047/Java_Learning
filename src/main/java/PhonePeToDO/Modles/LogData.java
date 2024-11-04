package PhonePeToDO.Modles;

import java.util.Objects;

public class LogData {
    private Long updateTime;
    private TaskStatus status;

    private String taskId;

    public LogData(Long updateTime, TaskStatus status, String taskId) {
        this.updateTime = updateTime;
        this.status = status;
        this.taskId = taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogData logData)) return false;
        return Objects.equals(updateTime, logData.updateTime) && status == logData.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(updateTime, status);
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "LogData{" +
                "updateTime=" + updateTime +
                ", status=" + status +
                ", taskId='" + taskId + '\'' +
                '}';
    }
}
