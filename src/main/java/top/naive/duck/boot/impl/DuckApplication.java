package top.naive.duck.boot.impl;

import top.naive.duck.boot.AbstractApplicationContext;
import top.naive.duck.boot.ApplicationContext;
import top.naive.duck.boot.ApplicationContextHolder;
import top.naive.duck.interceptor.AbstractAspectJAdvice;
import top.naive.duck.parsing.AnnotationResolver;
import top.naive.duck.parsing.AspectJExpressionPointcut;
import top.naive.duck.parsing.ExpressionHolder;
import top.naive.duck.parsing.impl.AnnotationResolverInitializer;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 下午8:16
 */
public class DuckApplication extends AbstractApplicationContext {

    private final Class<?> bootClass;
    private final List<AnnotationResolver> annotationResolvers = new ArrayList<>();

    public DuckApplication(Class<?> bootClass) {
        this.bootClass = bootClass;

        ApplicationContextHolder.setApplicationContext(this);
        new AnnotationResolverInitializer(this, annotationResolvers).init();
    }

    public static ApplicationContext run(Class<?> bootClass, Object[] args) {
        return new DuckApplication(bootClass).run(args);
    }

    public ApplicationContext run(Object[] args) {
        Annotation[] annotations = bootClass.getAnnotations();
        for (Annotation annotation : annotations) {
            for (AnnotationResolver annotationResolver : annotationResolvers) {
                if (annotationResolver.support(annotation)) {
                    annotationResolver.parse(annotation);
                }
            }
        }

        handleEarlyAdvices();

        return this;
    }

    private void handleEarlyAdvices() {
        for (String expression : earlyAdvices.keySet()) {
            AspectJExpressionPointcut pointcut;
            List<AbstractAspectJAdvice> advices = earlyAdvices.get(expression);

            if (expression.startsWith(ExpressionHolder.POINTCUT_PREFIX)) {
                pointcut = pointcuts.get(expression);
                if (pointcut == null) {
                    pointcut = new AspectJExpressionPointcut(expression);
                }
            } else {
                if (!expression.contains(".")) {
                    expression = advices.get(0).getAspectMethod().getDeclaringClass().getName() + "." + expression;
                }

                pointcut = pointcuts.get(expression);
                if (pointcut == null) {
                    throw new RuntimeException("不存在 " + expression + " 对应的 @Pointcut 方法");
                }
            }

            for (AbstractAspectJAdvice advice : advices) {
                advice.setPointcut(pointcut);
            }
        }
    }

    @Override
    public Class<?> getBootClass() {
        return bootClass;
    }

    public List<AnnotationResolver> getAnnotationResolvers() {
        return annotationResolvers;
    }
}
