package top.naive.duck.reflection;

import top.naive.duck.common.ServiceLoader;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 上午11:34
 */
public class ProxyCreator {

    public static Object createProxy(Class<?> targetClass, ProxyType proxyType) {
        ServiceLoader<ProxyBuilder> serviceLoader = ServiceLoader.load(proxyType.name().toLowerCase());
        return serviceLoader.get(0).buildProxy(targetClass);
    }
}
