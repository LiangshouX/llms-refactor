package java_programs;

/**
 * Levenshtein class for string comparison
 * @author derricklin
 */
public final class LEVENSHTEIN {

    private LEVENSHTEIN() {
        // private constructor to prevent instantiation
    }

    public static int levenshtein(final String source, final String target) {
        int result;
        if (source.isEmpty() || target.isEmpty()) {
            result = source.isEmpty() ? target.length() : source.length();
        } else if (source.charAt(0) == target.charAt(0)) {
            result = 1 + levenshtein(source.substring(1), target.substring(1));
        } else {
            result = 1 + Math.min(Math.min(
                    levenshtein(source,              target.substring(1)),
                    levenshtein(source.substring(1), target.substring(1))),
                    levenshtein(source.substring(1), target)
            );
        }
        return result;
    }
}