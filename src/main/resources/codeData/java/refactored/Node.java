package java_programs;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Node {

    private String value;
    private List<Node> successors;
    private List<Node> predecessors;
    private Node successor;

    public Node() {
        this.successor = null;
        this.successors = new ArrayList<>();
        this.predecessors = new ArrayList<>();
        this.value = null;
    }

    public Node(String value) {
        this.value = value;
        this.successor = null;
        this.successors = new ArrayList<>();
        this.predecessors = new ArrayList<>();
    }

    public Node(String value, Node successor) {
        this.value = value;
        this.successor = successor;
    }

    public Node(String value, List<Node> successors) {
        this.value = value;
        this.successors = successors;
    }

    public Node(String value, List<Node> predecessors, List<Node> successors) {
        this.value = value;
        this.predecessors = predecessors;
        this.successors = successors;
    }

    public String getValue() {
        return value;
    }

    public void setSuccessor(Node successor) {
        this.successor = successor;
    }

    public void setSuccessors(List<Node> successors) {
        this.successors = successors;
    }

    public void setPredecessors(List<Node> predecessors) {
        this.predecessors = predecessors;
    }

    public Node getSuccessor() {
        return successor;
    }

    public List<Node> getSuccessors() {
        return successors;
    }

    public List<Node> getPredecessors() {
        return predecessors;
    }
}