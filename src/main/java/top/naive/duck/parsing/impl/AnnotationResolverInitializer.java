package top.naive.duck.parsing.impl;

import top.naive.duck.annotations.Aspect;
import top.naive.duck.annotations.Component;
import top.naive.duck.annotations.PropertyScan;
import top.naive.duck.boot.ApplicationContext;
import top.naive.duck.common.ServiceLoader;
import top.naive.duck.parsing.AbstractAnnotationResolver;
import top.naive.duck.parsing.AnnotationResolver;
import top.naive.duck.parsing.Initializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 下午8:46
 */
public class AnnotationResolverInitializer implements Initializer {

    private static final Map<Class<?>, String> RESOLVERS = new HashMap<>();

    private final ApplicationContext applicationContext;
    private final List<AnnotationResolver> annotationResolvers;

    static {
        RESOLVERS.put(Aspect.class, Aspect.class.getName());
        RESOLVERS.put(PropertyScan.class, PropertyScan.class.getName());
        RESOLVERS.put(Component.class, Component.class.getName());
    }

    public AnnotationResolverInitializer(ApplicationContext context, List<AnnotationResolver> annotationResolvers) {
        this.applicationContext = context;
        this.annotationResolvers = annotationResolvers;
    }

    @Override
    public void init() {
        for (String key : RESOLVERS.values()) {
            AbstractAnnotationResolver annotationResolver = (AbstractAnnotationResolver) ServiceLoader.load(key).get(0);
            annotationResolver.setApplicationContext(applicationContext);
            annotationResolvers.add(annotationResolver);
        }
    }
}
