public class SquareRoot {
    public static double calculateSqrt(final double number, final double epsilon) {
        double approx = number / 2d;
        while (Math.abs(number - approx) > epsilon) {
            approx = 0.5d * (approx + number / approx);
        }
        return approx;
    }
}