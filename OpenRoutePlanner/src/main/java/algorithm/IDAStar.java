package algorithm;

import data_structure.ArrayList;
import data_structure.BinaryHeap;
import graph.Arc;
import graph.Graph;
import graph.Node;
import data_structure.HashMap;

/**
 * IDA*  shortest path algorithm with direct distance from n to target node 
 * as the heuristic.
 */
public class IDAStar {
    
    final private int nodeCount;
    final private ArrayList<Arc>[] adList;
    final private HashMap<Long, Node> nodes;
    private DijkstraNode resultNode;
    private int goalNode;
    private boolean[] nodeInPath;
    private int[] previousNode;
    
    private ArrayList<DijkstraNode> path;

    final private double latToKm;
    private double lonToKm;
    private double goalLat;
    private double goalLon;
    
    private long timeOut;
    private long startTime;

    public IDAStar(Graph graph)   {
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
        path = new ArrayList<>();
        path.add(new DijkstraNode(start.getID2(), 0, bound, start.getLat(), start.getLon()));
        
        nodeInPath = new boolean[nodeCount];
        nodeInPath[start.getID2()] = true;

        startTime = System.currentTimeMillis();
        
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
        }
    }
    
    private double search(ArrayList<DijkstraNode> path, double g, double bound, int d) {
        DijkstraNode node = path.getLast();
        if (System.currentTimeMillis() - startTime > timeOut)   {
                System.out.println("timed out");
                resultNode = node;
                resultNode.setDist(Double.MAX_VALUE);
                return -1;
            }
        
        double f = g + directDistance(node);
        if (f > bound || d > 10000000) {
            return f;
        }
        if (node.getID() == goalNode)   {
            resultNode = node;
            resultNode.setDist(g);
            return -1;
        }
        double min = Double.MAX_VALUE;
        BinaryHeap heap = successors(node);
        while (!(heap.isEmpty()))   {
            DijkstraNode succ = heap.poll();
            if (!(nodeInPath[succ.getID()]))    {
                path.add(succ);
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
                nodeInPath[path.getLast().getID()] = false;
                path.removeLast();
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
