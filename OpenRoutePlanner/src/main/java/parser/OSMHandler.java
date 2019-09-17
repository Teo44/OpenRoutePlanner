package parser;

import java.util.ArrayList;
import java.util.HashMap;
import graph.Arc;
import graph.Graph;
import graph.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class OSMHandler extends DefaultHandler  {
    
    ArrayList<Arc>[] adList;
    HashMap<Long, Node> nodes;
    HashMap<Integer, Long> realNodeID;
    int nodeCount;
    int arcCount;
    Node lastNode;
    
    ArrayList<Arc> arcs;
    boolean wayApproved;
    boolean noTagFiltering;
    ArrayList<String> approvedTags;
    
    ArrayList<Integer> nodeIDList;
    
    public OSMHandler(ArrayList<String> approvedTags) {
        nodes = new HashMap<>();
        realNodeID = new HashMap<>();
        nodeCount = 0;
        nodeIDList = new ArrayList<>();
        if (approvedTags.isEmpty()) {
            noTagFiltering = true;
        }
        this.approvedTags = approvedTags;
    }
    
    /**
     * Returns the the parsed OSM graph as a Graph-object, which contains
     * the adjacency list, hash map of nodes and the node count.
     * 
     * @return Graph-object
     */
    public Graph getGraph()    {
        Graph graph = new Graph(nodes, realNodeID, adList, nodeCount, arcCount);
        return graph;
    }
    
    @Override
    public void startElement( String uri, String localName, String qName, Attributes attributes) throws SAXException {
        
        if (qName.equals("node"))   {
            String id = attributes.getValue("id");
            String lat = attributes.getValue("lat");
            String lon = attributes.getValue("lon");
            //debug print
            //System.out.println("node id: " + id);
            // check that the node is valid: has an id and a location
            if (id != null && lat != null && lon != null)   {
                Node node = new Node(Long.parseLong(id), nodeCount, Double.parseDouble(lat), Double.parseDouble(lon));
                nodeCount += 1;
                nodes.put(node.getID(), node);
            }
        } else if (qName.equals("way")) {
            // create the adjacency list, all nodes should be before ways in the 
            // OSM XML files, so this works
            if (adList == null)  {
                //debug
                System.out.println("Created adjacency list with " + nodeCount + " nodes");
                adList = new ArrayList[nodeCount];
                for (int i = 0; i < nodeCount; i++) {
                    adList[i] = new ArrayList<>();
                }
            }
            arcs = new ArrayList<>();
            //String id = attributes.getValue("id");
            //debug print
            //System.out.println("way id: " + id);
        } else if (qName.equals("nd"))    {
            
            Node newNode = nodes.get(Long.parseLong(attributes.getValue("ref")));
            if (lastNode != null)   {
                double dist = nodeDistance(lastNode, newNode);
                long nd1 = lastNode.getID();
                long nd2 = newNode.getID();
                int nd1_id2 = lastNode.getID2();
                int nd2_id2 = newNode.getID2();
                realNodeID.put(nd1_id2, nd1);
                realNodeID.put(nd2_id2, nd2);
                arcs.add(new Arc(lastNode, newNode, dist));
                arcs.add(new Arc(newNode, lastNode, dist));
                //debug print
                //System.out.println("Distance from node " + nd1 + " to node " + nd2 + ": " + dist + "km");
                lastNode = newNode;
            } else  {
                lastNode = newNode;
            }
        } else if (qName.equals("tag")) { // check if the way has an approved tag and should be added to graph
            String k = attributes.getValue("k");
            if (!noTagFiltering) {
                for (String s : approvedTags)   {
                    if (k.equals(s))  {
                        wayApproved = true;
                    }
                }
            }
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // set lastNode to null, so the current way isn't connected to the next one
        if (noTagFiltering) {
            wayApproved = true;
        }
        if (qName.equals("way"))    {
            lastNode = null;
            if (wayApproved)    {
                arcCount += arcs.size();
                for (Arc a : arcs)  {
                    adList[a.getNode1().getID2()].add(a);
                }
                wayApproved = false;
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
