package top.naive.duck.convert;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/24 下午7:52
 */
public interface ConversionService {

    /**
     * 类型转换
     * @param source 原对象
     * @param targetType 目标对象 class
     * @param <T> 目标对象
     * @return 目标对象
     */
    <T> T convert(Object source, Class<T> targetType);
}
