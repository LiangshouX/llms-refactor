package java_programs;

import java.util.ArrayList;
import java.util.List;

public class Sieve {

    private Sieve() {
        // private constructor to prevent instantiation
    }

    public static boolean all(final List<Boolean> booleanList) {
        for (final boolean value : booleanList) {
            if (!value) {
                return false;
            }
        }
        return true;
    }

    public static boolean any(final List<Boolean> booleanList) {
        for (final boolean value : booleanList) {
            if (value) {
                return true;
            }
        }
        return false;
    }

    public static List<Boolean> buildComprehension(final int n, final List<Integer> primes) {
        final List<Boolean> builtComprehension = new ArrayList<>();
        for (final Integer p : primes) {
            builtComprehension.add(n % p > 0);
        }
        return builtComprehension;
    }

    public static List<Integer> sieve(final int max) {
        final List<Integer> primes = new ArrayList<>();
        for (int n = 2; n < max + 1; n++) {
            if (any(buildComprehension(n, primes))) {
                primes.add(n);
            }
        }
        return primes;
    }
}