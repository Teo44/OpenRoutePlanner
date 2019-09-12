package logic;

public class Result {
    
    private double distance[];
    private int[] previousNode;
    private double dist;

    public Result(double[] distance, int[] previousNode, double dist) {
        this.distance = distance;
        this.previousNode = previousNode;
        this.dist = dist;
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
