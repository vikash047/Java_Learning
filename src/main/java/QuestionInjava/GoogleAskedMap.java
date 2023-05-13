package QuestionInjava;

public interface GoogleAskedMap<U> {
    void add(U item);
    boolean remove(U item);
    boolean isExist(U item);
}
