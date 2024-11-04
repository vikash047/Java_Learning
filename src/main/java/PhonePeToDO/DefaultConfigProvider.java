package PhonePeToDO;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DefaultConfigProvider implements ConfigProvider {
    private volatile Config config;
    private ReentrantLock lock = new ReentrantLock();

    private Condition waitForUpdate = lock.newCondition();
    private boolean isConfigChanged = false;

    private Boolean requests = false;
    @Override
    public Config get() {
        if(requests) {
            isConfigChanged = true;
        }
        lock.lock();

        if(isConfigChanged) {
            updateConfig();
        }

        try {
            while (isConfigChanged) {
                waitForUpdate.await();
                //updateConfig();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return config;
    }

    @Override
    public void configChanged() {
        requests = true;
    }

    private void updateConfig() {
       // while (requests) {
            var newConfig = ConfigFactory.get();
            requests = false;
            lock.lock();
            isConfigChanged = false;
            try {
                config = (Config) newConfig;
                waitForUpdate.signalAll();
            } finally {
                lock.unlock();
            }
       // }
    }
}
