package java_programs.extra;

public final class MaximumWeightedSubset {
    private MaximumWeightedSubset() {
        // private constructor to prevent instantiation
    }

    public static int maxSubsetWeight(final int[] weights, final int bound) {
        int result = 0;
        if (weights.length != 0) {
            final int[][] maxWeight = new int[weights.length][bound];
            for (int w = 0; w <= bound; w++) {
                if (weights[0] <= w) {
                    maxWeight[0][w] = weights[0];
                } else {
                    maxWeight[0][w] = 0;
                }
            }

            for (int i = 1; i < weights.length; i++) {
                for (int w = 0; w <= bound; w++) {
                    if (weights[i] > w) {
                        maxWeight[i][w] = maxWeight[i-1][w];
                    } else {
                        final int include = weights[i] + maxWeight[i-1][w - weights[i]];
                        final int exclude = maxWeight[i-1][w];
                        maxWeight[i][w] = Math.max(include, exclude);
                    }
                }
            }
            result = maxWeight[weights.length-1][bound];
        }
        return result;
    }
}