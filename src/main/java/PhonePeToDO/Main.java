package PhonePeToDO;

import PhonePeToDO.Exceptions.TaskExitsException;
import PhonePeToDO.Modles.Task;
import PhonePeToDO.Modles.TaskStatus;
import PhonePeToDO.Modles.User;

public class Main {

    public static void main(String[] args) throws TaskExitsException {
        Service service = new Service();
        service.adduser(new User("1", "1"));
        service.adduser(new User("2", "2"));
        var ts = new Task("1", "1", "1", System.currentTimeMillis(), System.currentTimeMillis()+60000);
        service.addTask("1", ts);
        System.out.println(service.getTask("1"));
        ts.setTaskStatus(TaskStatus.Completed);
        service.modifyTask("1", ts);
        System.out.println(service.getTask("1"));
        System.out.println(service.getActivityLog("1", System.currentTimeMillis()- 60000, System.currentTimeMillis()));
        System.out.println(service.getStatistics("1",System.currentTimeMillis()- 60000, System.currentTimeMillis() ));
    }
}
