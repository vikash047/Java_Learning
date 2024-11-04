package LLDPracctice.AnnotationExample;

import java.lang.reflect.Field;
public class Main {
    public static void main(String[] args) throws NoSuchFieldException {
        MyClass obj = new MyClass();

        Class<?> cls = obj.getClass();
        Field field = cls.getDeclaredField("myField");

        if (field.isAnnotationPresent(MyAttribute.class)) {
            MyAttribute annotation = field.getAnnotation(MyAttribute.class);
            String attributeValue = annotation.value();

            System.out.println("Attribute value: " + attributeValue);
        }
        obj.someMethod();
    }
}
