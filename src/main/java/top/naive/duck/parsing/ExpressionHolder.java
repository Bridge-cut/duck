package top.naive.duck.parsing;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/23 下午12:51
 */
public class ExpressionHolder {

    public static final String POINTCUT_PREFIX = "execution";
    public static final String PARAMS_SPLIT = ",";

    private final String rawExpression;
    private final ExecutionRegxMatcher regxMatcher;
    private String returnType;
    private String methodName;
    private String[] paramTypes;

    public ExpressionHolder(String rawExpression, ExecutionRegxMatcher regxMatcher) {
        this.rawExpression = rawExpression;
        this.regxMatcher = regxMatcher;
        build();
    }

    public String getRawExpression() {
        return rawExpression;
    }

    public String getReturnType() {
        return returnType;
    }

    public String getMethodName() {
        return methodName;
    }

    public String[] getParamTypes() {
        return paramTypes;
    }

    public void build() {
        String expression = getRawExpression();
        if (expression.contains(POINTCUT_PREFIX)) {
            expression = removeMark(expression);
        }

        int spaceIndex = expression.indexOf(" ");
        int bracketIndex = expression.indexOf("(");
        returnType = expression.substring(0, spaceIndex);
        methodName = expression.substring(spaceIndex + 1, bracketIndex);

        String paramTypeExpr = expression.substring(bracketIndex + 1, expression.length() - 1);
        if (paramTypeExpr.length() == 0) {
            paramTypes = new String[0];
        } else if (paramTypeExpr.contains(PARAMS_SPLIT)) {
            paramTypes = paramTypeExpr.split(PARAMS_SPLIT);
        } else {
            paramTypes = new String[]{ paramTypeExpr };
        }
    }

    public boolean match(ExpressionHolder regxExprHolder) {
        return regxMatcher.regxMatch(returnType, regxExprHolder.getReturnType())
                && regxMatcher.regxMatch(methodName, regxExprHolder.getMethodName())
                && regxMatcher.regxMatch(paramTypes, regxExprHolder.getParamTypes());
    }

    private String removeMark(String rawExpression) {
        return rawExpression.substring(POINTCUT_PREFIX.length() + 1, rawExpression.length() - 1);
    }
}
