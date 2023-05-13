package FairMatic.Services;

import java.util.List;

public class Products implements LoadData{
    private List<FairMatic.Models.Products> productsList;
    @Override
    public void loadData(String path) {
        productsList = ReaderCSV.reader(path, FairMatic.Models.Products.class);
    }
}
