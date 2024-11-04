package PersistenceStorage;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface Storage {
    Pair<String, Integer> insert(byte[] key, byte[] value);
    ByteBuffer get(String pagePath) throws IOException;
    Pair<byte[], byte[]> get(ByteBuffer buffer, int position);
}
