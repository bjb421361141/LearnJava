package designPatterns.proxy.inf;

import basic.annotation.MyMethodAnnotation;

public interface MyService {
    void service();

    @MyMethodAnnotation
    void service(String operator);
}
