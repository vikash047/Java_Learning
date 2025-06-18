package JMT.PersistenceStorage;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class StorageImpl implements Storage{

    /*
       length of key length of value key flagbit value
     */
    private int PAGE_SIZE = 4096; // 4KB

    private static final int MAX_KEY_SIZE = 500; // 500B
    private static final int FLAG_BIT_SIZE = 1;
    private static final int ADDRESS_SIZE = Integer.BYTES;
    private ByteBuffer buffer;
    private String currentFilePath;

    private Disk disk;

    private int currentSize;
    private final FilePath filePath;
    public StorageImpl(int maxSize, Disk disk) {
        this.PAGE_SIZE = maxSize < 1000 ? PAGE_SIZE : maxSize;
        this.disk = disk;
        filePath = new FilePath();
        buffer = ByteBuffer.allocate(PAGE_SIZE);
        currentFilePath = filePath.getPath();
        this.currentSize = 0;
    }

    @Override
    public Pair<String, Integer> insert(byte[] key, byte[] value) {
        int dataSize = calculateDataSize(key.length, value.length);
        if(key.length >= MAX_KEY_SIZE) {
            throw new RuntimeException("Key should be less than " + MAX_KEY_SIZE);
        }
        boolean flag = false;
        int remBytes = PAGE_SIZE - currentSize;
        if (remBytes < dataSize) {
            try {
                reset();
                flag = addValueToNewPage(dataSize, value);
                dataSize = calculateDataSize(key.length, value.length);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        int offset = buffer.position();
        if(flag) {
            buffer.put((byte) 1);
        } else {
            buffer.put((byte) 0);
        }
        //System.out.println("current size " + currentSize + " data size " + dataSize + " buffer " + (PAGE_SIZE - offset));
        buffer.putInt(key.length);
        buffer.putInt(value.length);
        buffer.put(key);
        buffer.put(value);
        this.currentSize += dataSize;
        return new Pair<>(this.currentFilePath, offset);
    }

    @Override
    public ByteBuffer get(String pagePath) throws IOException {
        if(this.currentFilePath.equals(pagePath)) {
            byte[] des = new byte[buffer.array().length];
            System.arraycopy(buffer.array(), 0, des,  0, des.length);
            return ByteBuffer.wrap(des);
        } else {
            return ByteBuffer.wrap(disk.retrieveDiskPage(pagePath));
        }
    }

    @Override
    public Pair<byte[], byte[]> get(ByteBuffer buffer, int position) {
        buffer.position(position);
        byte flag = buffer.get();
        int keyLen = buffer.getInt();
        int valueLen = buffer.getInt();
        byte[] key = new byte[keyLen];
        byte[] value = new byte[valueLen];
        buffer.get(key);
        buffer.get(value);
        if(flag == 1) {
            try {
                value = this.get(new String(value, StandardCharsets.UTF_8)).array();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new Pair<>(key, value);
    }


    private void reset() throws IOException {
        disk.writeDataToDiskPage(this.buffer.array(), this.currentFilePath);
        this.currentFilePath = filePath.getPath();
        this.currentSize = 0;
        buffer.clear();
    }

    private byte[] writeValueToFile(byte[] buffer) throws IOException {
        String path = filePath.getPath("disk_value_");
        disk.writeDataToDiskPage(buffer, path);
        return path.getBytes(StandardCharsets.UTF_8);
    }

    private int calculateDataSize(int keyLen, int valueLen) {
        return FLAG_BIT_SIZE + 2*ADDRESS_SIZE + keyLen + valueLen;
    }

    private boolean addValueToNewPage(int dataSize, byte[] value) {
        if(dataSize > PAGE_SIZE) {
            try {
                value = writeValueToFile(value);
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
