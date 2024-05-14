package java_programs.extra;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public final class NestedParens {
    private static final Logger LOGGER = Logger.getLogger( NestedParens.class.getName() );

    private NestedParens() {
        // This utility class is not publicly instantiable.
    }

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final String inputString = scanner.next();
        final int[] numArray = new int[inputString.length()];
        for(int index=0; index<inputString.length(); index++) {
            numArray[index] = inputString.charAt(index)=='(' ? 1 : -1;
        }

        if(isProperlyNested(numArray) == 1) {
            LOGGER.log(Level.INFO, "GOOD");
        } else {
            LOGGER.log(Level.INFO, "BAD");
        }
        scanner.close();
    }

    public static int isProperlyNested(final int[] numberArray) {
        int isBad = 0;
        int depth = 0;
        int index = 0;
        while(index < numberArray.length) {
            depth += numberArray[index];
            if(depth < 0) { 
                isBad = 1; 
                return 0;
            }
            index += 1;
        }
        return 1;
    }
}