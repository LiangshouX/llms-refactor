package java_programs.extra;

public final class BinarySearch {
    private BinarySearch() {
        // This utility class is not publicly instantiable.
    }

    public static int findFirstInSorted(final int[] array, final int target) {
        int lowerBound = 0;
        int upperBound = array.length;
        int result = -1;

        while (lowerBound <= upperBound) {
            final int mid = (lowerBound + upperBound) / 2;

            if (target == array[mid] && (mid == 0 || target != array[mid - 1])) {
                result = mid;
                break;
            } else if (target <= array[mid]) {
                upperBound = mid;
            } else {
                lowerBound = mid + 1;
            }
        }
        return result;
    }
}