package java_programs;

import java.util.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author derricklin
 */
public final class BreadthFirstSearch {

    public static final Set<Node> NODES_VISITED = new HashSet<>();

    private BreadthFirstSearch() {
        // Utility class
    }

    public static boolean breadthFirstSearch(final Node startNode, final Node goalNode) {
        final Deque<Node> queue = new ArrayDeque<>();
        queue.addLast(startNode);

        NODES_VISITED.add(startNode);

        while (true) {
            final Node node = queue.removeFirst();

            if (node == goalNode) {
                return true;
            } else {
                for (final Node successorNode : node.getSuccessors()) {
                    if (!NODES_VISITED.contains(successorNode)) {
                        queue.addFirst(successorNode);
                        NODES_VISITED.add(successorNode);
                    }
                }
            }
        }
    }
}