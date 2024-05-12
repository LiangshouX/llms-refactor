
public class BaseConverterUtil {

    public static String convertToBase(int number, int base) {
        StringBuilder result = new StringBuilder();
        String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int remainder;
        while (number > 0) {
            remainder = number % base;
            number = number / base;
            result.insert(0, alphabet.charAt(remainder));
        }

        return result.toString();
    }
}