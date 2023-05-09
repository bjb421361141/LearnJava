package annotation;


import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyTypeAnnotation {
    String name() default "Is-TypeAnnotation";
}
