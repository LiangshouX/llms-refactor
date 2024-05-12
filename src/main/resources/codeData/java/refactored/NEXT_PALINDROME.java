package java_programs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class NextPalindrome {
    public static String findNextPalindrome(int[] digitList) {
        int highMid = digitList.length / 2;
        int lowMid = (digitList.length - 1) / 2;

        while (highMid < digitList.length && lowMid >= 0) {
            if (digitList[highMid] == 9) {
                digitList[highMid] = 0;
                digitList[lowMid] = 0;
                highMid++;
                lowMid--;
            } else {
                digitList[highMid]++;
                if (lowMid != highMid) {
                    digitList[lowMid]++;
                }
                return Arrays.toString(digitList);
            }
        }

        ArrayList<Integer> newPalindrome = new ArrayList<>();
        newPalindrome.add(1);
        newPalindrome.addAll(Collections.nCopies(digitList.length, 0));
        newPalindrome.add(1);

        return newPalindrome.toString();
    }
}