package java_programs;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author derricklin
 */
public final class BucketSort {

    private BucketSort() {
        // utility class should not have a public or default constructor
    }

    public static List<Integer> sort(final List<Integer> inputArray, final int bucketSize) {
        final List<Integer> counts = new ArrayList<>(Collections.nCopies(bucketSize, 0));
        for (final Integer x : inputArray) {
            counts.set(x, counts.get(x) + 1);
        }

        final List<Integer> sortedArray = new ArrayList<>();
        int index = 0;
        for (final Integer count : counts) {
            sortedArray.addAll(Collections.nCopies(count, index));
            index++;
        }

        return sortedArray;
    }
}