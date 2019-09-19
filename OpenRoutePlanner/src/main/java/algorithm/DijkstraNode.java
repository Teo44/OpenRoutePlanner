package algorithm;

/**
 * A comparable node object for the path finding algorithms to use
 */
public class DijkstraNode implements Comparable<DijkstraNode>{

    private double dist;
    private double hDist;
    private int ID;
    private double lat;
    private double lon;

    /**
     * @param ID node ID
     * @param dist The real distance to the node from the start 
     * @param hDist  An additional distance variable that is used for comparisons instead of 
     * the dist variable if given, e.g. for A*'s heuristic distances.
     */
    public DijkstraNode(int ID, double dist, double hDist)    {
        this.dist = dist;
        this.ID = ID;
        this.hDist = hDist;
    }
    
    /**
     * @param ID node ID
     * @param dist  The distance to the node from start, used for comparing nodes.
     */
    public DijkstraNode(int ID, double dist) {
        this.dist = dist;
        this.hDist = dist;
        this.ID = ID;
    }
    
    public DijkstraNode(int ID, double dist, double hDist, double lat, double lon)  {
        this.dist = dist;
        this.hDist = hDist;
        this.ID = ID;
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
    
    public void setDist(double dist) {
        this.dist = dist;
    }

    public double gethDist() {
        return hDist;
    }

    public void sethDist(double hDist) {
        this.hDist = hDist;
    }
    
    public double getDist() {
        return dist;
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
