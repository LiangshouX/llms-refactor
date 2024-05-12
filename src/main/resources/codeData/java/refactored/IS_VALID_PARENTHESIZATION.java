
public class ParenthesisValidator {
    public static boolean isValidParenthesization(final String parentheses) {
        int depth = 0;
        for (int i = 0; i < parentheses.length(); i++) {
            char paren = parentheses.charAt(i);
            if (paren == '(') {
                depth++;
            } else {
                depth--;
                if (depth < 0) {
                    return false;
                }
            }
        }
        return depth == 0;
    }
}