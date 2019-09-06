package parser;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class OSMParser {
    
    public OSMParser()  {
        
    }
    
    public ArrayList<Integer> parse(File osm)  {
        try {
           SAXParserFactory factory = SAXParserFactory.newInstance();
           SAXParser saxParser = factory.newSAXParser();
           OSMHandler handler = new OSMHandler();
           saxParser.parse(osm, handler);
           return handler.getNodeList();
        } catch (Exception e) {
           e.printStackTrace();
        }
    
        return null;
    }
    
}
