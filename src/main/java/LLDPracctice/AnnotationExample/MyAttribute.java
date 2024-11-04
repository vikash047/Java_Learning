package LLDPracctice.AnnotationExample;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MyAttribute {
    String value();
}
