package java_programs;

import java.util.Set;
import java.util.HashSet;

public final class DepthFirstSearch {
    private DepthFirstSearch() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static boolean depthFirstSearch(final Node startNode, final Node goalNode) {
        final Set<Node> nodesVisited = new HashSet<>();

        class Search {
            /* default */ boolean search(final Node node) {
                boolean result = false;
                if (!nodesVisited.contains(node)) {
                    if (node == goalNode) {
                        result = true;
                    } else {
                        nodesVisited.add(node);
                        for (final Node successorNode : node.getSuccessors()) {
                            if (search(successorNode)) {
                                return true;
                            }
                        }
                    }
                }
                return result;
            }
        }

        final Search searchInstance = new Search();
        return searchInstance.search(startNode);
    }

}