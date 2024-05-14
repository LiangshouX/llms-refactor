package java_programs;

import java.util.List;

public class GraphNode {

    private final String value;
    private List<GraphNode> successors;
    private List<GraphNode> predecessors;
    private GraphNode successor;

    public GraphNode() {
        this.successors = null;
        this.predecessors = null;
        this.value = "";
    }

    public GraphNode(final String value) {
        this.value = value;
        this.successors = null;
        this.predecessors = null;
    }

    public GraphNode(final String value, final GraphNode successor) {
        this.value = value;
        this.successor = successor;
        this.successors = null;
        this.predecessors = null;
    }

    public GraphNode(final String value, final List<GraphNode> successors) {
        this.value = value;
        this.successors = successors;
        this.predecessors = null;
    }

    public GraphNode(final String value, final List<GraphNode> predecessors, final List<GraphNode> successors) {
        this.value = value;
        this.predecessors = predecessors;
        this.successors = successors;
    }

    public String getValue() {
        return value;
    }

    public void setSuccessor(final GraphNode successor) {
        this.successor = successor;
    }

    public void setSuccessors(final List<GraphNode> successors) {
        this.successors = successors;
    }

    public void setPredecessors(final List<GraphNode> predecessors) {
        this.predecessors = predecessors;
    }

    public GraphNode getSuccessor() {
        return successor;
    }

    public List<GraphNode> getSuccessors() {
        return successors;
    }

    public List<GraphNode> getPredecessors() {
        return predecessors;
    }
}