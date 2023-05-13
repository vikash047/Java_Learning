package FairMatic;

import FairMatic.Services.LoadDataInMemory;

public class Main {
    public static void main(String[] args) {
        LoadDataInMemory loadDataInMemory = new LoadDataInMemory();
        loadDataInMemory.loadData();
    }
}
