package logic;

public class Arc {
    
    int nd1;
    int nd2;
    double dist; 

    public int getNd2() {
        return nd2;
    }

    public void setNd2(int nd2) {
        this.nd2 = nd2;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public Arc(int nd1, int nd2, double dist) {
        this.nd1 = nd1;
        this.nd2 = nd2;
        this.dist = dist;
    }
    
}
