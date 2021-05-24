package top.naive.duck.boot;

import top.naive.duck.interceptor.AbstractAspectJAdvice;
import top.naive.duck.parsing.AspectJExpressionPointcut;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 下午8:33
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    protected final Map<String, String> properties = new ConcurrentHashMap<>();
    protected final Map<String, AspectJExpressionPointcut> pointcuts = new ConcurrentHashMap<>();
    protected final Map<String, List<AbstractAspectJAdvice>> earlyAdvices = new ConcurrentHashMap<>();

    @Override
    public boolean containsProperty(String key) {
        return properties.containsKey(key);
    }

    @Override
    public String getProperty(String key) {
        if (!containsProperty(key)) {
            throw new RuntimeException("指定 key=" + key + " 不存在");
        }

        return properties.get(key);
    }

    @Override
    public void addProperty(String key, String value) {
        properties.put(key, value);
    }

    @Override
    public AspectJExpressionPointcut getPointcut(String key) {
        return pointcuts.get(key);
    }

    @Override
    public void addPointcut(String key, AspectJExpressionPointcut value) {
        pointcuts.put(key, value);
    }

    @Override
    public void addEarlyAdvice(String key, AbstractAspectJAdvice earlyAdvice) {
        if (!earlyAdvices.containsKey(key)) {
            earlyAdvices.put(key, new ArrayList<>());
        }
        earlyAdvices.get(key).add(earlyAdvice);
    }

    @Override
    public Set<AbstractAspectJAdvice> getAvailableAdvices() {
        Set<AbstractAspectJAdvice> advices = new HashSet<>();
        earlyAdvices.forEach((key, value) -> advices.addAll(value));
        return advices;
    }
}
