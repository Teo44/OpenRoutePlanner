package logic;

/**
 * An arc object for the adjacency list, representing the one way distance 
 * between two nodes.
 */
public class Arc {
    
    int nd1;
    int nd2;
    long nd1_realID;
    long nd2_realID;
    Double dist; 

    public long getNd1_realID() {
        return nd1_realID;
    }

    public void setNd1_realID(long nd1_id2) {
        this.nd1_realID = nd1_id2;
    }

    public long getNd2_realID() {
        return nd2_realID;
    }

    public void setNd2_realID(long nd2_id2) {
        this.nd2_realID = nd2_id2;
    }
    
    public int getNd2() {
        return nd2;
    }

    public int getNd1() {
        return nd1;
    }

    public void setNd1(int nd1) {
        this.nd1 = nd1;
    }

    public void setNd2(int nd2) {
        this.nd2 = nd2;
    }

    public Double getDist() {
        return dist;
    }

    public void setDist(Double dist) {
        this.dist = dist;
    }

    public Arc(int nd1, int nd2, long nd1_id2, long nd2_id2, double dist) {
        this.nd1 = nd1;
        this.nd2 = nd2;
        this.dist = dist;
        this.nd1_realID = nd1_id2;
        this.nd2_realID = nd2_id2;
    }
    
}
