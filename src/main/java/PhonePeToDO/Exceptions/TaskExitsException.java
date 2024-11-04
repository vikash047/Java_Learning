package PhonePeToDO.Exceptions;

public class TaskExitsException extends Exception{
    public TaskExitsException(String taskIdAlreadyExits) {
        super(taskIdAlreadyExits);
    }
}
