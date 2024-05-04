
package java_programs;
import java.util.*;

public class TOPOLOGICAL_ORDERING {
    public static ArrayList<Node> topological_ordering (List<Node> directedGraph) {
        ArrayList<Node> orderedNodes = new ArrayList<Node>();
        Queue<Node> queue = new LinkedList<Node>();

        for (Node node : directedGraph) {
            if (node.getPredecessors().isEmpty()) {
                queue.add(node);
            }
        }

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            orderedNodes.add(node);

            for (Node nextNode : node.getSuccessors()) {
                nextNode.getPredecessors().remove(node);

                if (nextNode.getPredecessors().isEmpty()) {
                    queue.add(nextNode);
                }
            }
        }

        return orderedNodes;
    }
}
