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
//            double lat = r.nextInt(maxLatDiff) - (maxLatDiff / 2) + r.nextDouble();
//            double lon = r.nextInt(maxLonDiff) - (maxLonDiff / 2) + r.nextDouble();
            double lat = r.nextInt(maxLatDiff) + r.nextDouble();
            double lon = r.nextInt(maxLonDiff) + r.nextDouble();
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
    
    /**
     * Generates a graph consisting of x times y evenly spread nodes, with 
     * n randomised roads spanning though the graph.
     * @param x X dimension of the graph
     * @param y y dimension of the graph
     * @param roadCount The amount of ways going through the graph
     * @param maxLatDiff The difference in latitude between the first and last 
     * node in the x dimension
     * @param maxLonDiff The difference in longitude between the first and last
     * node in the y dimension
     * @return The generated graph as a graph-object
     */
    public Graph generateGraph2(int x, int y, int roadCount, int maxLatDiff, int maxLonDiff)   {
        
        int nodeCount = x * y;
        Random r = new Random();
        ArrayList<Arc>[] adList = new ArrayList[nodeCount];
        HashMap<Long, Node> nodes = new HashMap<>();
        HashMap<Integer, Long> realNodeID = new HashMap<>();
        int arcCount = 0;
        for (int i = 0; i < nodeCount; i++) {
            adList[i] = new ArrayList<>();
        }
        
        // generating nodes as a grid
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                double lat = 1.0 * i / x * maxLatDiff;
                double lon = 1.0 * j / y * maxLonDiff;
                int id = i*x+j;
                Node node = new Node(id, id, lat, lon);
                //debug
//                System.out.println("generated node " + id + " at " + lat + " lat and " + lon + " lon");
                nodes.put((long) id, node);
                realNodeID.put(id, (long) id);
            }
        }
        //debug
        System.out.println("generating ways");
        // generate half of the "roads" leading through the graph one way...
        for (int n = 0; n < roadCount / 2; n++) {
            // the node where the road will beging
            int curX = r.nextInt(x);
            int curY = 0;
            int nextX;
            while (curY < y - 1) {
                int nextY = curY + 1;
                while (true)    {
                    int d = r.nextInt(3);
                    if (d == 0) {
                        nextX = curX;
                    } else if (d == 1)  {
                        nextX = curX - 1;
                    } else  {
                        nextX = curX + 1;
                    }
//                    System.out.println("next x: " + nextX);
                    if (nextX >= 0 && nextX < x)    {
                        break;
                    }
                }
                Node nd1 = nodes.get(curX * x + curY);
                Node nd2 = nodes.get(nextX * x + nextY);
                double dist = nodeDistance(nd1, nd2);
                adList[nd1.getID2()].add(new Arc(nd1, nd2, dist));
                adList[nd2.getID2()].add(new Arc(nd2, nd1, dist));
                //debug
//                System.out.println("generated arc between nodes at " +
//                        curX+" "+curY+" and " +nextX + " " + nextY);
                arcCount += 2;
                curY += 1;
                curX = nextX;
            }
        }
        // and the rest of the ways leading the other way
        for (int n = roadCount / 2; n < roadCount; n++) {
            int curX = 0;
            int curY = r.nextInt(y);
            int nextY;
            while (curX < x - 1) {
                int nextX = curX + 1;
                while (true)    {
                    int d = r.nextInt(3);
                    if (d == 0) {
                        nextY = curY;
                    } else if (d == 1)  {
                        nextY = curY - 1;
                    } else  {
                        nextY = curY + 1;
                    }
                    if (nextY >= 0 && nextY < y)    {
                        break;
                    }
                }
                Node nd1 = nodes.get(curX * x + curY);
                Node nd2 = nodes.get(nextX * x + nextY);
                double dist = nodeDistance(nd1, nd2);
                adList[nd1.getID2()].add(new Arc(nd1, nd2, dist));
                adList[nd2.getID2()].add(new Arc(nd2, nd1, dist));
                //debug
//                System.out.println("generated arc between nodes at " +
//                        curX+" "+curY+" and " +nextX + " " + nextY);
                arcCount += 2;
                curX += 1;
                curY = nextY;
            }
        }
        for (int i = 0; i < x - 1; i++) {
            Node nd1 = nodes.get(i);
            Node nd2 = nodes.get(i+1);
            double dist = nodeDistance(nd1, nd2);
            adList[nd1.getID2()].add(new Arc(nd1, nd2, dist));
            adList[nd2.getID2()].add(new Arc(nd2, nd1, dist));
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
 