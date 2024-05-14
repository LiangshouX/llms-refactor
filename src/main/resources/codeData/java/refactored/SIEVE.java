package java_programs;

import java.util.List;
import java.util.ArrayList;

/**
 * Utility class for SIEVE operations.
 *
 * @author derricklin
 */
public final class SIEVE {

    private SIEVE() {
        // Prevent instantiation
    }

    public static boolean all(final List<Boolean> arr) {
        boolean result = true;
        for (final Boolean value : arr) {
            if (!value) {
                result = false;
                break;
            }
        }
        return result;
    }

    public static boolean any(final List<Boolean> arr) {
        boolean result = false;
        for (final Boolean value : arr) {
            if (value) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static List<Boolean> listComprehension(final int number, final List<Integer> primeNumbers) {
        final List<Boolean> comprehension = new ArrayList<>();
        for (final Integer prime : primeNumbers) {
            comprehension.add(number % prime > 0);
        }
        return comprehension;
    }

    public static List<Integer> sieve(final Integer maxNumber) {
        final List<Integer> primeNumbers = new ArrayList<>();
        for (int number = 2; number <= maxNumber; number++) {
            if (any(listComprehension(number, primeNumbers))) {
                primeNumbers.add(number);
            }
        }
        return primeNumbers;
    }
}