package java_programs;
import java.util.*;

/**
 * This class provides a method to calculate the square root of a number
 *
 * @author derricklin
 */
public final class SquareRootCalculator {
    private SquareRootCalculator() {
        // this utility class should not be instantiated
    }

    public static double calculateSquareRoot(final double number, final double epsilon) {
        double approximation = number / 2d;
        while (Math.abs(number-approximation) > epsilon) {
            approximation = 0.5d * (approximation + number / approximation);
        }
        return approximation;
    }
}