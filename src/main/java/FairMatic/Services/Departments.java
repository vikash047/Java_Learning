package FairMatic.Services;

import java.util.ArrayList;
import java.util.List;

public class Departments implements LoadData{
    private List<FairMatic.Models.Departments> allDep;

    public Departments() {
        allDep = new ArrayList<>();
    }

    @Override
    public void loadData(String path) {
        allDep = ReaderCSV.reader(path, FairMatic.Models.Departments.class);
    }
}
