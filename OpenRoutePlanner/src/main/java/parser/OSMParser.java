package parser;

import java.io.File;
import data_structure.ArrayList;
import data_structure.HashMap;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import graph.Graph;

public class OSMParser { 
    /**
     * Parses an OSM XML file into a graph-object that the shortest 
     * path algorithms can use.
     * 
     * @param osm   OSM XML file
     * @param approvedTags List of OSM tags to filter out ways (eg. only highways).
     * Empty list results in no filtering.
     * @return Map as a graph-object
     */
    public Graph parse(File osm, ArrayList<String> approvedTags)  {
        try {
           SAXParserFactory factory = SAXParserFactory.newInstance();
           SAXParser saxParser = factory.newSAXParser();
           OSMNodeNeighbourCount counter = new OSMNodeNeighbourCount(approvedTags);
           saxParser.parse(osm, counter);
           int[] neighbours = counter.getNodeNeighbourCount();
           HashMap acceptedWays = counter.getAcceptedWays();
           SAXParserFactory factory2 = SAXParserFactory.newInstance();
           SAXParser saxParser2 = factory2.newSAXParser();
           OSMHandler handler = new OSMHandler(approvedTags, neighbours, acceptedWays);
           saxParser2.parse(osm, handler);
           return handler.getGraph();
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }
    
}
