package parser;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import logic.Graph;

public class OSMParser {
    
    public OSMParser()  {
        
    }
    
    /**
     * Parses an OSM XML file into a graph-object that the shortest 
     * path algorithms can use.
     * 
     * @param osm   OSM XML file
     * @return Map as a graph-object
     */
    public Graph parse(File osm, ArrayList<String> approvedTags)  {
        try {
           SAXParserFactory factory = SAXParserFactory.newInstance();
           SAXParser saxParser = factory.newSAXParser();
           OSMHandler handler = new OSMHandler(approvedTags);
           saxParser.parse(osm, handler);
           return handler.getGraph();
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }
    
}
