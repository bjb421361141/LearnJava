package annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyMethodAnnotation {
    String name() default "Is-MethodAnnotation";

    String url() default "www.dabai.com";

}
