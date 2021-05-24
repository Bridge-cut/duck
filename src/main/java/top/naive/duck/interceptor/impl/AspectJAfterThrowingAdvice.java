package top.naive.duck.interceptor.impl;

import top.naive.duck.annotations.Param;
import top.naive.duck.boot.ApplicationContext;
import top.naive.duck.interceptor.AbstractAspectJAdvice;
import top.naive.duck.interceptor.MethodInvocation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/20 下午5:50
 */
public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice {

    private String throwing;

    public AspectJAfterThrowingAdvice(ApplicationContext applicationContext, String value) {
        super(applicationContext, value);
    }

    public void setThrowing(String throwing) {
        this.throwing = throwing;
    }

    private void invokeAfterThrowingMethod(Throwable throwable, MethodInvocation methodInvocation) throws Throwable {
        List<Object> args = new ArrayList<>();
        Parameter[] parameters = aspectMethod.getParameters();
        for (Parameter parameter : parameters) {
            Annotation annotation = parameter.getAnnotation(Param.class);
            if (annotation != null) {
                if (!((Param) annotation).value().equals(this.throwing)) {
                    throw new RuntimeException("@Param 的 value 值必须与方法上的注解 @AfterThrowing 中的 value 值相同");
                } else {
                    args.add(throwable.getCause() == null ? throwable : throwable.getCause());
                    continue;
                }
            }
            args.add(methodInvocation);
        }

        aspectMethod.invoke(aspectObject, args.toArray());
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        try {
            return methodInvocation.proceed();
        }
        catch (Throwable ex) {
            invokeAfterThrowingMethod(ex, methodInvocation);
            throw ex;
        }
    }
}
