package top.naive.duck.interceptor.impl;

import top.naive.duck.boot.ApplicationContext;
import top.naive.duck.interceptor.AbstractAspectJAdvice;
import top.naive.duck.interceptor.MethodInvocation;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/20 下午5:50
 */
public class AspectJAfterAdvice extends AbstractAspectJAdvice {

    public AspectJAfterAdvice(ApplicationContext applicationContext, String value) {
        super(applicationContext, value);
    }

    private void invokeAfterMethod(MethodInvocation methodInvocation) throws Throwable {
        if (aspectMethod.getParameterCount() > 0) {
            aspectMethod.invoke(aspectObject, methodInvocation);
        } else {
            aspectMethod.invoke(aspectObject);
        }
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        try {
            return methodInvocation.proceed();
        } finally {
            invokeAfterMethod(methodInvocation);
        }
    }
}
