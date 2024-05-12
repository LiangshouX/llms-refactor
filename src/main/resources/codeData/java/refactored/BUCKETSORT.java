import java.util.List;

public class BucketSort {
    public static List<Integer> bucketsort(final List<Integer> arr, final int k) {
        final List<Integer> counts = new ArrayList<>(Collections.nCopies(k, 0));
        for (final Integer x : arr) {
            counts.set(x, counts.get(x) + 1);
        }

        final List<Integer> sortedArr = new ArrayList<>();
        int i = 0;
        for (Integer count : counts) {
            sortedArr.addAll(Collections.nCopies(count, i));
            i++;
        }

        return sortedArr;
    }
}