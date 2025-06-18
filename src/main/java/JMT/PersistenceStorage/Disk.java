package JMT.PersistenceStorage;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Disk {
    public void writeDataToDiskPage(byte[] byteBuffer, String filePathName) throws IOException {
        RandomAccessFile diskFile = new RandomAccessFile(filePathName, "rw");
        diskFile.write(byteBuffer);
        diskFile.close();
    }

    public byte[] retrieveDiskPage(String fileName) throws IOException {
        RandomAccessFile diskFile = new RandomAccessFile(fileName, "r");
        long len = diskFile.length();
        byte[] byteBuffer = new byte[(int)len];
        diskFile.readFully(byteBuffer);
        return byteBuffer;
    }
}
