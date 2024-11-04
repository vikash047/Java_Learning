package PersistenceStorage;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class StorageControllerTest {

    private final Disk disk = new Disk();
    private final FilePath filePath = new FilePath();

    private final Storage storage = new StorageImpl(15, disk);

    @Test
    void insert() {
        var st = new StorageController(storage);
        for(int i = 0; i <= 100000; i++) {
            var val = String.valueOf(i);
            st.insert(val, val);
        }
        try {
           for(int i = 1; i <= 100000; i += 1000) {
               var ret = st.getValue(String.valueOf(i));
               Assert.assertEquals(i, Integer.parseInt(ret));
           }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getValue() {
    }
}