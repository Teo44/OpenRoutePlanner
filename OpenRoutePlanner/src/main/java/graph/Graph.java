package graph;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A representation of a parsed graph.
 */
public class Graph {
    
    private ArrayList<Arc>[] adList;
    private HashMap<Long, Node> nodes;
    private HashMap<Integer, Long> realNodeID;
    private int nodeCount;
    private int arcCount;
    
    public Graph(HashMap<Long, Node> nodes, HashMap<Integer, Long> realNodeID, ArrayList<Arc>[] adList, int nodeCount, int arcCount)  {
        this.adList = adList;
        this.nodes = nodes;
        this.realNodeID = realNodeID;
        this.nodeCount = nodeCount;
        this.arcCount = arcCount;
    }

    public HashMap<Integer, Long> getRealNodeID() {
        return realNodeID;
    }

    public void setRealNodeID(HashMap<Integer, Long> realNodeID) {
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

}
