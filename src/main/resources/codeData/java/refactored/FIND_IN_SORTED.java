public class BinarySearch {

    public static int binarySearch(final int[] array, final int target, final int start, final int end) {
        if (start == end) {
            return -1;
        }
        final int mid = start + (end - start) / 2;
        if (target < array[mid]) {
            return binarySearch(array, target, start, mid);
        } else if (target > array[mid]) {
            return binarySearch(array, target, mid + 1, end);
        } else {
            return mid;
        }
    }

    public static int findInSorted(final int[] array, final int target) {
        return binarySearch(array, target, 0, array.length - 1);
    }
}