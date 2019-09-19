package algorithm;

import data_structure.ArrayList;
import data_structure.BinaryHeap;
import graph.Arc;
import graph.Graph;
import graph.Node;
import java.util.HashMap;
import java.util.Stack;

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
        double f = g + directDistance(node);
        if (f > bound) {
//            System.out.println("f > bound");
//            System.out.println("f: " + f);
//            System.out.println("bound: " + bound);
//            System.out.println("");
            return f;
        }
        if (node.getID() == goalNode)   {
//            System.out.println("found goal node: " + node.getID());
            resultNode = node;
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
