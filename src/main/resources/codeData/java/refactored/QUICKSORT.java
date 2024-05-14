package java_programs;

import java.util.ArrayList;
import java.util.List;

public final class QUICKSORT {
    private QUICKSORT() {
    }

    public static List<Integer> quicksort(final List<Integer> arr) {
        if (arr.isEmpty()) {
            return new ArrayList<>();
        }

        final Integer pivot = arr.get(0);
        final List<Integer> lesser = new ArrayList<>();
        final List<Integer> greater = new ArrayList<>();

        for (final Integer x : arr.subList(1, arr.size())) {
            if (x < pivot) {
                lesser.add(x);
            } else if (x > pivot) {
                greater.add(x);
            }
        }

        final List<Integer> middle = new ArrayList<>();
        middle.add(pivot);

        lesser.addAll(quicksort(lesser));
        lesser.addAll(middle);
        lesser.addAll(quicksort(greater));

        return lesser;
    }
}