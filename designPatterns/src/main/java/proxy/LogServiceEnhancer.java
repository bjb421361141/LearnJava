package proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 在不破坏原有结构上使用聚合来代替继承复用类对象
 */
public class LogServiceEnhancer implements InvocationHandler {
    LogService logger = new LogService();
    Object target;

    LogServiceEnhancer() {
    }

    public Object getProxyObject(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(), //类加载器
                target.getClass().getInterfaces(),  //获得被代理对象的所有接口
                this);  //InvocationHandler对象
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.beforelog();
        System.out.println(proxy.getClass());  //com.sun.proxy.$Proxy0  系统生成的代理类
        //---------方法信息描述
        System.out.println("方法名称:" + method.getName());
        //这边的方法传的是代理类的Method对象所以找不到注解信息
        for (Annotation annotations : method.getDeclaredAnnotations()) {
            System.out.println("方法声明" + annotations.annotationType().getName());
        }
        Object result = method.invoke(target, args);
        logger.afterlog();
        return result;
    }
}
