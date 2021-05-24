package top.naive.duck.convert;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/24 下午6:40
 */
public interface GenericConverter {

    /**
     * 类型转换
     * @param source 原对象
     * @param sourceType 原对象 class 类型
     * @param targetType 目标对象 class 类型
     * @return 转换后的对象
     */
    Object convert(Object source, Class<?> sourceType, Class<?> targetType);
}
