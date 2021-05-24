package top.naive.duck.boot;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/21 下午9:52
 */
public class ApplicationContextHolder {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextHolder.applicationContext = applicationContext;
    }
}
