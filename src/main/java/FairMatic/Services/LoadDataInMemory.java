package FairMatic.Services;

import JavaConcurrencyAndThreading.Executor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoadDataInMemory {

    private final String path = "C:\\Users\\vikkuma\\Downloads\\fairmatic-getMilk-2022-08\\fairmatic-getMilk-2022-08";

    private final String dep = "\\departments.csv";
    private Departments departments = new Departments();

    private final String ords = "\\orders.csv";
    private Orderes orderes = new Orderes();

    private final String prds = "\\products.csv";
    private Products products = new Products();

    private final String ordPrds = "\\order_products__train.csv";
    private OrderProducts orderProducts = new OrderProducts();
    public void loadData() {
        ExecutorService[] executorServices = new ExecutorService[4];
        for(int i = 0; i < 4; i++) {
            executorServices[i] = Executors.newVirtualThreadPerTaskExecutor();
        }
        var de = CompletableFuture.runAsync(() -> departments.loadData(path+dep), executorServices[0]);
        var op = CompletableFuture.runAsync(() -> orderProducts.loadData(path+ordPrds), executorServices[1]);
        var or = CompletableFuture.runAsync(() -> orderes.loadData(path+ords), executorServices[2]);
       // var pr = CompletableFuture.runAsync(() -> products.loadData(path+prds), executorServices[3]);
        de.join();
        op.join();
        or.join();
      //  pr.join();
    }
}
