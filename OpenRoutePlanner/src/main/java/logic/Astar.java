package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Astar {

    private PriorityQueue<DijkstraNode> heap;
    private boolean[] visited;
    private int nodeCount;
    private ArrayList<Arc>[] adList;
    private HashMap<Long, Node> nodes;
    private HashMap<Integer, Long> realNodeID;
    private double distance[];
    private int[] previousNode;
    
    private double latToKm;
    private double lonToKm;
    private double goalLat;
    private double goalLon;
    
    public Astar(Graph graph)   {
        this.nodeCount = graph.getNodeCount();
        adList = graph.getAdList();
        nodes = graph.getNodes();
        realNodeID = graph.getRealNodeID();
        latToKm = 110.574;
    }
    
    public Result shortestPath(Long nd1_id, Long nd2_id)    {
        if (nd1_id.equals(nd2_id))   {
            return new Result(distance, previousNode, 0l);
        }
        
        heap = new PriorityQueue<>();
        visited = new boolean[nodeCount];
        distance = new double[nodeCount];
        for (int i = 0; i < nodeCount; i++) {
            distance[i] = Double.MAX_VALUE;
        }
        previousNode = new int[nodeCount];
        
        Node start = nodes.get(nd1_id);
        Node end = nodes.get(nd2_id);
        
        // approximation for the longitude to km conversion to use for the heuristic
        lonToKm = 111.320*Math.cos( (start.getLat() + end.getLat()) / 2 * Math.PI / 180);
        
        DijkstraNode start2 = new DijkstraNode(start.getID2(), 0);
        
        goalLat = end.getLat();
        goalLon = end.getLon();
        
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
                    previousNode[a.getNd2()] = a.getNd1();
                    distance[a.getNd2()] = newDist;
                    DijkstraNode newNode = new DijkstraNode(a.getNd2(), newDist, newDist + directDistance(a.getNode2()));
                    heap.add(newNode);
                }
            }
        }
        
        Result result = new Result(distance, previousNode, distance[end.getID2()]); 
        
        return result;
    }
    
    private double directDistance(Node node)   {
        
        //Node nd1 = nodes.get(nd1_id);
        
        
        double lat1 = node.getLat();
        double lon1 = node.getLon();
        
        if ((lat1 == goalLat) && (lon1 == goalLon)) {
            return 0;
        }
        double latKm = (lat1 - goalLat) * latToKm;
        double lonKm = (lon1 - goalLon) * lonToKm;
        
        double dist = Math.sqrt(latKm * latKm + lonKm * lonKm);
        
        return dist;
    }
}
