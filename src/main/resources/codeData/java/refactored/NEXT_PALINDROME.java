package java_programs;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

public final class NextPalindrome {

    private static final int NINE = 9;
    private static final int ZERO = 0;
    private static final int ONE = 1;

    private NextPalindrome() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String nextPalindrome(final int... digitList) {
        int highMiddlePoint = Math.floorDiv(digitList.length, 2);
        int lowMiddlePoint = Math.floorDiv(digitList.length - 1, 2);
        String result = null;

        while (highMiddlePoint < digitList.length && lowMiddlePoint >= 0) {
            if (digitList[highMiddlePoint] == NINE) {
                digitList[highMiddlePoint] = ZERO;
                digitList[lowMiddlePoint] = ZERO;
                highMiddlePoint += 1;
                lowMiddlePoint -= 1;
            } else {
                digitList[highMiddlePoint] += 1;
                if (lowMiddlePoint != highMiddlePoint) {
                    digitList[lowMiddlePoint] += 1;
                }
                result = Arrays.toString(digitList);
            }
        }

        if (highMiddlePoint >= digitList.length || lowMiddlePoint < 0) {
            final List<Integer> otherwise = new ArrayList<>();
            otherwise.add(ONE);
            otherwise.addAll(Collections.nCopies(digitList.length, ZERO));
            otherwise.add(ONE);
            result = String.valueOf(otherwise);
        }
        
        return result;
    }
}