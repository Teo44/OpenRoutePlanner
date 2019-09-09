package logic;

public class Arc {
    
    int nd1;
    int nd2;
    Double dist; 

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

    public Arc(int nd1, int nd2, double dist) {
        this.nd1 = nd1;
        this.nd2 = nd2;
        this.dist = dist;
    }
    
}
