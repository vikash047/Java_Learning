package JavaConcurrencyAndThreading;

import java.util.Random;

public class MultiThreadedMergeSort {
    private static int size;
    private int[] input;
    private int[] scratch;

    public MultiThreadedMergeSort(int _sz) {
        size = _sz;
        input = new int[size];
        scratch = new int[size];
    }
    public void mergeSort(final int start, final int end, final int[] input) {
        if(start == end) {
            return;
        }
        final int mid = start + (end - start)/2;
        Thread wr1 = new Thread(() -> mergeSort(start, mid, input));
        Thread wr2 = new Thread(() -> mergeSort(mid + 1, end, input));
        wr1.start();
        wr2.start();
        try {
            wr1.join();
            wr2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        int i = start;
        int j = mid + 1;
        int k;
        for (k = start; k <= end; k++) {
            scratch[k] = input[k];
        }
        k = start;
        while (k <= end) {
            if(i <= mid && j <= end) {
                input[k] = Math.min(scratch[i], scratch[j]);
                if(input[k] == scratch[i]) {
                    i++;
                } else {
                    j++;
                }
            } else if(i <= mid && j > end) {
                input[k] = scratch[i];
                i++;
            } else {
                input[k] = scratch[j];
                j++;
            }
            k++;
        }
    }

    public static void print(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.printf(arr[i] + " ,");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MultiThreadedMergeSort merge = new MultiThreadedMergeSort(20);
        Random random = new Random(System.currentTimeMillis());
        int[] arr = new int[20];
        for(int i = 0; i < 20; i++){
            arr[i] = random.nextInt(20);
        }
        print(arr);
        merge.mergeSort(0, arr.length - 1, arr);
        print(arr);
     }
}
