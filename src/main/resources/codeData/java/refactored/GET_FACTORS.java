import java.util.ArrayList;
import java.util.List;

public class FactorCalculator {
    public static List<Integer> calculateFactors(int number) {
        if (number == 1) {
            return new ArrayList<>();
        }
        int max = (int) (Math.sqrt(number) + 1.0);
        for (int i = 2; i < max; i++) {
            if (number % i == 0) {
                List<Integer> factors = new ArrayList<>();
                factors.add(i);
                factors.addAll(calculateFactors(number / i));
                return factors;
            }
        }
        return new ArrayList<>();
    }
}