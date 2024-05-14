package java_programs;

/**
 * Utility class for checking the validity of a parenthesization.
 *
 */
public final class IsValidParenthesization {

    private IsValidParenthesization() {
        // This is a utility class. It is not supposed to be instantiated.
    }

    /**
     * Checks whether the given string represents a valid parenthesization.
     *
     * @param parens the string to check
     * @return {@code true} if the string represents a valid parenthesization,
     *         {@code false} otherwise
     */
    public static boolean isValidParenthesization(final String parens) {
        int depth = 0;
        boolean isValid = true;

        for (int i = 0; i < parens.length(); i++) {
            final char paren = parens.charAt(i);
            if (paren == '(') {
                depth++;
            } else {
                depth--;
                if (depth < 0) {
                    isValid = false;
                    break;
                }
            }
        }

        return isValid;
    }
}