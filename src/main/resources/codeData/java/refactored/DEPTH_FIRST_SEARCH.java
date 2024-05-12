
public class DepthFirstSearch {
    public static boolean depthFirstSearch(final Node startNode, final Node goalNode) {
        Set<Node> nodesVisited = new HashSet<>();
        class Search {
            boolean search(Node node) {
                if (nodesVisited.contains(node)) {
                    return false;
                } else if (node == goalNode) {
                    return true;
                } else {
                    for (Node successorNode : node.getSuccessors()) {
                        if (search(successorNode)) { return true; }
                    }
                }
                return false;
            }
        };

        Search searchOperation = new Search();
        return searchOperation.search(startNode);
    }
}