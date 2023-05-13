package CodingQuestions;

public interface LRUCacheMap {
    void put(int key, int value, long ttl);
    int get(int key);
    double calculateAvg();
}
