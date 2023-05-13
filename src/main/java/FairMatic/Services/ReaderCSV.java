package FairMatic.Services;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class ReaderCSV {
    public static <U> List<U> reader(String filePath, Class<? extends U> type) {
        try {
            List data = new CsvToBeanBuilder(new FileReader( filePath))
                    .withType(type)
                    .build()
                    .parse();
            return data;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            System.out.println("Exception occurs for file " + filePath);
            throw ex;
        }
    }
}
