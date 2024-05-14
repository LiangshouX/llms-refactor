package java_programs.extra;
import java.util.*;
import java.io.*;
import java.awt.Point;
import static java.lang.Math.*;

public class NestedParensUtility {

    private static final int ZERO = 0;

    private NestedParensUtility() {
        throw new IllegalStateException("Utility class");
    }

    public static void main(String[] args) {
        try (final Scanner input = new Scanner(System.in)) {
            while(input.hasNext()) {
                final String parenthesesString = input.next();
                int depth = ZERO;
                for(int index = ZERO; index < parenthesesString.length(); index++) {
                    if(parenthesesString.charAt(index) == '(') {
                        depth++;
                    } else {
                        depth--;
                        if(depth < ZERO) {
                            logParenthesesDepth("0");
                            continue;
                        }
                    }
                }
                logParenthesesDepth(depth == ZERO ? "1" : "0");
            }
        }
    }

    private static void logParenthesesDepth(String message) {
        System.out.println(message);
    }

    public static <T> List<T> createList() { return new ArrayList<>(); }
    public static <K,V> Map<K,V> createMap() { return new HashMap<>(); }
    public static int convertStringToInt(final String inputString) { return Integer.parseInt(inputString); }
    public static long convertStringToLong(final String inputString) { return Long.parseLong(inputString); }
}