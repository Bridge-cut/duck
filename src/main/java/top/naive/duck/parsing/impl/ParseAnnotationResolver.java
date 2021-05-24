package top.naive.duck.parsing.impl;

import top.naive.duck.annotations.Component;
import top.naive.duck.annotations.Value;
import top.naive.duck.common.Reflector;
import top.naive.duck.convert.impl.GenericConversionService;
import top.naive.duck.parsing.AbstractAnnotationResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 下午9:50
 */
public class ParseAnnotationResolver extends AbstractAnnotationResolver {

    private static final String EMPTY_VALUE = "";
    private final GenericConversionService conversionService = new GenericConversionService();

    public ParseAnnotationResolver() {
        this.supportAnnotation.add(Component.class);
    }

    public void parse(Object sourceObject, Class<?> clazz, Field field) {
        Reflector reflector = new Reflector(clazz);
        Method method = reflector.getSetterMethod(field.getName());
        Class<?> paramType = reflector.getSetterType(field.getName());
        method.setAccessible(true);

        String injectKey = field.getAnnotation(Value.class).value();
        if (injectKey.equals(EMPTY_VALUE)) {
            injectKey = clazz.getSimpleName() + "." + field.getName();
        }
        injectKey = injectKey.substring(0, 1).toLowerCase().concat(injectKey.substring(1));

        try {
            method.invoke(sourceObject,
                    conversionService.convert(getApplicationContext().getProperty(injectKey), paramType));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parse(Annotation annotation) {}
}
