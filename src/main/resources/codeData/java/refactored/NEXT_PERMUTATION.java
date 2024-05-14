package java_programs;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public final class NextPermutation {

    private NextPermutation() {
        // To prevent instantiation
        throw new UnsupportedOperationException("Cannot instantiate this utility class.");
    }

    public static List<Integer> getNextPermutation(final List<Integer> perm) {
        final int size = perm.size();
        List<Integer> result = new ArrayList<>();
        for (final int index = size - 2; index != -1; index--) {
            if (perm.get(index) < perm.get(index + 1)) {
                result = swapAndReverse(perm, index);
            }
        }
        return result;
    }

    private static List<Integer> swapAndReverse(final List<Integer> perm, final int index) {
        final int size = perm.size();
        List<Integer> result = perm;
        for (final int reversedIndex = size - 1; reversedIndex != index; reversedIndex--) {
            if (perm.get(reversedIndex) < perm.get(index)) {
                Collections.swap(perm, index, reversedIndex);
                reverseFromIndex(perm, index);
                result = perm;
            }
        }
        return result;
    }

    private static void reverseFromIndex(final List<Integer> list, final int index) {
        int left = index + 1;
        int right = list.size() - 1;
        while (left < right) {
            Collections.swap(list, left, right);
            left++;
            right--;
        }
    }
}