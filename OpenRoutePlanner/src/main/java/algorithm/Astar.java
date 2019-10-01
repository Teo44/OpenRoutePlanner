package algorithm;

import data_structure.BinaryHeap;
import graph.Arc;
import graph.Node;
import graph.Graph;
import data_structure.ArrayList;
import data_structure.HashMap;

/**
 * A*  shortest path algorithm with direct distance from n to target node 
 * as the heuristic.
 */
public class Astar {

    private BinaryHeap heap;
    private boolean[] visited;
    final private int nodeCount;
    final private ArrayList<Arc>[] adList;
    final private HashMap<Long, Node> nodes;
    private double distance[];
    private int[] previousNode;
    
    final private double latToKm;
    private double lonToKm;
    private double goalLat;
    private double goalLon;

    private long timeOut;
    
    /**
     * @param graph The graph to pathfind in
     */
    public Astar(Graph graph)   {
        this.nodeCount = graph.getNodeCount();
        adList = graph.getAdList();
        nodes = graph.getNodes();
        latToKm = 110.574;
        timeOut = Long.MAX_VALUE;
        
        /** 
        * The conversion of longitudes to kilometers is set to use whichever of the graphs bound 
        * longitudes results in the smaller conversion, to keep the heuristic admissible in all cases.
        */
        lonToKm = 111.320 * Math.cos(Math.min(graph.getMaxLat() * Math.PI / 180, graph.getMinLat() * Math.PI / 180));
    }

	public void setTimeOut(int timeout)	{
		this.timeOut = timeout * 1000;
	}
    
    /**
     * Returns the shortest route between nodes nd1 and nd2
     * @param nd1_id Node 1's ID from the OSM XML file
     * @param nd2_id Node 2s ID from the OSM XML file
     * @return A result object containing the results of the search
     * 
     * @see algorithm.Result
     */
    public Result shortestPath(Long nd1_id, Long nd2_id)    {
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
        
        goalLat = end.getLat();
        goalLon = end.getLon();
        
        heap.add(start2);

		long startTime = System.currentTimeMillis();
        
        while(!heap.isEmpty())  {
			if (System.currentTimeMillis() - startTime > timeOut)	{
				System.out.println("timed out");
				return new Result(null, null, Double.MAX_VALUE);
			}
            DijkstraNode node = heap.poll();
            if (visited[node.getID()])   {
                continue;
            }
            // Make this optional for testing the differences?
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
                    DijkstraNode newNode = new DijkstraNode(a.getNode2().getID2(), newDist, newDist + directDistance(a.getNode2()));
                    heap.add(newNode);
                }
            }
        }
        
        Result result = new Result(distance, previousNode, distance[end.getID2()]); 
        
        return result;
    }
    
    /**
     * The heuristic function. Calculates the direct distance from the given node 
     * to the target node of the search, using predefined coordinate to km conversions.
     * @param node The current node.
     * @return The direct distance to the target.
     */
    private double directDistance(Node node)   {
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
