
package java_programs;
import java.util.*;

public class DepthFirstSearch {
    public static boolean depthFirstSearch(Node startNode, Node goalNode) {
        Set<Node> nodesVisited = new HashSet<>();
        
        class Search {
            boolean search(Node node) {
                if (nodesVisited.contains(node)) {
                    return false;
                } else if (node == goalNode) {
                    return true;
                } else {
                    for (Node successorNode : node.getSuccessors()) {
                        if (search(successorNode)) { 
                            return true; 
                        }
                    }
                }
                return false;
            }
        };

        Search s = new Search();
        return s.search(startNode);
    }
}
