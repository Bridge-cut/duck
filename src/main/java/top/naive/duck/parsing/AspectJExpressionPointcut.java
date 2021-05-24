package top.naive.duck.parsing;

/**
 * 仅保存注解信息
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/20 下午12:15
 */
public class AspectJExpressionPointcut {

    private final String regxExpression;
    private final String signature;

    public AspectJExpressionPointcut(String regxExpression) {
        this(regxExpression, regxExpression);
    }

    public AspectJExpressionPointcut(String regxExpression, String signature) {
        this.regxExpression = regxExpression;
        this.signature = signature;
    }

    public String getRegxExpression() {
        return regxExpression;
    }

    public String getSignature() {
        return signature;
    }
}
