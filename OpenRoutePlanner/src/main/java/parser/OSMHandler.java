package parser;

import data_structure.ArrayList;
import data_structure.HashMap;
import graph.Arc;
import graph.Graph;
import graph.Node;
import java.util.HashSet;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Extended saxhandler to parse OSM XML files to a graph.
 */
public class OSMHandler extends DefaultHandler  {
    
    private ArrayList<Arc>[] adList;
    private HashMap<Long, Node> nodes;
    //private HashMap<Integer, Long> realNodeID;
    private int nodeCount;
    private int acceptedNodeCount;
    private int arcCount;
    private Node lastNode;
    private int[] neighbours;
    private HashSet<Long> acceptedWays;
    private long currentWayID;
    
    private ArrayList<Arc> arcs;
    private boolean noTagFiltering;
    
    private ArrayList<Integer> nodeIDList;
    
    /**
     * @param approvedTags Arraylist of tags for filtering the ways. 
     * E.g. "highway" only parses ways with the k="highway" attributes in one of their 
     * "tag" elements.
     */
    public OSMHandler(ArrayList<String> approvedTags, int[] neighbours, HashSet<Long> acceptedWays) {
        nodes = new HashMap<>();
        //realNodeID = new HashMap<>();
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
        Graph graph = new Graph(nodes, null, adList, acceptedNodeCount, arcCount);
        return graph;
    }
    
    @Override
    public void startElement( String uri, String localName, String qName, Attributes attributes) throws SAXException {
        
        if (qName.equals("node"))   {
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
                //debug
//                System.out.println("Created adjacency list with " + acceptedNodeCount + " nodes");
                adList = new ArrayList[acceptedNodeCount];
                for (int i = 0; i < acceptedNodeCount; i++) {
                    adList[i] = new ArrayList<>();
                }
            }
            arcs = new ArrayList<>();
        } else if (qName.equals("nd") && (acceptedWays.contains(currentWayID) || noTagFiltering))    {
            Node newNode = nodes.get(Long.parseLong(attributes.getValue("ref")));
            if (lastNode != null)   {
                double dist = nodeDistance(lastNode, newNode);
//                long nd1 = lastNode.getID();
//                long nd2 = newNode.getID();
//                int nd1_id2 = lastNode.getID2();
//                int nd2_id2 = newNode.getID2();
                //realNodeID.put(nd1_id2, nd1);
                //realNodeID.put(nd2_id2, nd2);
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
    
    public ArrayList getNodeList() {
        return nodeIDList;
    }
    
}
