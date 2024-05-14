package java_programs;

import java.util.Arrays;

/**
 * Utility class for calculating the possible change.
 *
 * @author derricklin
 */
public final class PossibleChange {

    private PossibleChange() {
        // Prevent instantiation
    }

    public static int calculatePossibleChange(final int[] coins, final int total) {
        int returnVal = 0; // Single return value
        if (total == 0) {
            returnVal = 1;
        } else if (total < 0) {
            returnVal = 0;
        } else {
            final int first = coins[0];
            final int[] rest = Arrays.copyOfRange(coins, 1, coins.length);
            returnVal = calculatePossibleChange(coins, total - first) + calculatePossibleChange(rest, total);
        }
        return returnVal;
    }
}