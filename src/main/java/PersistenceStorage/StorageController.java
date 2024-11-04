package PersistenceStorage;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class StorageController implements DB {
    private final Map<String, ByteBuffer> inMemoryBuffer; // can be replaced with LRU
    private final Storage storage;

    private final Map<String, Pair<String, Integer>> keyToPageMapping; // can be replaced with skipList or B+Tree

    public StorageController(Storage storage) {
        this.inMemoryBuffer = new HashMap<>();
        this.keyToPageMapping = new HashMap<>();
        this.storage = storage;
    }

    @Override
    public void insert(String key, String value) {
        var result = storage.insert(key.getBytes(StandardCharsets.UTF_8), value.getBytes(StandardCharsets.UTF_8));
        keyToPageMapping.put(key, result);
    }

    @Override
    public String getValue(String key) throws IOException {
        if(keyToPageMapping.containsKey(key)) {
            var result = keyToPageMapping.get(key);
            ByteBuffer buffer;
            if(inMemoryBuffer.containsKey(result.getKey())) {
               buffer = inMemoryBuffer.get(result.getKey());
            } else {
                buffer = storage.get(result.getKey());
                inMemoryBuffer.put(result.getKey(), buffer);
            }
            var ret = storage.get(inMemoryBuffer.get(result.getKey()), result.getValue());
            String value = new String(ret.getValue(), StandardCharsets.UTF_8);
            return value;
        }
        return null;
    }
}
