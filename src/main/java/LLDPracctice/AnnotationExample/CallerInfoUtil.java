package LLDPracctice.AnnotationExample;

import java.util.Arrays;

public class CallerInfoUtil {
    public static void getCallerInfo() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        System.out.println(Arrays.toString(stackTraceElements));
        if (stackTraceElements.length >= 4) {
            StackTraceElement caller = stackTraceElements[3];
            String callerMethodName = caller.getMethodName();
            String callerClassName = caller.getClassName();
            String callerFileName = caller.getFileName();
            int callerLineNumber = caller.getLineNumber();

            System.out.println("Caller Method: " + callerMethodName);
            System.out.println("Caller Class: " + callerClassName);
            System.out.println("Caller File: " + callerFileName);
            System.out.println("Caller Line: " + callerLineNumber);
        }
    }
}