package designPatterns.proxy;

import basic.annotation.MyMethodAnnotation;
import designPatterns.proxy.inf.MyService;

/**
 * 不使用代理类实现
 * 工作量特别大，如果项目中有多个类，多个方法，则要修改多次。
 * 违背了设计原则：开闭原则（OCP），对扩展开放，对修改关闭
 * 违背了设计原则：单一职责（SRP），每个方法除了要完成自己本身的功能，还要执行其他非业务操作
 * 违背了设计原则：依赖倒转（DIP），抽象不应该依赖细节。  即要针对接口编程
 *
 */
public class BusinessService implements MyService {
    LogService logService = new LogService();

    void noProxyService() {
        logService.beforelog();
        System.out.println("do noProxyService");
        logService.afterlog();
    }

    @Override
    @MyMethodAnnotation(name="myProxyService")
    public void service() {
        System.out.println("do service");
    }

    @Override
    public void service(String operator) {
        System.out.println(operator + "do service");
    }

}
