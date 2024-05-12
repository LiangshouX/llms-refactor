public class RpnEvaluator {
    public static Double evaluateRpn(final ArrayList<Object> tokens) {
        final Map<String, BinaryOperator<Double>> operators = new HashMap<>();
        operators.put("+", (a, b) -> a + b);
        operators.put("-", (a, b) -> a - b);
        operators.put("*", (a, b) -> a * b);
        operators.put("/", (a, b) -> a / b);

        final Stack<Double> stack = new Stack<>();

        for (final Object token : tokens) {
            if (token instanceof Double) {
                stack.push((Double) token);
            } else {
                final String operator = (String) token;
                final Double operand1 = stack.pop();
                final Double operand2 = stack.pop();
                final BinaryOperator<Double> binaryOperator = operators.get(operator);
                final Double result = binaryOperator.apply(operand2, operand1);
                stack.push(result);
            }
        }

        return stack.pop();
    }
}