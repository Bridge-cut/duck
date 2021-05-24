package top.naive.duck.demo;

import top.naive.duck.annotations.*;
import top.naive.duck.interceptor.MethodInvocation;

/**
 * 使用 @Aspect 标识该类为切面类
 *
 * 通知方法的执行顺序:
 * try {
 *     @Before
 *     method.invoke(obj, args);
 *     @AfterReturning
 * } catch (Exception e) {
 *     @AfterThrowing
 * } finally {
 *    @After
 * }
 *
 * 正常执行:
 * @Before(前置通知) -> @After(后置通知) -> @AfterReturning(返回通知)
 * 异常执行:
 * @Before(前置通知) -> @After(后置通知) -> @AfterThrowing(异常通知)
 *
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/20 上午11:27
 */
@Aspect
public class LogAspects {

    @Pointcut(value = "execution(* top.naive.duck.demo.Calculator.*(..))")
    public void pointCut(){}

//    @Pointcut(value = "execution(* top.naive.duck.demo.impl.CalculatorImpl.*(..))")
//    public void pointCut(){}

    @Before(value = "pointCut()")
    public void logStart(MethodInvocation invocation) {
        System.out.println(invocation.getMethod().getName() + " @Before");
    }

    @After(value = "pointCut()")
    public void logEnd(MethodInvocation invocation) {
        System.out.println(invocation.getMethod().getName() + " @After");
    }

    @AfterReturning(value = "pointCut()", returning = "result")
    public void logReturning(MethodInvocation invocation, @Param("result") Integer result) {
        System.out.println(invocation.getMethod().getName() + " @AfterReturning Result: " + result);
    }

    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void logThrowing(MethodInvocation invocation, @Param("exception") Exception exception) {
        System.out.println(invocation.getMethod().getName() + " @AfterThrowing Exception: " + exception.getMessage());
    }
}
