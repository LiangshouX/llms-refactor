package java_programs;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility class for Shunting Yard algorithm.
 *
 * @author derricklin
 */
public final class ShuntingYard {

    private ShuntingYard() {
        // Restrict instantiation
    }

    public static List<Integer> shuntingYard(final List<Object> tokens) {
        final Map<String, Integer> precedence = new ConcurrentHashMap<>();
        precedence.put("+",1);
        precedence.put("-",1);
        precedence.put("*",2);
        precedence.put("/",2);

        final List<Integer> rpnTokens = new ArrayList<>(100);
        final Deque<String> opStack = new ArrayDeque<>();

        for (final Object token: tokens) {
            if (token instanceof Integer) {
                rpnTokens.add((Integer) token);
            } else {
                final String operator = (String) token;
                while (!opStack.isEmpty() && precedence.get(operator) <= precedence.get(opStack.getLast())) {
                    rpnTokens.add(Integer.valueOf(opStack.pop()));
                }
            }
        }

        while (!opStack.isEmpty()) {
            rpnTokens.add(Integer.valueOf(opStack.pop()));
        }

        return rpnTokens;
    }

}