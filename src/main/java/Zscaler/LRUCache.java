package Zscaler;

public interface LRUCache {

    String get(String key);

    void put(String key, String value);
}
