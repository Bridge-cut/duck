package top.naive.duck.convert.impl;

import top.naive.duck.convert.ConversionService;
import top.naive.duck.convert.GenericConverter;
import top.naive.duck.convert.support.ConvertiblePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/24 下午6:41
 */
public class GenericConversionService implements ConversionService {

    private static final Map<ConvertiblePair, GenericConverter> CONVERTERS = new ConcurrentHashMap<>();

    static {
        CONVERTERS.put(new ConvertiblePair(String.class, Number.class), new StringToNumberConverter());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T convert(Object source, Class<T> targetType) {
        Class<?> sourceType = source.getClass();
        GenericConverter converter = getConverter(sourceType, targetType);
        if (converter == null) {
            throw new RuntimeException("不存在 " + sourceType + " 类型转换为 " + targetType + " 类型的类型转换器");
        }

        return (T) converter.convert(source, sourceType, targetType);
    }

    private GenericConverter getConverter(Class<?> sourceType, Class<?> targetType) {
        List<Class<?>> sourceCandidates = getClassHierarchy(sourceType);
        List<Class<?>> targetCandidates = getClassHierarchy(targetType);
        for (Class<?> sourceCandidate : sourceCandidates) {
            for (Class<?> targetCandidate : targetCandidates) {
                ConvertiblePair convertiblePair = new ConvertiblePair(sourceCandidate, targetCandidate);
                GenericConverter converter = CONVERTERS.get(convertiblePair);
                if (converter != null) {
                    return converter;
                }
            }
        }

        return null;
    }

    private List<Class<?>> getClassHierarchy(Class<?> clazz) {
        List<Class<?>> hierarchy = new ArrayList<>();
        while (clazz != null) {
            hierarchy.add(clazz);
            clazz = clazz.getSuperclass();
        }

        return hierarchy;
    }
}
