package java_programs;

/**
 * Utility class to find the maximum sublist sum.
 *
 * @author derricklin
 */
public final class MaxSublistSum {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private MaxSublistSum() {}

    /**
     * Computes the maximum sublist sum from an array of integers.
     *
     * @param arr the array of integers to compute the sum from
     * @return the maximum sublist sum
     */
    public static int maxSublistSum(final int... arr) {
        int maxEndingHere = 0;
        int maxSoFar = 0;

        for (final int element : arr) {
            maxEndingHere += element;
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }

        return maxSoFar;
    }
}