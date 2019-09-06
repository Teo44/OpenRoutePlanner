package parser;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class OSMHandler extends DefaultHandler  {
    
    ArrayList<Integer> nodes;
    
    @Override
    public void startElement( String uri, String localName, String qName, Attributes attributes) throws SAXException {
        
        nodes = new ArrayList<>();
        
        if (qName.equals("node"))   {
            String id = attributes.getValue("id");
            System.out.println("node id: " + id);
            nodes.add(Integer.parseInt(id));
        } else if (qName.equals("way")) {
            String id = attributes.getValue("id");
            System.out.println("way id: " + id);
        }
    }
    
    public ArrayList<Integer> getNodeList() {
        return nodes;
    }
    
}
