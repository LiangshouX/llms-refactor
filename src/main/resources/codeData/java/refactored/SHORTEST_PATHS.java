package java_programs;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Angela Chen
 */
public final class ShortestPaths {

    // Define Infinite as a large enough value. This value will be used
    // for vertices not connected to each other
    static final int INFINITY = 99_999;

    private ShortestPaths() {
        // private constructor to prevent instantiation
    }

    public static Map<String, Integer> findShortestPaths(final String source, final Map<List<String>, Integer> weightByEdge) {
        final ConcurrentHashMap<String, Integer> weightByNode = new ConcurrentHashMap<>();
        for (final List<String> edge : weightByEdge.keySet()) {
            weightByNode.put(edge.get(1), INFINITY);
            weightByNode.put(edge.get(0), INFINITY);
        }

        weightByNode.put(source, 0);
        for (int i = 0; i < weightByNode.size(); i++) {
            for (final List<String> edge : weightByEdge.keySet()) {
                final int updateWeight = Math.min(
                        weightByNode.get(edge.get(0))
                                + weightByEdge.get(edge),
                        weightByNode.get(edge.get(1)));
                weightByEdge.put(edge, updateWeight);
            }
        }
        return weightByNode;
    }

    public static Map<String, Integer> findShortestPaths(final Node source, final List<WeightedEdge> weightByEdge) {
        final ConcurrentHashMap<String, Integer> weightByNode = new ConcurrentHashMap<>();
        for (final WeightedEdge edge : weightByEdge) {
            weightByNode.put(edge.getNodeValue1(), INFINITY);
            weightByNode.put(edge.getNodeValue2(), INFINITY);
        }

        weightByNode.put(source.getValue(), 0);
        for (int i = 0; i < weightByNode.size(); i++) {
            for (final WeightedEdge edge : weightByEdge) {
                final int updatedWeight = Math.min(
                        weightByNode.get(edge.getNodeValue1())
                                + edge.getEdgeWeight(),
                        weightByNode.get(edge.getNodeValue2()));
                edge.setEdgeWeight(updatedWeight);
            }
        }
        return weightByNode;
    }
}
In the `WeightedEdge` class, replace the direct access of `node1`, `node2` and `weight` with getter methods like `getNodeValue1()`, `getNodeValue2()` and `getEdgeWeight()`. Also, replace the direct setting of `weight` with a setter method like `setEdgeWeight(int weight)`.