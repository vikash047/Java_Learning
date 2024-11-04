package LLDPracctice.AnnotationExample;

public class MyClass {
    @MyAttribute("example")
    private String myField;

    @CallerInfo
    public void someMethod() {
        CallerInfoUtil.getCallerInfo();
    }
    // ...
}