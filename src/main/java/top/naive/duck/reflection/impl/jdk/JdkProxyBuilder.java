package top.naive.duck.reflection.impl.jdk;

import top.naive.duck.reflection.ProxyBuilder;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 下午4:21
 */
public class JdkProxyBuilder implements ProxyBuilder {

    @Override
    public Object buildProxy(Class<?> targetClass) {
        return new JdkDynamicProxy(targetClass).getProxy();
    }
}
