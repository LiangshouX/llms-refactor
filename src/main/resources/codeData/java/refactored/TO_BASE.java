package java_programs;

import java.util.*;

/**
 *
 * @author derricklin
 */
public final class ToBaseUtil {
    private ToBaseUtil() {
        // private constructor to prevent instantiation
    }
    
    public static String toBase(int number, final int base) {
        StringBuilder result = new StringBuilder();
        final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int index;
        while (number > 0) {
            index = number % base;
            number = number / base; // floor division?
            result.append(alphabet.charAt(index));
        }

        return result.toString();
    }
}