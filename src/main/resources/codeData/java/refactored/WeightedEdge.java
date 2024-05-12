package java_programs;
import java.util.*;

public class WeightedEdge implements Comparable<WeightedEdge>{
    public Node node1;
    public Node node2;
    public int weight;

    public WeightedEdge () {
        this(null, null, 0);
    }
    public WeightedEdge (final Node node1, final Node node2, final int weight) {
        this.node1 = node1;
        this.node2 = node2;
        this.weight = weight;
    }
    @Override
    public int compareTo(final WeightedEdge compareNode) {
        int compareWeight = compareNode.weight;

        return Integer.compare(this.weight, compareWeight);
    }
}