package LLDPracctice.SnakeLadder;

public class Ladder extends Obstacle{
    public Ladder(int startPos, int endPos) {
        super(startPos, endPos);
    }

    @Override
    public int applyAction() {
        return endPos;
    }
}
