package algorithm;

/**
 * A comparable node object for Dijkstra
 */
public class DijkstraNode implements Comparable<DijkstraNode>{

    private double dist;
    private double hDist;
    private int ID;
    
    public void setDist(double dist) {
        this.dist = dist;
    }

    public double gethDist() {
        return hDist;
    }

    public void sethDist(double hDist) {
        this.hDist = hDist;
    }
    
    public DijkstraNode(int ID, double dist, double hDist)    {
        this.dist = dist;
        this.ID = ID;
        this.hDist = hDist;
    }
    
    public double getDist() {
        return dist;
    }

    public DijkstraNode(int ID, double dist) {
        this.dist = dist;
        this.hDist = dist;
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
        if (this.hDist > s.gethDist()) return 1;
        return -1;
    }
}
