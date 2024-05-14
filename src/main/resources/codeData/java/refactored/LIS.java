package java_programs;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *
 * @author derricklin
 */
public final class LongestIncreasingSubsequence {
    private LongestIncreasingSubsequence() {
        // Prevent instantiation
    }

    public static int longestIncreasingSubsequence(final int... arr) {
        final Map<Integer,Integer> sequenceEnds = new ConcurrentHashMap<>(100);
        int longest = 0;

        List<Integer> prefixLengths = new ArrayList<>(100);
        int index = 0;
        for (final int value : arr) {

            prefixLengths.clear();
            for (int j=1; j < longest+1; j++) {
                if (arr[sequenceEnds.get(j)] < value) {
                    prefixLengths.add(j);
                }
            }

            final int length = prefixLengths.isEmpty() ? 0 : Collections.max(prefixLengths);

            if (length == longest) {
                sequenceEnds.put(length+1, index);
                longest = length + 1;
            } else if (value < arr[sequenceEnds.get(length+1)]) {
                sequenceEnds.put(length+1, index);
                longest = length + 1;
            }

            index++;
        }
        return longest;
    }
}