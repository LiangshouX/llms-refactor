package java_programs;

import java.util.*;

/**
 * Utility class to reverse a linked list.
 *
 * @author derricklin
 */
public final class ReverseLinkedList {
    private ReverseLinkedList() {
        // This utility class is not publicly instantiable.
    }

    public static Node reverseLinkedList(Node inputNode) {
        Node prevNode = null;
        Node nextNode;
        Node currentNode = inputNode;
        while (currentNode != null) {
            nextNode = currentNode.getSuccessor();
            currentNode.setSuccessor(prevNode);
            prevNode = currentNode;
            currentNode = nextNode;
        }
        return prevNode;
    }
}