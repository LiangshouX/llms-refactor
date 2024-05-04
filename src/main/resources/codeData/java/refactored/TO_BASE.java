
package java_programs;
import java.util.*;

public class ToBase {
    public static String toBase(int num, int b) {
        String result = "";
        String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int i;
        while (num > 0) {
            i = num % b;
            num = num / b; // floor division?
            result = result + String.valueOf(alphabet.charAt(i));
        }

        return result;
    }
}
