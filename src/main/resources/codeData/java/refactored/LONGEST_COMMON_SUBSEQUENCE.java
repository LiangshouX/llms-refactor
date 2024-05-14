package java_programs;

/**
 *
 * @author derricklin
 */
public final class LongestCommonSubsequence {
    private LongestCommonSubsequence() {
        //this private constructor is added to hide the implicit public one.
    }

    public static String getLongestCommonSubsequence(final String stringA, final String stringB) {
        String result = "";
        if (!stringA.isEmpty() && !stringB.isEmpty()) {
            if (stringA.charAt(0) == stringB.charAt(0)) {
                result = stringA.charAt(0) + getLongestCommonSubsequence(stringA.substring(1), stringB);
            } else {
                final String firstSubsequence = getLongestCommonSubsequence(stringA, stringB.substring(1));
                final String secondSubsequence = getLongestCommonSubsequence(stringA.substring(1), stringB);
                final String result = firstSubsequence.length() >= secondSubsequence.length() ? firstSubsequence : secondSubsequence;
            }
        }
        return result;
    }
}