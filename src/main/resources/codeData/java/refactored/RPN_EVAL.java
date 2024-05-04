
package java_programs;
import java.util.*;
import java.util.function.BinaryOperator;

public class RPN_EVAL {
    public static Double rpn_eval(ArrayList<Object> tokens) {
        Map<String, BinaryOperator<Double>> op = new HashMap<>();
        op.put("+", (a, b) -> a + b);
        op.put("-", (a, b) -> a - b);
        op.put("*", (a, b) -> a * b);
        op.put("/", (a, b) -> a / b);

        Stack<Double> stack = new Stack<>();

        for (Object token : tokens) {
            if (token instanceof Double) {
                stack.push((Double) token);
            } else {
                String operator = (String) token;
                Double b = stack.pop();
                Double a = stack.pop();
                BinaryOperator<Double> bin_op = op.get(operator);
                Double result = bin_op.apply(a, b);
                stack.push(result);
            }
        }

        return stack.pop();
    }
}
