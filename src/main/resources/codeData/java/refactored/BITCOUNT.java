package java_programs;

/**
 *
 * @author derricklin
 */
public final class BitCount {
    private BitCount() {
        // This utility class has a non-private constructor.
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static int countBits(int number) {
        int count = 0;
        int value = number;
        while (value != 0) {
            value = (value ^ (value - 1));
            count++;
        }
        return count;
    }
}