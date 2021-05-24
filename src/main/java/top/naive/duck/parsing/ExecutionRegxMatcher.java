package top.naive.duck.parsing;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/23 上午10:53
 */
public class ExecutionRegxMatcher {

    private static final String COMMON_WILDCARD = "*";
    private static final String PARAM_WILDCARD = "..";

    public boolean match(String methodSignature, String regxExpression) {
        if (!regxExpression.startsWith(ExpressionHolder.POINTCUT_PREFIX)) {
            throw new RuntimeException("切入点表达式必须以 execution 开头");
        }

        ExpressionHolder methodExprHolder = new ExpressionHolder(methodSignature, this);
        ExpressionHolder regxExprHolder = new ExpressionHolder(regxExpression, this);

        return methodExprHolder.match(regxExprHolder);
    }

    public boolean regxMatch(Object rawValue, Object regxValue) {
        if (rawValue.getClass().equals(String.class)) {
            return stringMatch((String) rawValue, (String) regxValue);
        }

        return stringArrayMatch((String[]) rawValue, (String[]) regxValue);
    }

    private boolean stringMatch(String rawValue, String regxValue) {
        if (regxValue.contains(".")) {
            boolean result = true;
            String[] rawStrings = rawValue.split("\\.");
            String[] regxStrings = regxValue.split("\\.");
            for (int i = 0; i < rawStrings.length; i++) {
                result = result && stringMatch(rawStrings[i], regxStrings[i]);
            }

            return result;
        }

        return rawValue.equals(regxValue) || regxValue.equals(COMMON_WILDCARD);
    }

    private boolean stringArrayMatch(String[] rawValue, String[] regxValue) {
        if (regxValue.length == 1 && regxValue[0].equals(PARAM_WILDCARD)) {
            return true;
        }

        if (rawValue.length != regxValue.length) {
            return false;
        }

        int length = rawValue.length;
        for (int i = 0; i < length; i++) {
            if (!rawValue[i].equals(regxValue[i])) {
                return false;
            }
        }

        return true;
    }
}
