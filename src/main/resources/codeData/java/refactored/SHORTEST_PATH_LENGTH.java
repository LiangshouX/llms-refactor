
package java_programs;
import java.util.*;

public class ShortestPathLength {
    public static int shortestPathLength(Map<List<Node>, Integer> lengthByEdge, Node startNode, Node goalNode) {
        int n = lengthByEdge.size();
        Map<Node, Integer> unvisitedNodes = new HashMap<>();
        Set<Node> visitedNodes = new HashSet<>();

        unvisitedNodes.put(startNode, 0);

        while (!unvisitedNodes.isEmpty()) {
            Node node = getNodeWithMinDistance(unvisitedNodes);
            int distance = unvisitedNodes.get(node);
            unvisitedNodes.remove(node);

            if (node.getValue() == goalNode.getValue()) {
                return distance;
            }
            visitedNodes.add(node);

            for (Node nextNode : node.getSuccessors()) {
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

        return Integer.MAX_VALUE;
    }

    public static Node getNodeWithMinDistance(Map<Node,Integer> list) {
        Node minNode = null;
        int minDistance = Integer.MAX_VALUE;
        for (Node node : list.keySet()) {
            int distance = list.get(node);
            if (distance < minDistance) {
                minDistance = distance;
                minNode = node;
            }
        }
        return minNode;
    }
}
