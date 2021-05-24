package top.naive.duck.common.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 下午3:35
 */
public class Resources {

    private static final ClassLoaderWrapper CLASS_LOADER_WRAPPER = new ClassLoaderWrapper();

    /**
     * 返回类路径上的资源作为 InputStream 对象
     *
     * @param resource 待寻找的资源
     * @return 资源对应的 InputStream 对象
     * @throws IOException 如果找不到或无法读取资源
     */
    public static InputStream getResourceAsStream(String resource) throws IOException {
        return getResourceAsStream(null, resource);
    }

    /**
     * 返回类路径上的资源作为 InputStream 对象
     *
     * @param loader   用于获取资源的类加载器
     * @param resource 待寻找的资源
     * @return 资源对应的 InputStream 对象
     * @throws IOException 如果找不到或无法读取资源
     */
    public static InputStream getResourceAsStream(ClassLoader loader, String resource) throws IOException {
        InputStream in = CLASS_LOADER_WRAPPER.getResourceAsStream(resource, loader);
        if (in == null) {
            throw new IOException("无法找到资源 " + resource);
        }
        return in;
    }

    /**
     * 加载类
     *
     * @param className - 要获取的类
     * @return 加载的类
     * @throws ClassNotFoundException 如果找不到或无法读取资源
     */
    public static Class<?> classForName(String className) throws ClassNotFoundException {
        return CLASS_LOADER_WRAPPER.classForName(className);
    }
}
