package java_programs;

public class WeightedEdge implements Comparable<WeightedEdge>{
    public Node node1;
    public Node node2;
    public int weight;

    public WeightedEdge() {
        // To address the code smell, instead of assigning null explicitly, we can leave the Java default values (which is null for objects)
    }

    public WeightedEdge(final Node node1, final Node node2, final int weight) {
        this.node1 = node1;
        this.node2 = node2;
        this.weight = weight;
    }

    @Override
    public int compareTo(final WeightedEdge compareNode) {
        final int compareWeight = compareNode.weight; 

        //ascending order
        return this.weight - compareWeight;
    }
}