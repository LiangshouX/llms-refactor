import java.util.*;

public class ShuntingYard {
    public static List<Integer> shuntingYard(final List<Object> tokens) {
        Map<String, Integer> precedence = new HashMap<>();
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);

        List<Integer> rpntokens = new ArrayList<>(100);
        Deque<String> opstack = new ArrayDeque<>();

        for (final Object token : tokens) {
            if (token instanceof Integer) {
                rpntokens.add((Integer) token);
            } else {
                String operator = (String) token;
                while (!opstack.isEmpty() && precedence.get(operator) <= precedence.get(opstack.peekLast())) {
                    rpntokens.add(Integer.parseInt(opstack.pop()));
                }
            }
        }

        while (!opstack.isEmpty()) {
            rpntokens.add(Integer.parseInt(opstack.pop()));
        }

        return rpntokens;
    }
}