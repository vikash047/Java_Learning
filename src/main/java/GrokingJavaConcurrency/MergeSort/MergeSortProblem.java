package GrokingJavaConcurrency.MergeSort;

public class MergeSortProblem {
    private int size;
    private int[] input;
    private int[] scratch;

    public MergeSortProblem(int size) {
        this.size = size;
        input = new int[size];
        scratch = new int[size];
    }

    public void mergeSort(final int start, final int end, final int[] input) {
        if(start == end) {
            return;
        }
        int mid = start + (end - start)/2;
        Thread w1 = new Thread(new Runnable() {
            @Override
            public void run() {
                mergeSort(start, mid, input);
            }
        });

        Thread w2 = new Thread(new Runnable() {
            @Override
            public void run() {
                mergeSort(mid + 1, end, input);
            }
        });
        w1.start();
        w2.start();

        try {
            w1.join();
            w2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        int i = start;
        int j = mid + 1;
        int k;
        for(k = start; k <= end; k++) {
            scratch[k] = input[k];
        }
        k = start;
        while(k <= end) {
            if(i <= mid & j <= end) {
                input[k] = Math.min(scratch[i], scratch[j]);
                if(input[k] == scratch[i]) {
                    i++;
                } else {
                    j++;
                }
            } else if(i <= mid && j > end) {
                input[k] = scratch[i++];
            } else {
                input[k] = scratch[j++];
            }
            k++;
        }
    }

}
