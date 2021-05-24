package top.naive.duck.interceptor;

import top.naive.duck.parsing.AspectJExpressionPointcut;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/20 下午4:30
 */
public interface PointcutAdvisor {

    /**
     * 获取该增强器对应的注解信息
     * @return 注解信息
     */
    public AspectJExpressionPointcut getPointcutInfo();
}
