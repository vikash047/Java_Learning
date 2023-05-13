package Stripe;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PenalityTImeTest {

    private PenalityTIme penalityTIme = new PenalityTIme();
    @Test
    void computePenality() {
        int cal = penalityTIme.computePenality("Y Y Y N N N N", 0);
        Assert.assertEquals(3, cal);
        cal = penalityTIme.computePenality("Y Y Y N N N N", 7);
        Assert.assertEquals(4, cal);
        cal = penalityTIme.computePenality("Y Y Y N N N N", 3);
        Assert.assertEquals(0, cal);
        cal = penalityTIme.computePenality("", 0);
        Assert.assertEquals(0, cal);
        cal = penalityTIme.computePenality("Y N Y N N N N", 3);
        Assert.assertEquals(1, cal);

    }
    @Test
    void bestClosingTime() {
        int cal = penalityTIme.bestClosingTime("Y Y Y N N N N");
        Assert.assertEquals(3, cal);
    }
}