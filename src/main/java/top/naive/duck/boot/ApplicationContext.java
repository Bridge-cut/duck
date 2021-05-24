package top.naive.duck.boot;

import top.naive.duck.interceptor.AbstractAspectJAdvice;
import top.naive.duck.parsing.AspectJExpressionPointcut;

import java.util.Set;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 下午8:29
 */
public interface ApplicationContext {

    /**
     * 获取应用启动类的 Class
     * @return 启动类 Class
     */
    public Class<?> getBootClass();

    /**
     * 判断配置文件中是否指定 key 对应的 value
     * @param key key
     * @return true 则存在
     */
    public boolean containsProperty(String key);

    /**
     * 获取配置文件中的 key 对应的 value
     * @param key key
     * @return value
     */
    public String getProperty(String key);

    /**
     * 添加通过配置文件传入的 key-value
     * @param key key-value
     * @param value value
     */
    public void addProperty(String key, String value);

    /**
     * 获取应用运行时对应 key 值的增强器注解信息
     * @param key key
     * @return 增强器注解信息
     */
    public AspectJExpressionPointcut getPointcut(String key);

    /**
     * 添加增强器注解信息
     * @param key 增强器注解信息对应的 key
     * @param value 增强器注解信息
     */
    public void addPointcut(String key, AspectJExpressionPointcut value);

    /**
     * 添加缺乏 AspectJExpressionPointcut 的 AbstractAspectJAdvice
     * @param key AbstractAspectJAdvice 需要 key 值的 AspectJExpressionPointcut
     * @param earlyAdvice 缺乏 AspectJExpressionPointcut 的 AbstractAspectJAdvice
     */
    public void addEarlyAdvice(String key, AbstractAspectJAdvice earlyAdvice);

    /**
     * 获取应用配置的所有增强器
     * @return 应用配置的所有增强器
     */
    public Set<AbstractAspectJAdvice> getAvailableAdvices();
}

