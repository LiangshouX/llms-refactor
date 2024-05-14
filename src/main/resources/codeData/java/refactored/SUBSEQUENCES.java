package java_programs;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating subsequences.
 *
 * @author derricklin
 */
public final class Subsequences {

    private Subsequences() {
        // This constructor is intentionally empty. Nothing to see here.
    }

    /**
     * Generates subsequences.
     *
     * @param start Starting point of the subsequences.
     * @param end Ending point of the subsequences.
     * @param length Length of the subsequences.
     * @return A list of lists representing the subsequences.
     */
    public static List<List<Integer>> generateSubsequences(final int start, final int end, final int length) {
        final List<List<Integer>> result = new ArrayList<>();
        if (length != 0) {
            for (int index = start; index < end + 1 - length; index++) {
                final List<List<Integer>> base = generateBaseList(index, end, length);
                result.addAll(base);
            }
        }
        return result;
    }

    private static List<List<Integer>> generateBaseList(final int index, final int end, final int length) {
        final List<List<Integer>> base = new ArrayList<>();
        for (final List<Integer> rest : generateSubsequences(index + 1, end, length - 1)) {
            rest.add(0, index);
            base.add(rest);
        }
        return base;
    }
}