package parser;

import java.util.ArrayList;
import java.util.HashMap;
import logic.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class OSMHandler extends DefaultHandler  {
    
    ArrayList<Integer>[] adList;
    HashMap<Integer, Node> nodes;
    int nodeCount;
    Node lastNode;
    
    ArrayList<Integer> nodeIDList;
    
    public OSMHandler() {
        nodes = new HashMap<>();
        nodeCount = 0;
        
        nodeIDList = new ArrayList<>();
    }
    
    @Override
    public void startElement( String uri, String localName, String qName, Attributes attributes) throws SAXException {
        
        if (qName.equals("node"))   {
            String id = attributes.getValue("id");
            String lat = attributes.getValue("lat");
            String lon = attributes.getValue("lon");
            //debug print
            //System.out.println("node id: " + id);
            // check that the node is valid; has an id and a location
            if (id != null && lat != null && lon != null)   {
                nodeCount += 1;
                Node node = new Node(Integer.parseInt(id), Double.parseDouble(lat), Double.parseDouble(lon));
                nodes.put(node.getId(), node);
            }
        } else if (qName.equals("way")) {
            // create the adjacency list, all nodes should be before ways in the 
            // OSM XML files, so this works
            if (adList == null)  {
                adList = new ArrayList[nodeCount];
            }
            if (qName.equals("nd")) {
                System.out.println(attributes.getValue("nd"));
            }
            String id = attributes.getValue("id");
            //debug print
            //System.out.println("way id: " + id);
            System.out.println(attributes.getLength());
        } else if (qName.equals("nd"))    {
            Integer newNode = Integer.parseInt(attributes.getValue("ref"));
            if (lastNode != null)   {
                System.out.println(nodeDistance(lastNode, nodes.get(newNode)));
                lastNode = nodes.get(newNode);
            } else  {
                lastNode = nodes.get(newNode);
            }
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("way"))    {
            lastNode = null;
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
