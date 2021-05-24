package top.naive.duck.convert.impl;

import top.naive.duck.convert.GenericConverter;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/24 下午7:33
 */
public class StringToNumberConverter implements GenericConverter {

    @Override
    public Object convert(Object source, Class<?> sourceType, Class<?> targetType) {
        if (targetType.equals(Integer.class)) {
            return Integer.valueOf((String) source);
        } else if (targetType.equals(Long.class)) {
            return Long.valueOf((String) source);
        } else {
            throw new IllegalArgumentException(
                    "无法将 String [" + source + "] 转换为 [" + targetType.getName() + "]");
        }
    }
}
