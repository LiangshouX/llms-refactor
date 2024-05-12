public class MathUtils {

    private MathUtils() {
        // private constructor to prevent instantiation
    }

    public static int greatestCommonDivisor(final int number1, final int number2) {
        if (number2 == 0) {
            return number1;
        } else {
            return greatestCommonDivisor(number1 % number2, number2);
        }
    }
}