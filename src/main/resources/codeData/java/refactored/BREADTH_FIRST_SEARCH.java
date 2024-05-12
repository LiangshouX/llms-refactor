package java_programs;
import java.util.*;

public class BreadthFirstSearch {

    private static final Set<Node> nodesVisited = new HashSet<>();

    public static boolean breadthFirstSearch(final Node startNode, final Node goalNode) {
        Deque<Node> queue = new ArrayDeque<>();
        queue.addLast(startNode);

        nodesVisited.add(startNode);

        while (!queue.isEmpty()) {
            Node node = queue.removeFirst();

            if (node == goalNode) {
                return true;
            } else {
                for (Node successorNode : node.getSuccessors()) {
                    if (!nodesVisited.contains(successorNode)) {
                        queue.addFirst(successorNode);
                        nodesVisited.add(successorNode);
                    }
                }
            }
        }
        return false;
    }

}