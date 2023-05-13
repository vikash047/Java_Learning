package FairMatic.Services;

import FairMatic.Models.OrderedProducts;

import java.util.List;

public class OrderProducts implements LoadData{
    List<OrderedProducts> orderedProducts;
    @Override
    public void loadData(String path) {
        orderedProducts = ReaderCSV.reader(path, OrderedProducts.class);
    }
}
