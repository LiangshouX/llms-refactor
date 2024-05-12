package java_programs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShortestPaths {

    final static int INF = 99999;

    public static Map<String, Integer> shortestPaths(Node source, List<WeightedEdge> weightByEdge) {
        Map<String, Integer> weightByNode = new HashMap<>();
        for (WeightedEdge edge : weightByEdge) {
            weightByNode.put(edge.getNode1().toString(), INF);
            weightByNode.put(edge.getNode2().toString(), INF);
        }

        weightByNode.put(source.getValue(), 0);
        for (int i = 0; i < weightByEdge.size(); i++) {
            for (WeightedEdge edge : weightByEdge) {
                int updateWeight = Math.min(
                        weightByNode.get(edge.getNode1().toString()) + edge.getWeight(),
                        weightByNode.get(edge.getNode2().toString())
                );
                weightByNode.put(edge.getNode2().toString(), updateWeight);
            }
        }
        return weightByNode;
    }
}