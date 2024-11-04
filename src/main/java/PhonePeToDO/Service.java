package PhonePeToDO;

import PhonePeToDO.Exceptions.TaskExitsException;
import PhonePeToDO.Modles.LogData;
import PhonePeToDO.Modles.Task;
import PhonePeToDO.Modles.TaskStatus;
import PhonePeToDO.Modles.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {
    private final Map<String, User> userMapping = new HashMap<>();

    public void adduser(User user) {
        if(!userMapping.containsKey(user.getId())) {
            userMapping.put(user.getId(), user);
        }
    }

    public void addTask(String userId, Task task) throws TaskExitsException {
        if(userMapping.containsKey(userId)) {
            var tb = userMapping.get(userId).getTaskBoard();
            tb.addTask(task);
        }
    }

    public void modifyTask(String userId, Task task) {
        if(userMapping.containsKey(userId)) {
            var tb = userMapping.get(userId).getTaskBoard();
            tb.modifyTask(task);
        }
    }

    public Task getTask(String userId, String taskId) {
        if(userMapping.containsKey(userId)) {
            var tb = userMapping.get(userId).getTaskBoard();
            return tb.getTask(taskId);
        }
        return null;
    }

    public List<Task> getTask(String userId) {
        if(userMapping.containsKey(userId)) {
            var tb = userMapping.get(userId).getTaskBoard();
            return tb.getAllTasks();
        }
        return null;
    }

    public List<Task> getTask(String userId, Long from, Long to) {
        if(userMapping.containsKey(userId)) {
            var tb = userMapping.get(userId).getTaskBoard();
            return tb.getAllTasks(from, to);
        }
        return null;
    }

    public Map<TaskStatus, Integer> getStatistics(String userId, Long from, Long to) {
        if(userMapping.containsKey(userId)) {
            var tb = userMapping.get(userId).getTaskBoard();
            var data = tb.getLogs(from, to);
            Map<TaskStatus, Integer> ret = new HashMap<>();
            for(var d : data) {
                ret.putIfAbsent(d.getStatus(), 0);
                ret.put(d.getStatus(), ret.get(d.getStatus()) + 1);
            }
            List<Integer> res = new ArrayList<>();
            return ret;

        }
        return null;
    }

    public List<LogData> getActivityLog(String userId, Long from, Long to) {
        if(userMapping.containsKey(userId)) {
            var tb = userMapping.get(userId).getTaskBoard();
            return tb.getLogs(from, to);
        }
        return null;
    }
}
