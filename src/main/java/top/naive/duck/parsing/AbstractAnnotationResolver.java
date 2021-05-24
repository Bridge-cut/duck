package top.naive.duck.parsing;

import top.naive.duck.boot.ApplicationContext;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 下午8:51
 */
public abstract class AbstractAnnotationResolver implements AnnotationResolver {

    protected final ClassLoader classLoader;
    protected ApplicationContext applicationContext;
    protected List<Class<?>> supportAnnotation;

    public AbstractAnnotationResolver() {
        this.classLoader = this.getClass().getClassLoader();
        this.supportAnnotation = new ArrayList<>();
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    @Override
    public boolean support(Annotation annotation) {
        return supportAnnotation.contains(annotation.annotationType());
    }

    /**
     * 交由子类决定是否覆盖
     * @param clazz 待解析的 class
     */
    @Override
    public void parse(Class<?> clazz) {}
}
