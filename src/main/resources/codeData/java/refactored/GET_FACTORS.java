package java_programs;
import java.util.*;

/**
 * Utility class for getting factors of a number.
 * @author derricklin
 */
public final class FactorGetter {
    
    private FactorGetter(){
        //private constructor to avoid object creation
    }

    /**
     * Method to get factors of a number.
     * @param number the number to get the factors of.
     * @return a list of factors of the number.
     */
    public static List<Integer> getFactors(final int number) {
        final int one = 1;
        List<Integer> factors = new ArrayList<>();
        if (number != one) {
            final int max = (int)(Math.sqrt(number) + 1.0);
            for (int i=2; i < max; i++) {
                if (number % i == 0) {
                    final List<Integer> prepend = new ArrayList<>();
                    prepend.add(i);
                    prepend.addAll(getFactors(number / i));
                    factors.addAll(prepend);
                    break;
                }
            }
        }
        return factors;
    }
}