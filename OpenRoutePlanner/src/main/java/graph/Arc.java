package graph;

/**
 * An arc object for the adjacency list, representing the one way distance 
 * between two nodes.
 */
public class Arc {
    
    Double dist; 
    
    Node node1;
    Node node2;

    public Node getNode1() {
        return node1;
    }

    public void setNode1(Node node1) {
        this.node1 = node1;
    }

    public Node getNode2() {
        return node2;
    }

    public void setNode2(Node node2) {
        this.node2 = node2;
    }

    public Double getDist() {
        return dist;
    }

    public void setDist(Double dist) {
        this.dist = dist;
    }
    
    public Arc(Node node1, Node node2, double dist) {
        this.dist = dist;
        this.node1 = node1;
        this.node2 = node2;
    }
    
}
