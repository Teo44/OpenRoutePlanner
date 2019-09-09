package logic;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Dijkstra {
    
    private PriorityQueue<DijkstraNode> heap;
    private boolean[] visited;
    private int nodeCount;
    private ArrayList<Arc>[] adList;
    private HashMap<Long, Node> nodes;
    private double distance[];
    
    public Dijkstra(Graph graph) {
        
        heap = new PriorityQueue<>();
        this.nodeCount = graph.getNodeCount();
        visited = new boolean[nodeCount];
        distance = new double[nodeCount];
        adList = graph.getAdList();
        nodes = graph.getNodes();
        
        for (int i = 0; i < nodeCount; i++) {
            distance[i] = Double.MAX_VALUE;
        }
        
    }
    
    public void shortestPath(Long nd1_id, Long nd2_id)  {
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
                //debug
//                System.out.println("current dist: " + currentDist);
//                System.out.println("new dist: " + newDist);
                if (newDist < currentDist)  {
                    //debug
//                    System.out.println("new distance was shorter");
                    distance[a.getNd2()] = newDist;
                    DijkstraNode newNode = new DijkstraNode(a.getNd2(), newDist);
                    heap.add(newNode);
                }
            }
        }
        
        //debug
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        System.out.println("Shortest distance to node: " + df.format(distance[end.getID2()]) + "km");        
    }
    
}
