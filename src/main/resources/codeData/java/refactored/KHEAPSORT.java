package java_programs;

import java.util.*;

/**
 *
 * @author derricklin
 */
public final class KHEAPSORT {
    private KHEAPSORT() {
        throw new IllegalStateException("Utility class");
    }

    public static List<Integer> kheapsort(final List<Integer> arr, final int numberOfElements) {
        final Queue<Integer> heap = new PriorityQueue<>();
        for (final Integer v : arr.subList(0,numberOfElements)) {
            heap.add(v);
        }

        final List<Integer> output = new ArrayList<>();
        for (final Integer x : arr) {
            heap.add(x);
            final Integer popped = heap.poll();
            output.add(popped);
        }

        while (!heap.isEmpty()) {
            output.add(heap.poll());
        }

        return output;
    }
}