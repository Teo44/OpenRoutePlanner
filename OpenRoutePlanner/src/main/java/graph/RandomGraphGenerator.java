package graph;

import data_structure.ArrayList;
import data_structure.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class for generating random graphs, with options to choose the amount of nodes, amount of 
 * arcs generated per node, the maximum difference in latitude and longitude and whether to make sure
 * the graph is connected.
 */
public class RandomGraphGenerator {
    
    /**
     * @param nodeCount Amount of nodes to generate
     * @param arcsPerNode Amount of arcs to generate per node
     * @param maxLatDiff The maximum difference in latitude between the nodes
     * @param maxLonDiff The maximum difference in longitude between the nodes
     * @param connected If true, all the nodes will be connected in random order to make sure 
     * the graph is connected.
     * @return A graph-object representation of the generated graph
     */
    public Graph generateGraph(int nodeCount, int arcsPerNode, int maxLatDiff, int maxLonDiff, boolean connected)  {
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
                adList[i].add(new Arc(node1, node2, dist));
                adList[node2ID].add(new Arc(node2, node1, dist));
                arcCount += 2;
            }
        }
        
        // Make sure the graph is connected by connecting all nodes in a random order
        if (connected)  {
            int[] randomNodes = new int[nodeCount];
            for (int i = 0; i < nodeCount; i++) {
                randomNodes[i] = i;
            }
            Random rnd = ThreadLocalRandom.current();
            for (int i = nodeCount - 1; i > 0; i--)
            {
              int index = rnd.nextInt(i + 1);
              int a = randomNodes[index];
              randomNodes[index] = randomNodes[i];
              randomNodes[i] = a;
            }
            for (int i = 0; i < nodeCount - 1; i++) {
                Node node2 = nodes.get((long) randomNodes[i]);
                Node node1 = nodes.get((long) randomNodes[i+1]);
                double dist = nodeDistance(node1, node2);
                adList[i + 1].add(new Arc(node1, node2, dist));
                adList[i].add(new Arc(node2, node1, dist));
                arcCount += 2;
            }
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
