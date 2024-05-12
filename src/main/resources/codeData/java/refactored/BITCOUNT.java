
public class BitCountUtils {
    public static int countBits(int number) {
        int bitCount = 0;
        while (number != 0) {
            number = (number ^ (number - 1));
            bitCount++;
        }
        return bitCount;
    }
}