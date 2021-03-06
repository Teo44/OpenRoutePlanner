package parser;

import datastructure.ArrayList;
import datastructure.HashMap;
import graph.Arc;
import graph.Graph;
import graph.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Extended saxhandler to parse OSM XML files to a graph.
 */
public class OSMHandler extends DefaultHandler  {
    
    private ArrayList<Arc>[] adList;
    final private HashMap<Long, Node> nodes;
    final private HashMap<Long, Long> acceptedWays;
    private long[] realNodeID;
    private int nodeCount;
    private int acceptedNodeCount;
    private int arcCount;
    private Node lastNode;
    final private int[] neighbours;
    private long currentWayID;
    private double maxLat;
    private double minLat;
    private double maxLon;
    private double minLon;
    
    private ArrayList<Arc> arcs;
    private boolean noTagFiltering;
    
    final private ArrayList<Integer> nodeIDList;
    
    /**
     * @param approvedTags Arraylist of tags for filtering the ways. 
     * E.g. "highway" only parses ways with the k="highway" attributes in one of their 
     * "tag" elements.
     * @param acceptedWays HashMap containing the ID's of ways to be accepted.
     * @param neighbours An array containing the amount of neighbours each node has.
     */
    public OSMHandler(ArrayList<String> approvedTags, int[] neighbours, HashMap<Long, Long> acceptedWays) {
        nodes = new HashMap<>();
        nodeCount = 0;
        acceptedNodeCount = 0;
        nodeIDList = new ArrayList<>();
        if (approvedTags.isEmpty()) {
            noTagFiltering = true;
        }
        this.neighbours = neighbours;
        this.acceptedWays = acceptedWays;
    }
    
    /**
     * Returns the the parsed OSM graph as a Graph-object, which contains
     * the adjacency list, hash map of nodes and the node count.
     * 
     * @return Graph-object
     */
    public Graph getGraph()    {
        Graph graph = new Graph(nodes, realNodeID, adList, acceptedNodeCount, arcCount, maxLat, minLat, maxLon, minLon);
        return graph;
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        
        if (qName.equals("bounds")) {
            maxLat = Double.parseDouble(attributes.getValue("maxlat"));
            minLat = Double.parseDouble(attributes.getValue("minlat"));
            maxLon = Double.parseDouble(attributes.getValue("maxlon"));
            minLon = Double.parseDouble(attributes.getValue("minlon"));
        } else if (qName.equals("node"))   {
            String id = attributes.getValue("id");
            String lat = attributes.getValue("lat");
            String lon = attributes.getValue("lon");
            // check that the node is valid: has an id and a location
            if (id != null && lat != null && lon != null)   {
                // discard nodes that wont have any neighbours
                if (neighbours[nodeCount] != 0) {
                    Node node = new Node(Long.parseLong(id), acceptedNodeCount, Double.parseDouble(lat), Double.parseDouble(lon));
                    acceptedNodeCount += 1;
                    nodes.put(node.getID(), node);
                }
                nodeCount += 1;
            }
        } else if (qName.equals("way")) {
            // create the adjacency list, all nodes should be before ways in the 
            // OSM XML files, so this works
            currentWayID = Long.parseLong(attributes.getValue("id"));
            if (adList == null)  {
                adList = new ArrayList[acceptedNodeCount];
                for (int i = 0; i < acceptedNodeCount; i++) {
                    adList[i] = new ArrayList<>();
                }
                realNodeID = new long[acceptedNodeCount];
            }
            arcs = new ArrayList<>();
        } else if (qName.equals("nd") && (acceptedWays.contains(currentWayID) || noTagFiltering))    {
            Node newNode = nodes.get(Long.parseLong(attributes.getValue("ref")));
            if (lastNode != null)   {
                double dist = nodeDistance(lastNode, newNode);
                long nd1 = lastNode.getID();
                long nd2 = newNode.getID();
                int node1ID2 = lastNode.getID2();
                int node2ID2 = newNode.getID2();
                realNodeID[node1ID2] = nd1;
                realNodeID[node2ID2] = nd2;
                arcs.add(new Arc(lastNode, newNode, dist));
                arcs.add(new Arc(newNode, lastNode, dist));
                lastNode = newNode;
            } else  {
                lastNode = newNode;
            }
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("way"))    {
            // set lastNode to null, so the current way isn't connected to the next one
            lastNode = null;
            arcCount += arcs.size();
            for (Arc a : arcs)  {
                adList[a.getNode1().getID2()].add(a);
            }
        }
    }
    
    private double nodeDistance(Node nd1, Node nd2) {
        double lat1 = nd1.getLat();
        double lon1 = nd1.getLon();
        double lat2 = nd2.getLat();
        double lon2 = nd2.getLon();
        
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;
            return (dist);
        }
    }
    
    public ArrayList getNodeList() {
        return nodeIDList;
    }
}
