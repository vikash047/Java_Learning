package LLDPracctice.ConfigurationService;

public interface Subscriber<T> {
    void handle(T configuration);
    String getSubscriberId();
    String getConfigurationName();
}
