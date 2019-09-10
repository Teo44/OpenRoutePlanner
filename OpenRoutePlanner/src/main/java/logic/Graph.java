package logic;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A representation of a parsed graph.
 */
public class Graph {
    
    private ArrayList<Arc>[] adList;
    private HashMap<Long, Node> nodes;
    private int nodeCount;
    
    public Graph(HashMap<Long, Node> nodes, ArrayList<Arc>[] adList, int nodeCount)  {
        this.adList = adList;
        this.nodes = nodes;
        this.nodeCount = nodeCount;
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
