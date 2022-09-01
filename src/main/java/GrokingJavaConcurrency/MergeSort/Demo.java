package GrokingJavaConcurrency.MergeSort;

import java.util.Random;

public class Demo {
    public static void main(String[] args) {
        Random random = new Random(System.currentTimeMillis());
        int[] input = new int[25];
        for(int i = 0; i < 25; i++) {
            input[i] = random.nextInt(10000);
        }
        MergeSortProblem mergeSortProblem = new MergeSortProblem(25);
        mergeSortProblem.mergeSort(0, 24, input);
        for(int i = 0; i < 25; i++) {
            System.out.print(input[i] + ", ");
        }
        System.out.println();
    }
}
