package top.naive.duck.interceptor;

import java.util.List;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/20 下午4:18
 */
public abstract class AbstractMethodInvocation implements MethodInvocation {

    protected List<PointcutAdvisor> pointcutAdvisors;
    protected AspectJAdvicesBuilder aspectJAdvicesBuilder = new AspectJAdvicesBuilder();
    protected int currentAdvisorIndex = -1;

    public void addPointcutAdvisor(PointcutAdvisor advisor) {
        pointcutAdvisors.add(advisor);
    }

    public AspectJAdvicesBuilder getAspectJAdvicesBuilder() {
        return aspectJAdvicesBuilder;
    }

    protected abstract Object invokeJoinPoint() throws Throwable;

    @Override
    public Object proceed() throws Throwable {
        if (this.currentAdvisorIndex == this.pointcutAdvisors.size() - 1) {
            return invokeJoinPoint();
        }

        PointcutAdvisor interceptor = this.pointcutAdvisors.get(++this.currentAdvisorIndex);

        return ((AbstractAspectJAdvice) interceptor).invoke(this);
    }
}
