package LLDPracctice.ConfigurationService;

public interface Publisher {
    void register(Subscriber subscriber);
    void unRegister(Subscriber subscriber);
}
