package PhonePeToDO.Modles;

public class User {
    private String id;
    private String Name;

    private TaskBoard taskBoard;
    public User(String id, String name) {
        this.id = id;
        Name = name;
        this.taskBoard = new TaskBoard();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public TaskBoard getTaskBoard() {
        return taskBoard;
    }
}
