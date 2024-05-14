package java_programs;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This utility class provides methods to calculate the length of Longest Common Subsequence (LCS)
 * @author derricklin
 */
public final class LcsLength {

    private LcsLength() {
        // This private constructor prevents the utility class from being instantiated.
    }

    public static Integer calculateLcsLength(final String stringOne, final String stringTwo) {
        // Using a map of maps to store intermediate results
        Map<Integer, Map<Integer,Integer>> dynamicProgrammingMap = new ConcurrentHashMap<>();

        // Initialize all the internal maps to 0
        Map<Integer,Integer> initialize;
        for (int i = 0; i < stringOne.length(); i++) {
            initialize = dynamicProgrammingMap.getOrDefault(i, new ConcurrentHashMap<>());
            for (int j = 0; j < stringTwo.length(); j++) {
                initialize.putIfAbsent(j, 0);
            }
            dynamicProgrammingMap.put(i, initialize);
        }

        // Calculate the LCS length
        final Map<Integer, Integer> internalMap;
        for (int i = 0; i < stringOne.length(); i++) {
            for (int j = 0; j < stringTwo.length(); j++) {
                if (stringOne.charAt(i) == stringTwo.charAt(j)) {
                    internalMap = dynamicProgrammingMap.get(i);
                    if (dynamicProgrammingMap.containsKey(i - 1)) {
                        final int insertValue = dynamicProgrammingMap.get(i - 1).get(j) + 1;
                        internalMap.put(j, insertValue);
                    } else {
                        internalMap.put(j, 1);
                    }
                    dynamicProgrammingMap.put(i, internalMap);
                }
            }
        }

        // Get the maximum LCS length
        int maxValue = 0;
        if (!dynamicProgrammingMap.isEmpty()) {
            final List<Integer> retList = new ArrayList<>();
            for (Map<Integer, Integer> value : dynamicProgrammingMap.values()) {
                if (!value.isEmpty()) {
                    retList.add(Collections.max(value.values()));
                }
            }
            if (!retList.isEmpty()) {
                maxValue = Collections.max(retList);
            }
        }
        return maxValue;
    }
}