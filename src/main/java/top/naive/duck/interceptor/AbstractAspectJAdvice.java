package top.naive.duck.interceptor;

import top.naive.duck.boot.ApplicationContext;
import top.naive.duck.parsing.AspectJExpressionPointcut;

import java.lang.reflect.Method;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/20 下午4:32
 */
public abstract class AbstractAspectJAdvice implements PointcutAdvisor {

    protected Object aspectObject;
    protected final String value;
    protected Method aspectMethod;
    protected final ApplicationContext applicationContext;
    protected AspectJExpressionPointcut pointcut;

    public AbstractAspectJAdvice(ApplicationContext applicationContext, String value) {
        this.applicationContext = applicationContext;
        this.value = value;
        applicationContext.addEarlyAdvice(value, this);
    }

    public Method getAspectMethod() {
        return aspectMethod;
    }

    public void setPointcut(AspectJExpressionPointcut pointcut) {
        this.pointcut = pointcut;
    }

    public void setAspectObject(Object aspectObject) {
        this.aspectObject = aspectObject;
    }

    public void setAspectMethod(Method aspectMethod) {
        this.aspectMethod = aspectMethod;
    }

    public abstract Object invoke(MethodInvocation methodInvocation) throws Throwable;

    @Override
    public AspectJExpressionPointcut getPointcutInfo() {
        return pointcut;
    }
}
