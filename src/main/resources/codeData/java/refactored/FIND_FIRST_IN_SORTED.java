public class BinarySearch {

    public static int findFirstInSortedArray(final int[] array, final int target) {
        int left = 0;
        int right = array.length;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (target == array[mid] && (mid == 0 || target != array[mid-1])) {
                return mid;
            } else if (target <= array[mid]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return -1;
    }

}