package FairMatic.Models;

public class Summary {
    String departName;
    Integer depId;
    double percentage;


    public Summary(Integer depId, double percentage) {
        this.depId = depId;
        this.percentage = percentage;
    }
}
