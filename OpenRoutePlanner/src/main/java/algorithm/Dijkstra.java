package algorithm;

import data_structure.BinaryHeap;
import data_structure.ArrayList;
import graph.Arc;
import graph.Node;
import graph.Graph;
import data_structure.HashMap;

/**
 * Dijkstra's shortest path algorithm
 */
public class Dijkstra {
    
    private BinaryHeap heap;
    private boolean[] visited;
    final private int nodeCount;
    final private ArrayList<Arc>[] adList;
    final private HashMap<Long, Node> nodes;
    private double distance[];
    private int[] previousNode;

	private long timeOut;
    
    /**
     * @param graph The graph to pathfind in
     */
    public Dijkstra(Graph graph) {
        this.nodeCount = graph.getNodeCount();
        adList = graph.getAdList();
        nodes = graph.getNodes();
		timeOut = Long.MAX_VALUE;
    }

	public void setTimeOut(int timeout)	{
		this.timeOut = timeout * 1000;
	}
    
    /**
     * Calculates the shortest path from node 1 (nd1) to node 2 (nd2)
     * 
     * @param nd1_id Node 1's ID from the OSM XML file
     * @param nd2_id Node 2's ID from the OSM XML file
     * @return A result object containing the results of the search
     * 
     * @see algorithm.Result
     */
    public Result shortestPath(Long nd1_id, Long nd2_id)  {
        if (nodes.get(nd1_id) == null || nodes.get(nd2_id) == null) {
            System.out.println("Node with given ID doesn't exist in graph");
            Result result = new Result(null, null, Double.MAX_VALUE);
            result.setTimedOut(true);
            return result;
        }
        if (nd1_id.equals(nd2_id))   {
            return new Result(distance, previousNode, 0l);
        }
        
        heap = new BinaryHeap();
        visited = new boolean[nodeCount];
        distance = new double[nodeCount];
        for (int i = 0; i < nodeCount; i++) {
            distance[i] = Double.MAX_VALUE;
        }
        previousNode = new int[nodeCount];
        
        Node start = nodes.get(nd1_id);
        Node end = nodes.get(nd2_id);
        DijkstraNode start2 = new DijkstraNode(start.getID2(), 0);
        
        heap.add(start2);

        long startTime = System.currentTimeMillis();
        
        while(!heap.isEmpty())  {
            if (System.currentTimeMillis() - startTime > timeOut)	{
                System.out.println("timed out");
                Result result = new Result(null, null, Double.MAX_VALUE);
                result.setTimedOut(true);
                return result;
            }
            DijkstraNode node = heap.poll();
            if (visited[node.getID()])   {
                continue;
            }
            //make this optional for testing?
            if (node.getID() == end.getID())  {
                break;
            }
            visited[node.getID()] = true;
            for (Arc a : adList[node.getID()]) {
                Double currentDist = distance[a.getNode2().getID2()];
                Double newDist = node.getDist() + a.getDist();
                if (newDist < currentDist)  {
                    previousNode[a.getNode2().getID2()] = a.getNode1().getID2();
                    distance[a.getNode2().getID2()] = newDist;
                    DijkstraNode newNode = new DijkstraNode(a.getNode2().getID2(), newDist);
                    heap.add(newNode);
                }
            }
        }
        
        Result result = new Result(distance, previousNode, distance[end.getID2()]);
        return result;
    }
    
}
