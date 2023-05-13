package FairMatic.Services;

import FairMatic.Models.Orders;

import java.util.List;

public class Orderes implements LoadData{
    private List<Orders> ordersList;
    @Override
    public void loadData(String path) {
        ordersList = ReaderCSV.reader(path, Orders.class);
    }
}
