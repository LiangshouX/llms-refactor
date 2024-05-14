package java_programs;

/**
 * Utility class for detecting cycles in Nodes.
 */
public final class CycleDetector {

    private CycleDetector() {
        // Prevents instantiation
    }

    /**
     * Detects if there's a cycle in the given node.
     *
     * @param node the starting node
     * @return true if there's a cycle, false otherwise
     */
    public static boolean detectCycle(final Node node) {
        Node hare = node;
        Node tortoise = node;

        while (hare.getSuccessor() != null) {
            tortoise = tortoise.getSuccessor();
            hare = hare.getSuccessor().getSuccessor();

            if (hare == tortoise) {
                break;
            }
        }

        return hare == tortoise;
    }
}