package top.naive.duck.interceptor;

import top.naive.duck.boot.ApplicationContext;
import top.naive.duck.boot.ApplicationContextHolder;
import top.naive.duck.parsing.ExecutionRegxMatcher;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/21 下午9:36
 */
public class AspectJAdvicesBuilder {

    private final ExecutionRegxMatcher executionMatcher = new ExecutionRegxMatcher();
    private final ApplicationContext applicationContext = ApplicationContextHolder.getApplicationContext();

    public List<PointcutAdvisor> buildAdvicesChain(Method method) {
        List<PointcutAdvisor> interceptors = new ArrayList<>();

        Class<?>[] classes = method.getParameterTypes();
        StringBuilder pointcutExpr = new StringBuilder(method.getReturnType().getName() + " ");
        pointcutExpr.append(method.getDeclaringClass().getName()).append(".").append(method.getName()).append("(");
        for (Class<?> param : classes) {
            pointcutExpr.append(param.getName()).append(",");
        }
        pointcutExpr.append(")");

        Set<AbstractAspectJAdvice> availableAdvices = applicationContext.getAvailableAdvices();
        for (AbstractAspectJAdvice advice : availableAdvices) {
            String regxExpression = advice.getPointcutInfo().getRegxExpression();

            if (executionMatcher.match(pointcutExpr.toString(), regxExpression)) {
                interceptors.add(advice);
            }
        }

        return interceptors;
    }
}
