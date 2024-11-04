package LLDPracctice.SnakeLadder;

public class Snake extends Obstacle{
    public Snake(int startPos, int endPos) {
        super(startPos, endPos);
    }

    @Override
    public int applyAction() {
        return startPos;
    }
}
