package top.naive.duck.reflection.impl.jdk;

import top.naive.duck.common.ObjectFactory;
import top.naive.duck.interceptor.MethodInvocation;
import top.naive.duck.interceptor.impl.JdkMethodInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 下午6:25
 */
public class JdkDynamicProxy implements InvocationHandler {

    private final Class<?> targetClass;
    private final Object targetObject;

    public JdkDynamicProxy(Class<?> targetClass) {
        this.targetClass = targetClass;
        this.targetObject = ObjectFactory.createObject(targetClass);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodInvocation methodInvocation = new JdkMethodInvocation(proxy, targetObject, method, args);
        return methodInvocation.proceed();
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                targetClass.getInterfaces(), this);
    }
}
