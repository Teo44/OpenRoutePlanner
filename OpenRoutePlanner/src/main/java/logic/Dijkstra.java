package logic;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Dijkstra's shortest path algorithm
 */
public class Dijkstra {
    
    private PriorityQueue<DijkstraNode> heap;
    private boolean[] visited;
    private int nodeCount;
    private ArrayList<Arc>[] adList;
    private HashMap<Long, Node> nodes;
    private double distance[];
    
    public Dijkstra(Graph graph) {
        this.nodeCount = graph.getNodeCount();
        adList = graph.getAdList();
        nodes = graph.getNodes();
    }
    
    /**
     * Calculates the shortest path from node 1 (nd1) to node 2 (nd2)
     * 
     * @param nd1_id Node 1's ID from the OSM XML file
     * @param nd2_id Node 2's ID from the OSM XML file
     */
    public Double shortestPath(Long nd1_id, Long nd2_id)  {
        if (nd1_id.equals(nd2_id))   {
            return 0d;
        }
        
        heap = new PriorityQueue<>();
        visited = new boolean[nodeCount];
        distance = new double[nodeCount];
        for (int i = 0; i < nodeCount; i++) {
            distance[i] = Double.MAX_VALUE;
        }
        
        Node start = nodes.get(nd1_id);
        Node end = nodes.get(nd2_id);
        DijkstraNode start2 = new DijkstraNode(start.getID2(), 0);
        
        heap.add(start2);
        
        while(!heap.isEmpty())  {
            DijkstraNode node = heap.poll();
            if (visited[node.getID()])   {
                continue;
            }
            visited[node.getID()] = true;
            for (Arc a : adList[node.getID()]) {
                Double currentDist = distance[a.getNd2()];
                Double newDist = node.getDist() + a.getDist();
                if (newDist < currentDist)  {
                    distance[a.getNd2()] = newDist;
                    DijkstraNode newNode = new DijkstraNode(a.getNd2(), newDist);
                    heap.add(newNode);
                }
            }
        }
        
        return distance[end.getID2()];
    }
    
}
