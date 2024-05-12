public class ShortestPathLengthUtil {
    public static int shortestPathLength(final Map<List<Node>, Integer> lengthByEdge, final Node startNode, final Node goalNode) {
        int n = lengthByEdge.size();
        
        final Map<Node, Integer> unvisitedNodes = new HashMap<>();
        final Set<Node> visitedNodes = new HashSet<>();

        unvisitedNodes.put(startNode, 0);

        while (!unvisitedNodes.isEmpty()) {
            final Node node = getNodeWithMinDistance(unvisitedNodes);
            final int distance = unvisitedNodes.get(node);
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
                        distance + lengthByEdge.get(Arrays.asList(node, nextNode))));
            }
        }

        return Integer.MAX_VALUE;
    }

    public static Node getNodeWithMinDistance(final Map<Node,Integer> list) {
        Node minNode = null;
        int minDistance = Integer.MAX_VALUE;
        for (Node node : list.keySet()) {
            final int distance = list.get(node);
            if (distance < minDistance) {
                minDistance = distance;
                minNode = node;
            }
        }
        return minNode;
    }
}