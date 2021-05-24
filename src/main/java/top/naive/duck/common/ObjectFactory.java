package top.naive.duck.common;

import top.naive.duck.annotations.Value;
import top.naive.duck.parsing.impl.ParseAnnotationResolver;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 下午3:39
 */
public class ObjectFactory {

    private static final Map<Class<?>, Object> CACHED_OBJECTS = new ConcurrentHashMap<>();

    public static Object createObject(Class<?> clazz) {
        try {
            if (CACHED_OBJECTS.containsKey(clazz)) {
                return CACHED_OBJECTS.get(clazz);
            }

            Object result = clazz.getDeclaredConstructor().newInstance();
            if (shouldInject(clazz)) {
                injectValue(result, clazz);
            }

            CACHED_OBJECTS.put(clazz, result);
            return result;
        } catch (InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean shouldInject(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(Value.class) != null) {
                return true;
            }
        }

        return false;
    }

    private static void injectValue(Object result, Class<?> clazz) {
        ParseAnnotationResolver parser = (ParseAnnotationResolver) CACHED_OBJECTS.get(ParseAnnotationResolver.class);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(Value.class) != null) {
                parser.parse(result, clazz, field);
            }
        }
    }
}
