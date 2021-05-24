package top.naive.duck.interceptor.impl;

import top.naive.duck.interceptor.AbstractMethodInvocation;

import java.lang.reflect.Method;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 下午7:02
 */
public class JdkMethodInvocation extends AbstractMethodInvocation {

    private final Object proxy;
    private final Object targetObject;
    private final Method method;
    private final Object[] arguments;

    public JdkMethodInvocation(Object proxy, Object target, Method method, Object[] args) {
        this.proxy = proxy;
        this.targetObject = target;
        this.method = method;
        this.arguments = args;
        this.pointcutAdvisors = getAspectJAdvicesBuilder()
                .buildAdvicesChain(method);
    }

    @Override
    protected Object invokeJoinPoint() throws Throwable {
        return method.invoke(targetObject, arguments);
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
