package LLDPracctice.SnakeLadder;

import java.util.Random;

public class Dice {
    private static Random random = new Random();
    public static int getNext() {
        return random.nextInt(1,7);
    }
}
