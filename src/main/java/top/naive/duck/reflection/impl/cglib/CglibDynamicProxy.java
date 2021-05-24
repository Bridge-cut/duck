package top.naive.duck.reflection.impl.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import top.naive.duck.interceptor.MethodInvocation;
import top.naive.duck.interceptor.impl.CglibMethodInvocation;

import java.lang.reflect.Method;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 下午6:25
 */
public class CglibDynamicProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        MethodInvocation methodInvocation = new CglibMethodInvocation(obj, method, proxy, args);
        return methodInvocation.proceed();
    }
}
