public class LevenshteinDistanceCalculator {
    public static int calculateLevenshteinDistance(final String source, final String target) {
        if (source.isEmpty()) {
            return target.length();
        } else if (target.isEmpty()) {
            return source.length();
        } else if (source.charAt(0) == target.charAt(0)) {
            return 1 + calculateLevenshteinDistance(source.substring(1), target.substring(1));
        } else {
            return 1 + Math.min(
                calculateLevenshteinDistance(source, target.substring(1)),
                Math.min(
                    calculateLevenshteinDistance(source.substring(1), target.substring(1)),
                    calculateLevenshteinDistance(source.substring(1), target)
                )
            );
        }
    }
}