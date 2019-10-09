package algorithm;

/**
 * An object for returning the results of pathfinding algorithms.
 */
public class Result {
    
    private double distance[];
    private int[] previousNode;
    private double dist;
    private boolean timedOut;

    /**
     * @param distance The shortest distances from the start node to all the traversed nodes.
     * @param previousNode The previous node of each node on the route, to find the path
     * @param dist The shortest distance to the target node.
     */
    public Result(double[] distance, int[] previousNode, double dist) {
        this.distance = distance;
        this.previousNode = previousNode;
        this.dist = dist;
        this.timedOut = false;
    }

    public boolean timedOut() {
        return timedOut;
    }

    public void setTimedOut(boolean timedOut) {
        this.timedOut = timedOut;
    }
    
    public double[] getDistance() {
        return distance;
    }

    public void setDistance(double[] distance) {
        this.distance = distance;
    }

    public int[] getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(int[] previousNode) {
        this.previousNode = previousNode;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }
    
    

}
