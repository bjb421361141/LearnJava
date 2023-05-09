package proxy.inf;

import annotation.MyMethodAnnotation;

public interface MyService {
    void service();

    @MyMethodAnnotation
    void service(String operator);
}
