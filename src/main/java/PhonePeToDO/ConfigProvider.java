package PhonePeToDO;

public interface ConfigProvider {
    /**
     * @return latest/updated config instance model
     */
    Config get();

    /**
     * Hints the provider loading mechanism to update it's cache. can't be blocked.
     */
    void configChanged();
}

class Config {
    // all config
}


