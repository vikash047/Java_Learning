package InterestingProblems;

public interface MapEntry<KEY> {
    void put(KEY key, Integer value, long epochttl);
    Integer get(KEY key);
    boolean delete(KEY key);

    Double getAverage() throws InterruptedException;

}
