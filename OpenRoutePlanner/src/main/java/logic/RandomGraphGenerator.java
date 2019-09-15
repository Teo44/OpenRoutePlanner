package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RandomGraphGenerator {

    public RandomGraphGenerator() {
    }
    
    public Graph generateGraph(int nodeCount, int arcsPerNode, int maxLatDiff, int maxLonDiff)  {
        ArrayList<Arc>[] adList = new ArrayList[nodeCount];
        Random r = new Random();
        HashMap<Long, Node> nodes = new HashMap<>();
        HashMap<Integer, Long> realNodeID = new HashMap<>();
        int arcCount = 0;
        
        for (int i = 0; i < nodeCount; i++) {
            double lat = r.nextInt(maxLatDiff) - (maxLatDiff / 2) + r.nextDouble();
            double lon = r.nextInt(maxLonDiff) - (maxLonDiff / 2) + r.nextDouble();
            Node node = new Node(i, i, lat, lon);
            nodes.put((long) i, node);
            realNodeID.put(i, (long) i);
        }
        
        for (int i = 0; i < nodeCount; i++) {
            adList[i] = new ArrayList<>();
        }
        
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < arcsPerNode; j++)   {
                int node2ID = r.nextInt(nodeCount);
                // prevent arcs to the same node
                while (node2ID == i)    {
                    node2ID = r.nextInt(nodeCount);
                }
                Node node2 = nodes.get((long) node2ID);
                Node node1 = nodes.get((long) i);
                double dist = nodeDistance(node1, node2);
                adList[i].add(new Arc(node1.getID2(), node2.getID2(), node1.getID(), node2.getID(), node1, node2, dist));
                adList[node2ID].add(new Arc(node2.getID2(), node1.getID2(), node2.getID(), node1.getID(), node2, node1, dist));
                arcCount += 2;
            }
        }
        
        // makes sure the graph is connected by connecting all consecutive nodes
        for (int i = 0; i < nodeCount - 1; i++) {
            Node node2 = nodes.get((long) i+1);
            Node node1 = nodes.get((long) i);
            double dist = nodeDistance(node1, node2);
            adList[i].add(new Arc(node1.getID2(), node2.getID2(), node1.getID(), node2.getID(), node1, node2, dist));
            adList[i+1].add(new Arc(node2.getID2(), node1.getID2(), node2.getID(), node1.getID(), node2, node1, dist));
            arcCount += 2;
        }
        
        Graph graph = new Graph(nodes, realNodeID, adList, nodeCount, arcCount);
        return graph;
    }
    
    private double nodeDistance(Node nd1, Node nd2) {
        double lat1 = nd1.getLat();
        double lon1 = nd1.getLon();
        double lat2 = nd2.getLat();
        double lon2 = nd2.getLon();
        
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;
            return (dist);
        }
    }
    
}
