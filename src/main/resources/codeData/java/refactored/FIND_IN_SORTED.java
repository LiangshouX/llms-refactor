package java_programs;

/**
 * This class contains methods to find an element in a sorted array.
 * @author derricklin
 */
public final class FindInSorted {

    private FindInSorted() {
        // Utility class
    }

    public static int binarySearch(final int[] finalArray, final int finalElement, final int finalStart, final int finalEnd) {
        if (finalStart == finalEnd) {
            return -1;
        }
        final int middle = finalStart + (finalEnd - finalStart) / 2;

        if (finalElement < finalArray[middle]) {
            return binarySearch(finalArray, finalElement, finalStart, middle);
        } else if (finalElement > finalArray[middle]) {
            return binarySearch(finalArray, finalElement, middle, finalEnd);
        }
        
        return middle;
    }

    public static int findInSortedArray(final int[] finalArray, final int finalElement) {
        return binarySearch(finalArray, finalElement, 0, finalArray.length);
    }
}