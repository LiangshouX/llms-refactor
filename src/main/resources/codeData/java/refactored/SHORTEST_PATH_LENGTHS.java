package java_programs;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class ShortestPathLengths {

    private static final int INF = 99_999;

    private ShortestPathLengths() {
    }

    public static Map<List<Integer>, Integer> calculateShortestPathLengths(final int numNodes,
                                                                           final Map<List<Integer>, Integer> lengthByEdge) {
        final Map<List<Integer>, Integer> lengthByPath = new ConcurrentHashMap<>();
        List<Integer> edge;

        for (int i = 0; i < numNodes; i++) {
            for (int j =0; j < numNodes; j++) {
                edge = Arrays.asList(i,j);
                if (i == j) {
                    lengthByPath.put(edge, 0);
                } else if (lengthByEdge.containsKey(edge)) {
                    lengthByPath.put(edge, lengthByEdge.get(edge));
                } else {
                    lengthByPath.put(edge, INF);
                }
            }
        }

        List<Integer> firstPath, secondPath, thirdPath;
        int updateLength;

        for (int k = 0; k < numNodes; k++) {
            for (int i = 0; i < numNodes; i++) {
                for (int j = 0; j < numNodes; j++) {
                    firstPath = Arrays.asList(i,j);
                    secondPath = Arrays.asList(i,k);
                    thirdPath = Arrays.asList(j,k);
                    updateLength = Math.min(lengthByPath.get(firstPath),
                            sumLengths(lengthByPath.get(secondPath),
                                    lengthByPath.get(thirdPath)));
                    lengthByPath.put(firstPath, updateLength);
                }
            }
        }
        return lengthByPath;
    }

    private static int sumLengths(final int firstLength, final int secondLength) {
        int sum;
        if(firstLength == INF || secondLength == INF) {
            sum = INF;
        } else {
            sum = firstLength + secondLength;
        }
        return sum;
    }

}