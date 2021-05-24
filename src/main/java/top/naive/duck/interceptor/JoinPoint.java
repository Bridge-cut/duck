package top.naive.duck.interceptor;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/20 上午11:34
 */
public interface JoinPoint {

    /**
     * 进入方法拦截链中的下一个 MethodInterceptor
     * @return 经过 list(MethodInterceptor) 处理后得到的 result
     * @throws Throwable 异常抛出
     */
    public Object proceed() throws Throwable;
}
