package top.naive.duck.reflection;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 下午4:16
 */
public interface ProxyBuilder {

    /**
     * 由具体的子类实现，返回的代理可能包括 MethodInterceptor
     *
     * @param targetClass 需要创建代理的 Class
     * @return 代理对象
     */
    public Object buildProxy(Class<?> targetClass);
}
