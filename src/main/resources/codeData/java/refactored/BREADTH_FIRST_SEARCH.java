package java_programs;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

public class BreadthFirstSearch {

    public static Set<Node> nodesVisited = new HashSet<>();

    public static boolean breadthFirstSearch(Node startNode, Node goalNode) {
        ArrayDeque<Node> queue = new ArrayDeque<>();
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