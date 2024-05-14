package java_programs;

import java.util.List;
import java.util.Map;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BinaryOperator;

/**
 * Utility class for RPN Evaluation.
 * @author derricklin
 */
public final class RpnEval {

    private RpnEval() {
        // private constructor to hide the implicit public one
    }

    public static Double evaluateRpn(List<Object> tokens) {
        final Map<String, BinaryOperator<Double>> operatorToFunctionMap = new ConcurrentHashMap<>();
        operatorToFunctionMap.put("+", Double::sum);
        operatorToFunctionMap.put("-", (firstOperand, secondOperand) -> firstOperand - secondOperand);
        operatorToFunctionMap.put("*", (firstOperand, secondOperand) -> firstOperand * secondOperand);
        operatorToFunctionMap.put("/", (firstOperand, secondOperand) -> firstOperand / secondOperand);

        final Deque<Double> stack = new ArrayDeque<>();

        for (Object token : tokens) {
            if (token instanceof Double) {
                stack.push((Double) token);
            } else {
                String operator = (String) token;
                final Double firstOperand = stack.pop();
                final Double secondOperand = stack.pop();
                final BinaryOperator<Double> binaryOperator = operatorToFunctionMap.get(operator);
                Double result = binaryOperator.apply(firstOperand, secondOperand);
                stack.push(result);
            }
        }

        return stack.pop();
    }
}