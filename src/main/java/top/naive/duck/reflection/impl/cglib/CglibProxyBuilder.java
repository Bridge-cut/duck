package top.naive.duck.reflection.impl.cglib;

import net.sf.cglib.proxy.Enhancer;
import top.naive.duck.reflection.ProxyBuilder;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 下午4:22
 */
public class CglibProxyBuilder implements ProxyBuilder {

    @Override
    public Object buildProxy(Class<?> targetClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(new CglibDynamicProxy());

        return enhancer.create();
    }
}
