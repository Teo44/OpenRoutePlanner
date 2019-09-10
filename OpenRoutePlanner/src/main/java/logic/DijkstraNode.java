package logic;

/**
 * A comparable node object for Dijkstra
 */
public class DijkstraNode implements Comparable<DijkstraNode>{

    private double dist;
    private int ID;
    
    public void setDist(double dist) {
        this.dist = dist;
    }
    
    public double getDist() {
        return dist;
    }

    public DijkstraNode(int ID, double dist) {
        this.dist = dist;
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    
    @Override
    public int compareTo(DijkstraNode s)   {
        if (this.dist > s.getDist()) return 1;
        return -1;
    }
}
