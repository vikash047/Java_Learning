package JavaRealWorldExamples.Netflix;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class SlidingWindowMedianTest {
    private SlidingWindowMedian medianSlidingWindow;

    @Before
    public void initialize() {
        medianSlidingWindow = new SlidingWindowMedian();
    }
    @Test
    public void MainTest() {
        System.out.println("Example - 1");
        Integer[] arr = {1,3,-1,-3,5,3,6,7};
        int k = 3;
        System.out.println("Input: array =" + Arrays.toString(arr) + ", k = " + k);
        double[] output = medianSlidingWindow.median(Arrays.asList(arr), k);
        System.out.println("Output: Medians =" + Arrays.toString(output));

        System.out.println("\nExample - 2");
        Integer[] arr2 = {1,2};
        k = 1;
        System.out.println("Input: array =" + Arrays.toString(arr2) + ", k = " + k);
        double[] output2 = medianSlidingWindow.median(Arrays.asList(arr2), k);
        System.out.println("Output: Medians =" + Arrays.toString(output2));
    }
}