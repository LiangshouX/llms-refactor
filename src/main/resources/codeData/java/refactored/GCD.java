package java_programs;
import java.util.*;

/**
 * Utility class for calculating Greatest Common Divisor.
 */
public final class GreatestCommonDivisor {

    private GreatestCommonDivisor() {
        // prevent instantiation
    }

    public static int calculateGcd(final int numberOne, int numberTwo) {
        int result = numberOne;
        while (numberTwo != 0) {
            int temp = numberTwo;
            numberTwo = numberOne % numberTwo;
            result = temp;
        }
        return result;
    }
}