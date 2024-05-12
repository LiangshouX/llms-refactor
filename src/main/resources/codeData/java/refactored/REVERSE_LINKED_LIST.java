public class LinkedListUtils {
    public static Node reverseLinkedList(Node inputNode) {
        Node reversedNode = null;
        Node nextNode;
        while (inputNode != null) {
            nextNode = inputNode.getSuccessor();
            inputNode.setSuccessor(reversedNode);
            reversedNode = inputNode;
            inputNode = nextNode;
        }
        return reversedNode;
    }
}