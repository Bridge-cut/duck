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
public class AspectJAfterReturningAdvice extends AbstractAspectJAdvice {

    private String returning;

    public AspectJAfterReturningAdvice(ApplicationContext applicationContext, String value) {
        super(applicationContext, value);
    }

    public void setReturning(String returning) {
        this.returning = returning;
    }

    private void invokeAfterReturningMethod(Object returnVale, MethodInvocation methodInvocation) throws Throwable {
        List<Object> args = new ArrayList<>();
        Parameter[] parameters = aspectMethod.getParameters();
        for (Parameter parameter : parameters) {
            Annotation annotation = parameter.getAnnotation(Param.class);
            if (annotation != null) {
                if (!((Param) annotation).value().equals(this.returning)) {
                    throw new RuntimeException("@Param 的 value 值必须与方法上的注解 @AfterReturning 中的 value 值相同");
                } else {
                    args.add(returnVale);
                    continue;
                }
            }
            args.add(methodInvocation);
        }

        aspectMethod.invoke(aspectObject, args.toArray());
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object returnValue = methodInvocation.proceed();
        invokeAfterReturningMethod(returnValue, methodInvocation);
        return returnValue;
    }
}
