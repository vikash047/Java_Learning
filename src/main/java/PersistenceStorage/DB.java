package PersistenceStorage;

import java.io.IOException;

public interface DB {
    void insert(String key, String value);
    String getValue(String key) throws IOException;
}
