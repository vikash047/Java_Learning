package LLDPracctice.SnakeLadder;

public class User {
    private int currentPos;
    private String id;

    public User(int currentPos, String id) {
        this.currentPos = currentPos;
        this.id = id;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public String getId() {
        return id;
    }

    public User changePost(int pos) {
        return new User(pos, id);
    }
}
