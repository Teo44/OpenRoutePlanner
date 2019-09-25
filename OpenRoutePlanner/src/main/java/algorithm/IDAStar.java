package algorithm;

import data_structure.ArrayList;
import data_structure.BinaryHeap;
import graph.Arc;
import graph.Graph;
import graph.Node;
import data_structure.HashMap;
import java.util.Stack;

/**
 * IDA*  shortest path algorithm with direct distance from n to target node 
 * as the heuristic.
 */
public class IDAStar {
    
    private int nodeCount;
    private ArrayList<Arc>[] adList;
    private HashMap<Long, Node> nodes;
    private DijkstraNode resultNode;
    private int goalNode;
    private boolean[] nodeInPath;
    private int[] previousNode;
    
    private Stack<DijkstraNode> path;

    private double latToKm;
    private double lonToKm;
    private double goalLat;
    private double goalLon;
    private long timeOut;

    public IDAStar(Graph graph)   {
        this.nodeCount = graph.getNodeCount();
        adList = graph.getAdList();
        nodes = graph.getNodes();
        latToKm = 110.574;
        timeOut = Long.MAX_VALUE;
    }
    
    public void setTimeOut(int timeout)    {
        this.timeOut = timeout * 1000;
    }
    
    public Result shortestPath(Long nd1_id, Long nd2_id)    {
        if (nd1_id.equals(nd2_id))   {
            return new Result(null, previousNode, 0l);
        }
        
        Node start = nodes.get(nd1_id);
        Node end = nodes.get(nd2_id);
        goalNode = end.getID2();
        goalLat = end.getLat();
        goalLon = end.getLon();
        
        double bound = directDistance(start);
        path = new Stack();
        path.add(new DijkstraNode(start.getID2(), 0, bound, start.getLat(), start.getLon()));
        
        nodeInPath = new boolean[nodeCount];
        nodeInPath[start.getID2()] = true;
        
        /** 
        *Approximation for the longitude to km conversion to use for the heuristic
        * Longitude to km ratio differs a lot from the equator to the poles, so we take
        * the average between the start and end points latitude, and use that for the conversion 
        * to speed up the heuristics calculation.
        */
        lonToKm = 111.320*Math.cos( (start.getLat() + end.getLat()) / 2 * Math.PI / 180);
        long startTime = System.currentTimeMillis();
        
        //debug
//        int depth = 0;
        
        while(true) {
            if (System.currentTimeMillis() - startTime > timeOut)   {
                System.out.println("timed out");
                return new Result(null, null, Double.MAX_VALUE);
            }
            double t = search(path, 0, bound, 0);
            if (t == -1)    {
                return new Result(null, null, resultNode.getDist());
            }
            if (t == Double.MAX_VALUE)  {
                return new Result(null, null, Double.MAX_VALUE);
            }
            bound = t;
            //debug
//            System.out.print("Depth of IDA search: " + depth++);
//            System.out.println(" Bound: " + bound);
        }
    }
    
    private double search(Stack<DijkstraNode> path, double g, double bound, int d) {
        DijkstraNode node = path.peek();

        
        double f = g + directDistance(node);
//double f = g + 0.001;
        if (f > bound || d > 10000000) {
            return f;
        }
        if (node.getID() == goalNode)   {
            //debug
//            System.out.println("depth of final search: " + d);
            resultNode = node;
            resultNode.setDist(g);
            return -1;
        }
        double min = Double.MAX_VALUE;
        BinaryHeap heap = successors(node);
        while (!(heap.isEmpty()))   {
            DijkstraNode succ = heap.poll();
            if (!(nodeInPath[succ.getID()]))    {
                path.push(succ);
                nodeInPath[succ.getID()] = true;
                if (g == 0) {
                }
                double t = search(path, g + succ.getDist(), bound, d+1);
                if (t == -1)    {
                    return -1;
                }
                if (t < min)  {
                    min = t;
                }
                nodeInPath[path.peek().getID()] = false;
                path.pop();
            }
        }
        
        return min;
        
    }
    
    private BinaryHeap successors(DijkstraNode node)  {
        BinaryHeap heap = new BinaryHeap();
        for (Arc a : adList[node.getID()])  {
            Node n = a.getNode2();
            heap.add(new DijkstraNode(n.getID2(), a.getDist(), directDistance(n), n.getLat(), n.getLon()));
        }
        return heap;
    }
    
    private double directDistance(DijkstraNode node)   {
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
