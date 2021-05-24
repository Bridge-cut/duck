package top.naive.duck.demo;

import top.naive.duck.annotations.ComponentScan;
import top.naive.duck.annotations.PropertyScan;
import top.naive.duck.boot.impl.DuckApplication;
import top.naive.duck.demo.impl.CalculatorImpl;
import top.naive.duck.reflection.ProxyCreator;
import top.naive.duck.reflection.ProxyType;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 下午4:27
 */
@PropertyScan(value = {"", "demo_properties"})
@ComponentScan(value = {"top.naive.duck.demo"})
public class DemoApplication {

    public static void main(String[] args) {
        DuckApplication.run(DemoApplication.class, args);

        Calculator calculator = (Calculator) ProxyCreator.createProxy(CalculatorImpl.class, ProxyType.JDK);
        calculator.add();
        calculator.div();

//        CalculatorImpl calculator = (CalculatorImpl) ProxyCreator.createProxy(CalculatorImpl.class, ProxyType.CGLIB);
//        calculator.add();
//        calculator.div();
    }
}
