package java_programs.extra;

public final class LevenshteinUtility {
    private LevenshteinUtility() {
        // Prevent instantiation
    }

    public static int levenshtein(final String source, final String target) {
        int result = 0;
        if (source.equals("") && !target.equals("")) {
            result = target.length();
        } else if (!source.equals("") && target.equals("")) {
            result = source.length();
        } else if (!source.equals("") && !target.equals("")) {
            if (source.charAt(0) == target.charAt(0)) {
                result = 1 + levenshtein(source.substring(1), target.substring(1));
            } else {
                int min1 = levenshtein(source, target.substring(1));
                int min2 = Math.min(levenshtein(source.substring(1), target.substring(1)),
                        levenshtein(source.substring(1), target));
                result = 1 + Math.min(min1, min2);
            }
        }
        return result;
    }
}