package java_programs;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Minimum spanning tree.
 */
public final class MinimumSpanningTree {
    private MinimumSpanningTree() {
        throw new UnsupportedOperationException("Cannot instantiate this class");
    }

    public static Set<WeightedEdge> getMinimumSpanningTree(final List<WeightedEdge> weightedEdges) {
        Map<Node, Set<Node>> groupByNode = new ConcurrentHashMap<>();
        final Set<WeightedEdge> minSpanningTree = new HashSet<>();

        Collections.sort(weightedEdges);

        for (final WeightedEdge edge : weightedEdges) {
            final Node vertexU = edge.node1;
            final Node vertexV = edge.node2;
            Set<Node> setU = groupByNode.get(vertexU);
            Set<Node> setV = groupByNode.get(vertexV);

            if (setU == null) {
                setU = new HashSet<>(Arrays.asList(vertexU));
                groupByNode.put(vertexU, setU);
            }
            if (setV == null) {
                setV = new HashSet<>(Arrays.asList(vertexV));
                groupByNode.put(vertexV, setV);
            }

            if (!setU.equals(setV)) {
                minSpanningTree.add(edge);
                groupByNode = update(groupByNode, vertexU, vertexV);
                for (final Node node : groupByNode.get(vertexV)) {
                    groupByNode = update(groupByNode, node, vertexU);
                }
            }
        }
        return minSpanningTree;
    }

    public static Map<Node, Set<Node>> update(final Map<Node, Set<Node>> groupByNode, final Node vertexU, final Node vertexV) {
        final Set<Node> vertexUSpan = groupByNode.get(vertexU);
        vertexUSpan.addAll(groupByNode.get(vertexV));

        return groupByNode;
    }
}