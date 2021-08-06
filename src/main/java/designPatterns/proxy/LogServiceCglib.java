package designPatterns.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 */
public class LogServiceCglib implements MethodInterceptor {
    LogService logger = new LogService();
    Object target;

    LogServiceCglib() {
    }

    public Object getProxyObject(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(this);
        enhancer.setSuperclass(target.getClass());
        return enhancer.create(); //生成代理对象
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        logger.beforelog();
        Object result = methodProxy.invoke(target, objects);
        logger.afterlog();
        return result;
    }
}
