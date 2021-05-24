package top.naive.duck.interceptor.impl;

import net.sf.cglib.proxy.MethodProxy;
import top.naive.duck.demo.impl.CalculatorImpl;
import top.naive.duck.interceptor.AbstractMethodInvocation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 下午7:02
 */
public class CglibMethodInvocation extends AbstractMethodInvocation {

    private final Object cglibProxy;
    private final Method method;
    private final MethodProxy methodProxy;
    private final Object[] arguments;

    public CglibMethodInvocation(Object cglibProxy, Method method, MethodProxy methodProxy, Object[] args) {
        this.cglibProxy = cglibProxy;
        this.method = method;
        this.methodProxy = methodProxy;
        this.arguments = args;
        this.pointcutAdvisors = getAspectJAdvicesBuilder()
                .buildAdvicesChain(method);
    }

    @Override
    protected Object invokeJoinPoint() throws Throwable {
        return methodProxy.invokeSuper(cglibProxy, arguments);
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public Method getMethod() {
        return method;
    }
}
