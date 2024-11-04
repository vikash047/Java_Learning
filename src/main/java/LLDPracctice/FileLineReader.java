package LLDPracctice;


import java.util.Arrays;

public class FileLineReader {
    public static void main(String[] args) {
        printFileNameAndLineNumber();
    }

    public static void printFileNameAndLineNumber() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        // The element at index 2 represents the calling method
        StackTraceElement callingMethod = stackTraceElements[2];
        System.out.println(Arrays.toString(stackTraceElements));
        String fileName = callingMethod.getFileName();
        int lineNumber = callingMethod.getLineNumber();
        System.out.println(Integer.bitCount(100));

        System.out.println("File Name: " + fileName);
        System.out.println("Line Number: " + lineNumber);
    }
}
