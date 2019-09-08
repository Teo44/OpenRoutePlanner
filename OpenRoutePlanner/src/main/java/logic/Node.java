package logic;

public class Node {
    
    double lat;
    double lon;
    long id;
    int id2;

    public int getID2() {
        return id2;
    }

    public void setID2(int id2) {
        this.id2 = id2;
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

    public long getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public Node(long id, int id2, double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
        this.id = id;
        this.id2 = id2;
    }
    
    
    
}
