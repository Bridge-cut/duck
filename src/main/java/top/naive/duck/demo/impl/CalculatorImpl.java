package top.naive.duck.demo.impl;

import top.naive.duck.annotations.Component;
import top.naive.duck.annotations.Value;
import top.naive.duck.demo.Calculator;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/23 下午9:00
 */
@Component
public class CalculatorImpl implements Calculator {

    @Value
    private Integer firstNum;

    @Value(value = "cal.second")
    private Integer secondNum;

    public Integer getFirstNum() {
        return firstNum;
    }

    public void setFirstNum(Integer firstNum) {
        this.firstNum = firstNum;
    }

    public Integer getSecondNum() {
        return secondNum;
    }

    public void setSecondNum(Integer secondNum) {
        this.secondNum = secondNum;
    }

    @Override
    public Integer add() {
        Integer result = firstNum + secondNum;
//        Integer result = 1 + 2;
        System.out.println("===== add =====");
        System.out.println(result);
        System.out.println("===============");

        return result;
    }

    @Override
    public Integer div() {
        Integer result = firstNum / secondNum;
//        Integer result = 9 / 0;
        System.out.println("===== div =====");
        System.out.println(result);
        System.out.println("===============");

        return result;
    }
}
