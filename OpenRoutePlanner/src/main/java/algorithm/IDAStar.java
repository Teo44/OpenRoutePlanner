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

    public IDAStar(Graph graph)   {
        this.nodeCount = graph.getNodeCount();
        adList = graph.getAdList();
        nodes = graph.getNodes();
        latToKm = 110.574;
    }
    
    public Result shortestPath(Long nd1_id, Long nd2_id)    {
        if (nd1_id.equals(nd2_id))   {
            return new Result(null, previousNode, 0l);
        }
        
        Node start = nodes.get(nd1_id);
        Node end = nodes.get(nd2_id);
//        System.out.println("ID of goal node: " + end.getID());
//        System.out.println("ID2 of goal node: " + end.getID2());
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
        
        while(true) {
            double t = search(path, 0, bound);
            if (t == -1)    {
                return new Result(null, null, resultNode.getDist());
            }
            if (t == Double.MAX_VALUE)  {
                return new Result(null, null, Double.MAX_VALUE);
            }
            bound = t;
        }
    }
    
    private double search(Stack<DijkstraNode> path, double g, double bound) {
//        System.out.println("called search with g of " + g + " and bound of: " + bound);
        DijkstraNode node = path.lastElement();
        double f = g + 1.1*directDistance(node);
        if (f > bound) {
//            System.out.println("f > bound");
//            System.out.println("f: " + f);
//            System.out.println("bound: " + bound);
//            System.out.println("");
            return f;
        }
        if (node.getID() == goalNode)   {
//            System.out.println("found goal node: " + node.getID());
//            System.out.println("distance to node " + node.getDist());
            resultNode = node;
            resultNode.setDist(g);
            return -1;
        }
        double min = Double.MAX_VALUE;
        BinaryHeap heap = successors(node);
        while (!(heap.isEmpty()))   {
//            System.out.println("polling heap");
            DijkstraNode succ = heap.poll();
//            System.out.println("is node " + succ.getID() + " in the path: " + nodeInPath[succ.getID()]);
            if (!(nodeInPath[succ.getID()]))    {
//                System.out.println("adding node to path: " + succ.getID());
                path.add(succ);
                nodeInPath[succ.getID()] = true;
//                System.out.println("path size: " + path.size());
                if (g == 0) {
//                    System.out.println("starting from the beginning");
                }
//                System.out.println("searching node " + succ.getID());
//                System.out.println("distance from current: " + succ.getDist());
//                System.out.println("current distance: " + g);
//                System.out.println("total dist of current route: " + (g + succ.getDist()));
                double t = search(path, g + succ.getDist(), bound);
                if (t == -1)    {
                    return -1;
                }
                if (t < min)  {
                    min = t;
                }
                nodeInPath[path.pop().getID()] = false;
            }
        }
        
        return min;
        
    }
    
    private BinaryHeap successors(DijkstraNode node)  {
        BinaryHeap heap = new BinaryHeap();
//        System.out.println("adlist size: " + adList[node.getID()].size());
        for (Arc a : adList[node.getID()])  {
//            System.out.println("arc betweeen: " + a.getNode1().getID() + " and " + a.getNode2().getID());
//            System.out.println("");
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
