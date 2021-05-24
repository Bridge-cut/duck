package top.naive.duck.parsing;

import java.lang.annotation.Annotation;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 下午8:41
 */
public interface AnnotationResolver {

    /**
     * 判断该 AnnotationResolver 是否支持解析入参 annotation
     * @param annotation annotation
     * @return true 则支持解析
     */
    public boolean support(Annotation annotation);

    /**
     * 解析对应的 annotation
     * @param annotation 待解析的 annotation
     */
    public void parse(Annotation annotation);

    /**
     * 解析对应的 class
     * @param clazz 待解析的 class
     */
    public void parse(Class<?> clazz);
}
