package top.naive.duck.reflection;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 上午11:35
 */
public enum ProxyType {
    /**
     * JDK JDK 动态代理 至少需要实现一个 interface
     * CGLIB CGLIB 动态代理 基于子类
     */
    JDK,
    CGLIB
}
