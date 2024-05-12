public class CycleDetectionUtils {
    public static boolean detectCycle(final Node node) {
        Node hare = node;
        Node tortoise = node;

        while (true) {
            if (hare.getSuccessor() == null) {
                return false;
            }

            tortoise = tortoise.getSuccessor();
            hare = hare.getSuccessor().getSuccessor();

            if (hare == tortoise) {
                return true;
            }
        }
    }
}