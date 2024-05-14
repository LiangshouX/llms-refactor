package java_programs;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Angela Chen
 */

public final class ShortestPathLength {
    private ShortestPathLength() {
        // private constructor to prevent instantiation
    }
    
    public static int calculateShortestPathLength(Map<List<Node>, Integer> lengthByEdge, final Node startNode, final Node goalNode) {
        final Map<Node, Integer> unvisitedNodes = new ConcurrentHashMap<>();
        final Set<Node> visitedNodes = new HashSet<>();
        int result = Integer.MAX_VALUE;

        unvisitedNodes.put(startNode, 0);

        while (!unvisitedNodes.isEmpty()) {
            final Node node = getNodeWithMinDistance(unvisitedNodes);
            final int distance = unvisitedNodes.get(node);
            unvisitedNodes.remove(node);

            if (node.getValue() == goalNode.getValue()) {
                result = distance;
                break;
            }
            visitedNodes.add(node);

            for (final Node nextNode : node.getSuccessors()) {
                if (visitedNodes.contains(nextNode)) {
                    continue;
                }

                if (unvisitedNodes.get(nextNode) == null) {
                    unvisitedNodes.put(nextNode, Integer.MAX_VALUE);
                }

                unvisitedNodes.put(nextNode, Math.min(unvisitedNodes.get(nextNode),
                        unvisitedNodes.get(nextNode) + lengthByEdge.get(Arrays.asList(node, nextNode))));
            }
        }

        return result;
    }

    public static Node getNodeWithMinDistance(final Map<Node,Integer> nodeList) {
        Node minNode = null;
        int minDistance = Integer.MAX_VALUE;
        for (final Map.Entry<Node, Integer> entry : nodeList.entrySet()) {
            final int distance = entry.getValue();
            if (distance < minDistance) {
                minDistance = distance;
                minNode = entry.getKey();
            }
        }
        return minNode;
    }
}