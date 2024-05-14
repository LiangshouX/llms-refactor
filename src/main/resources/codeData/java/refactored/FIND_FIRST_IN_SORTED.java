package java_programs;

import java.util.*;

/**
 * Class to find the first occurrence in a sorted array
 *
 * @author derricklin
 */
public final class FindFirstInSorted {

    private FindFirstInSorted() {
        // Utility class
    }

    public static int findFirstInSorted(final int[] array, final int searchElement) {
        int lowIndex = 0;
        int highIndex = array.length;
        int result = -1;

        while (lowIndex <= highIndex) {
            final int middleIndex = (lowIndex + highIndex) / 2;

            if (searchElement == array[middleIndex] && (middleIndex == 0 || searchElement != array[middleIndex - 1])) {
                result = middleIndex;
                break;
            } else if (searchElement <= array[middleIndex]) {
                highIndex = middleIndex;
            } else {
                lowIndex = middleIndex + 1;
            }
        }

        return result;
    }

}