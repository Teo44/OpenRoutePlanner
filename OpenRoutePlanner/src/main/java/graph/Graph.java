package graph;

import data_structure.ArrayList;
import data_structure.HashMap;

/**
 * A representation of a graph using an adjacency list. 
 */
public class Graph {
    
    private ArrayList<Arc>[] adList;
    private HashMap<Long, Node> nodes;
    private long[] realNodeID;
    private int nodeCount;
    private int arcCount;
    private double maxLat;
    private double minLat;
    private double maxLon;
    private double minLon;
    
    /**
     * @param nodes Hashmap with ID of node as key, node-object as value
     * @param realNodeID Hashmap with the running ID of nodes as key, real ID as value
     * @param adList Adjacency list for the nodes, index is the running ID of the node
     * @param nodeCount Amount of nodes in the graph
     * @param arcCount  Amount of arcs in the graph
     */
    public Graph(HashMap<Long, Node> nodes, long[] realNodeID, ArrayList<Arc>[] adList, int nodeCount, int arcCount, double maxLat, double minLat, double maxLon, double minLon)  {
        this.adList = adList;
        this.nodes = nodes;
        this.realNodeID = realNodeID;
        this.nodeCount = nodeCount;
        this.arcCount = arcCount;
        this.maxLat = maxLat;
        this.minLat = minLat;
        this.maxLon = maxLon;
        this.minLon = minLon;
    }

    public long[] getRealNodeID() {
        return realNodeID;
    }

    public void setRealNodeID(long[] realNodeID) {
        this.realNodeID = realNodeID;
    }

    public int getArcCount() {
        return arcCount;
    }

    public void setArcCount(int arcCount) {
        this.arcCount = arcCount;
    }

    public ArrayList<Arc>[] getAdList() {
        return adList;
    }

    public void setAdList(ArrayList<Arc>[] adList) {
        this.adList = adList;
    }

    public HashMap<Long, Node> getNodes() {
        return nodes;
    }

    public void setNodes(HashMap<Long, Node> nodes) {
        this.nodes = nodes;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }

    public double getMaxLat() {
        return maxLat;
    }

    public void setMaxLat(double maxLat) {
        this.maxLat = maxLat;
    }

    public double getMinLat() {
        return minLat;
    }

    public void setMinLat(double minLat) {
        this.minLat = minLat;
    }

    public double getMaxLon() {
        return maxLon;
    }

    public void setMaxLon(double maxLon) {
        this.maxLon = maxLon;
    }

    public double getMinLon() {
        return minLon;
    }

    public void setMinLon(double minLon) {
        this.minLon = minLon;
    }
}
