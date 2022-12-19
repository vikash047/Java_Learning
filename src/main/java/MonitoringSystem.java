import java.util.HashMap;
import java.util.Objects;

public class MonitoringSystem {
    private int getKey(String a, String b) {
        return Objects.hash(a, b);
    }
    /*
    public void logLatency(String applicationName, String api, int latencyInMills) {

    }

    public void logError(String applicationName, String api, int errorCode) {
    }

    public int getPercentileLatency(int percentile, String applicationName, String api) {
    }

    public int[] getTopErrors(String applicationName, String api) {
    }*/
}
