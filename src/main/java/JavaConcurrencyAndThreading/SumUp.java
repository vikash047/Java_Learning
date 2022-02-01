package JavaConcurrencyAndThreading;

public class SumUp {
    private Integer start;
    private Integer end;
    private Long counter = 0L;
    private final Integer max = Integer.MAX_VALUE;

    public SumUp(int st, int end) {
        this.start = st;
        this.end = end;
    }

    public void sum() {
        for(int i = this.start; i <= this.end; i++) {
            counter += i;
        }
    }

    public Long getCounter(){
        return this.counter;
    }


}
