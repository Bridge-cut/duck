package top.naive.duck.interceptor;

import java.lang.reflect.Method;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 下午6:57
 */
public interface MethodInvocation extends JoinPoint {

    /**
     * 获取参数作为数组对象
     * @return 方法使用的参数数组
     */
    Object[] getArguments();

    /**
     * 获取被调用的方法
     * @return 被调用的方法
     */
    Method getMethod();
}
