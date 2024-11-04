package PhonePeToDO.Modles;

import PhonePeToDO.Exceptions.TaskExitsException;

import java.util.*;

public class TaskBoard {
    private final TreeMap<Long, Set<String>> taskMap;
    private final Map<String, Task> tasks;

    private final UpdateLog updateLog;
    private final Long hoursInDayInMillis = 86400000L;
    public TaskBoard() {
        taskMap = new TreeMap<>();
        tasks = new HashMap<>();
        updateLog = new UpdateLog();
    }

    public void addTask(Task task) throws TaskExitsException {
        if(tasks.containsKey(task.getId())) {
            throw new TaskExitsException("Task id already exits");
        }
        tasks.put(task.getId(), task);
        Long day = task.getStartTime()/hoursInDayInMillis;
        addTaskMap(task, day);
        addLogEntry(task);
    }
    public void modifyTask(Task task) {
        if(tasks.containsKey(task.getId())) {
            var oldTask = tasks.get(task.getId());
            Long oldDay = oldTask.getStartTime()/hoursInDayInMillis;
            oldTask.modifyTask(task);
            Long day = task.getStartTime()/hoursInDayInMillis;
            if(oldDay != day) {
                removeTskMap(oldDay, oldTask.getId());
                addTaskMap(oldTask, day);
            }
            addLogEntry(oldTask);
        }
    }

    public Task getTask(String id) {
        return tasks.get(id);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<Task> getAllTasks(Long from, Long end) {
        Long day = from/hoursInDayInMillis;
        var key = taskMap.ceilingKey(day);
        var tailMap = taskMap.tailMap(key);
        List<Task> list = new ArrayList<>();
        var endDay = end/hoursInDayInMillis;
        for(var kv : tailMap.entrySet()) {
            if(kv.getKey().compareTo(endDay) > 0) {
                break;
            }
            for(var entry : kv.getValue()) {
                list.add(tasks.get(entry));
            }
        }
        return list;
    }

    public List<LogData> getLogs(Long fromTime, Long endTime) {
        return this.updateLog.getLogDataList(fromTime, endTime);
    }

    private void addTaskMap(Task task, Long day) {
        taskMap.putIfAbsent(day, new HashSet<>());
        taskMap.get(day).add(task.getId());
    }

    private void removeTskMap(Long day, String id) {
        if(taskMap.containsKey(day)) {
            taskMap.get(day).remove(id);
        }
    }

    private void addLogEntry(Task task) {
        this.updateLog.addLog(new LogData(System.currentTimeMillis(), task.getTaskStatus(), task.getId()));
    }
}
