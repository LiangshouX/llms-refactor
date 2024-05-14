package java_programs;

import java.util.List;
import java.util.ArrayList;

public final class KthElementFinder {

    private KthElementFinder() {
    }

    public static Integer kth(final List<Integer> numbers, final int position) {
        final int pivot = numbers.get(0);
        final List<Integer> below = new ArrayList<>();
        final List<Integer> above = new ArrayList<>();
        Integer result = null;

        for (final Integer number : numbers) {
            if (number < pivot) {
                below.add(number);
            } else if (number > pivot) {
                above.add(number);
            }
        }

        final int numLess = below.size();
        final int numLessOrEqual = numbers.size() - above.size();

        if (position < numLess) {
            result = kth(below, position);
        } else if (position >= numLessOrEqual) {
            result = kth(above, position - numLessOrEqual);
        } else {
            result = pivot;
        }
        return result;
    }
}