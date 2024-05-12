public class ShortestPathLengthsUtil {

    private static final int INF = 99999;

    public static Map<List<Integer>, Integer> calculateShortestPathLengths(int numNodes, Map<List<Integer>, Integer> lengthByEdge) {
        Map<List<Integer>, Integer> lengthByPath = new HashMap<>();

        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                List<Integer> edge = new ArrayList<>(Arrays.asList(i, j));
                if (i == j) {
                    lengthByPath.put(edge, 0);
                } else if (lengthByEdge.containsKey(edge)) {
                    lengthByPath.put(edge, lengthByEdge.get(edge));
                } else {
                    lengthByPath.put(edge, INF);
                }
            }
        }

        for (int k = 0; k < numNodes; k++) {
            for (int i = 0; i < numNodes; i++) {
                for (int j = 0; j < numNodes; j++) {
                    int updateLength = Math.min(lengthByPath.get(Arrays.asList(i, j)),
                                                sumLengths(lengthByPath.get(Arrays.asList(i, k)),
                                                           lengthByPath.get(Arrays.asList(k, j))));
                    lengthByPath.put(Arrays.asList(i, j), updateLength);
                }
            }
        }

        return lengthByPath;
    }

    private static int sumLengths(int a, int b) {
        if (a == INF || b == INF) {
            return INF;
        }
        return a + b;
    }

}