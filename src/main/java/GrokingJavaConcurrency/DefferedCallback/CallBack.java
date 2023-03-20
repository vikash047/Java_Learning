package GrokingJavaConcurrency.DefferedCallback;

public class CallBack {
    long executeAt;
    String message;

    public CallBack(long executeAt, String message) {
        this.executeAt = System.currentTimeMillis() +  executeAt*1000;
        this.message = message;
    }
}
