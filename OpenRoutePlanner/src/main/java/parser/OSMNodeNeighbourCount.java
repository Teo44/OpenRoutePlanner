package parser;

import data_structure.ArrayList;
import data_structure.HashMap;
import graph.Arc;
import graph.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Extended saxhandler to count the neighbours of each node. Used for 
 * optimising the results of the actual parser.
 */
public class OSMNodeNeighbourCount extends DefaultHandler{

    private int nodeCount;
    final private HashMap<Long, Node> nodes;
    final private HashMap<Long, Long> acceptedWays;
    private int[] neighbours;
    private Node lastNode;
    private ArrayList<Arc> arcs;
    boolean wayApproved;
    boolean noTagFiltering;
    private long currentWayID;
    ArrayList<String> approvedTags;
    
    public OSMNodeNeighbourCount(ArrayList<String> approvedTags) {
        nodeCount = 0;
        nodes = new HashMap<>();
        if (approvedTags.isEmpty()) {
            noTagFiltering = true;
        }
        this.approvedTags = approvedTags;
        acceptedWays = new HashMap<>();
    }
    
    @Override
    public void startElement( String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("node"))   {
            String id = attributes.getValue("id");
            String lat = attributes.getValue("lat");
            String lon = attributes.getValue("lon");
            // check that the node is valid: has an id and a location
            if (id != null && lat != null && lon != null)   {
                Node node = new Node(Long.parseLong(id), nodeCount, Double.parseDouble(lat), Double.parseDouble(lon));
                nodeCount += 1;
                nodes.put(node.getID(), node);
            }
        } else if (qName.equals("way")) {
                currentWayID = Long.parseLong(attributes.getValue("id"));
                if (neighbours == null) {
                    neighbours = new int[nodeCount];
                }
                arcs = new ArrayList<>();
                
        } else if (qName.equals("nd"))  {
                Node newNode = nodes.get(Long.parseLong(attributes.getValue("ref")));
                if (lastNode != null)   {
                    arcs.add(new Arc(lastNode, newNode, 0));
                    arcs.add(new Arc(newNode, lastNode, 0));
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
        
        if (noTagFiltering) {
            wayApproved = true;
        }
        if (qName.equals("way"))    {
            // set lastNode to null, so the current way isn't connected to the next one
            lastNode = null;
            if (wayApproved)    {
                acceptedWays.put(currentWayID, 0l);
                for (Arc a : arcs)  {
                    neighbours[a.getNode1().getID2()] += 1;
                }
                wayApproved = false;
            }
        }
    }
    
    public int[] getNodeNeighbourCount()    {
        return neighbours;
    }
    
    public HashMap getAcceptedWays()   {
        return acceptedWays;
    }
}
