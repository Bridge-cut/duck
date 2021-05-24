package top.naive.duck.parsing.impl;

import top.naive.duck.annotations.*;
import top.naive.duck.common.ObjectFactory;
import top.naive.duck.interceptor.AbstractAspectJAdvice;
import top.naive.duck.interceptor.impl.AspectJAfterAdvice;
import top.naive.duck.interceptor.impl.AspectJAfterReturningAdvice;
import top.naive.duck.interceptor.impl.AspectJAfterThrowingAdvice;
import top.naive.duck.interceptor.impl.AspectJBeforeAdvice;
import top.naive.duck.parsing.AbstractAnnotationResolver;
import top.naive.duck.parsing.AspectJExpressionPointcut;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 下午9:15
 */
public class AopAnnotationResolver extends AbstractAnnotationResolver {

    public AopAnnotationResolver() {
        this.supportAnnotation.add(Aspect.class);
    }

    @Override
    public void parse(Class<?> clazz) {
        Object aspectObject = ObjectFactory.createObject(clazz);
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Annotation annotation = method.getAnnotations()[0];
            try {
                instantiateAdvice(aspectObject, method, annotation);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void parse(Annotation annotation) {

    }

    private void instantiateAdvice(Object aspectObject, Method method, Annotation annotation) throws Exception {
        AbstractAspectJAdvice duckAdvice = null;
        AspectJExpressionPointcut expressionPointcut;

        Class<? extends Annotation> aClass = annotation.annotationType();
        if (Pointcut.class.equals(aClass)) {
            if (method.getParameterCount() > 0) {
                throw new Exception("指定注解 @Pointcut 的方法不允许存在入参");
            }

            expressionPointcut = new AspectJExpressionPointcut(((Pointcut) annotation).value().strip(),
                    method.getDeclaringClass().getName() + "." + method.getName() + "()");

            getApplicationContext().addPointcut(expressionPointcut.getRegxExpression(), expressionPointcut);
            getApplicationContext().addPointcut(expressionPointcut.getSignature(), expressionPointcut);
            return;
        } else if (Before.class.equals(aClass)) {
            duckAdvice = new AspectJBeforeAdvice(getApplicationContext(),
                    ((Before) annotation).value());
        } else if (After.class.equals(aClass)) {
            duckAdvice = new AspectJAfterAdvice(getApplicationContext(),
                    ((After) annotation).value());
        } else if (AfterReturning.class.equals(aClass)) {
            duckAdvice = new AspectJAfterReturningAdvice(getApplicationContext(),
                    ((AfterReturning) annotation).value());
        } else if (AfterThrowing.class.equals(aClass)) {
            duckAdvice = new AspectJAfterThrowingAdvice(getApplicationContext(),
                    ((AfterThrowing) annotation).value());
        }

        assert duckAdvice != null : "AbstractAspectJAdvice duckAdvice 必须不为 null 才能走到此处";
        duckAdvice.setAspectMethod(method);
        duckAdvice.setAspectObject(aspectObject);
        handleExtraPros(duckAdvice, annotation);
    }

    private void handleExtraPros(AbstractAspectJAdvice duckAdvice, Annotation annotation) {
        Class<? extends Annotation> aClass = annotation.annotationType();
        if (AfterReturning.class.equals(aClass)) {
            ((AspectJAfterReturningAdvice) duckAdvice).setReturning(((AfterReturning) annotation).returning());
        } else if (AfterThrowing.class.equals(aClass)) {
            ((AspectJAfterThrowingAdvice) duckAdvice).setThrowing(((AfterThrowing) annotation).throwing());
        }
    }
}
