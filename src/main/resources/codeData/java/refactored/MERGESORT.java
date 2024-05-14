package java_programs;

import java.util.ArrayList;
import java.util.List;

public final class MERGESORT {

    private MERGESORT() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static List<Integer> merge(final List<Integer> left, final List<Integer> right) {
        final List<Integer> result = new ArrayList<>();
        int indexLeft = 0;
        int indexRight = 0;

        while (indexLeft < left.size() && indexRight < right.size()) {
            if (left.get(indexLeft) <= right.get(indexRight)) {
                result.add(left.get(indexLeft));
                indexLeft++;
            } else {
                result.add(right.get(indexRight));
                indexRight++;
            }
        }
        result.addAll(left.subList(indexLeft,left.size()).isEmpty() ? right.subList(indexRight, right.size()) : left.subList(indexLeft, left.size()));
        return result;
    }

    public static List<Integer> mergesort(final List<Integer> arr) {
        if (arr.isEmpty()) {
            return arr;
        }
        
        final int middle = arr.size() / 2;
        
        List<Integer> left = new ArrayList<>(arr.subList(0,middle));
        left = mergesort(left);
        
        List<Integer> right = new ArrayList<>(arr.subList(middle, arr.size()));
        right = mergesort(right);

        return merge(left, right);
    }
}