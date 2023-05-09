package proxy;

import proxy.inf.MyService;
import net.sf.cglib.core.DebuggingClassWriter;

/**
 * 代理模式：针对不同对象实现同一业务操作：日志记录等
 * Spring 中代理如果对象有接口优先使用 jdk 的代理类进行代理，否则使用 Cglib来动态实现代理
 * asm：直接对.class文件进行操作，往里插入业务操作代码
 *
 * cglib、AspectJ、java proxy类: 生成代理类，并将业务操作写入代理类中
 *      cglib ：针对被代理对象中的方法进行代理（不必实现接口），代理类继承于 代理对象 并实现了Factory接口，外加处理方法需要实现 MethodInterceptor
 *      java proxy:针对被代理对象继承的接口方法进行代理,代理类继承于java.lang.reflect.Proxy类，外加处理方法需要实现InvocationHandler
 *
 */
public class Main {
    public static void main(String[] args) {
        LogServiceEnhancer logServiceEnhancer = new LogServiceEnhancer();
//        System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles","true"); //保存生成的代理类 1.8之后
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true"); //保存生成的代理类
        MyService service = (MyService) logServiceEnhancer.getProxyObject(new BusinessService());
        service.service();
        System.out.println(service.getClass());

        LogServiceCglib logServiceCglib = new LogServiceCglib();
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "E://tmp");
        MyService service1 = (MyService) logServiceCglib.getProxyObject(new BusinessService());
        service1.service();
        System.out.println(service1.getClass());



    }
}
