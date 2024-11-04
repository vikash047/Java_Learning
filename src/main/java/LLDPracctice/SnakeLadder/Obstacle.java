package LLDPracctice.SnakeLadder;

public abstract class Obstacle {
    protected int startPos;
    protected int endPos;

    public Obstacle(int startPos, int endPos) {
        this.startPos = startPos;
        this.endPos = endPos;
    }
    public abstract int applyAction();
}
