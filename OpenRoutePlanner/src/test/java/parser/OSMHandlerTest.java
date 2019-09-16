package parser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import graph.Arc;
import graph.Graph;
import graph.Node;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

public class OSMHandlerTest {
    
    static OSMParser parser;
    static File test;
    static File test2;
    static ArrayList<String> approvedTags;
    static ArrayList<String> approvedTags2;
    static Graph graph;
    static Graph kumpula;
    static HashMap<Long, Node> nodes;
    static ArrayList<Arc>[] arcs;
    
    @BeforeClass
    public static void setUp()  {
        test = new File("test_files" + File.separator + "test.xml");
        test2 = new File("test_files" + File.separator + "Kumpula.osm");
        approvedTags = new ArrayList<>();
        approvedTags2 = new ArrayList<>();
        approvedTags2.add("highway");
        parser = new OSMParser();
        graph = parser.parse(test, approvedTags);
        kumpula = parser.parse(test2, approvedTags2);
    }
    
    @Test
    public void nodeCountCorrect1()  { 
        assertTrue(graph.getNodeCount() == 3);
    }
    
    @Test
    public void nodeCountCorrect2() {
        assertTrue(kumpula.getNodeCount() == 12832);
    }
    
    @Test
    public void arcCountCorrect1()    {
        assertTrue(graph.getArcCount() == 6);
    }
    
    @Test
    public void arcCountCorrect2()  {
        assertTrue(kumpula.getArcCount() == 8652);
    }
    
    @Test 
    public void nodesCorrect1()   {
        nodes = graph.getNodes();
        Node node = nodes.get(123l);
        assertTrue(node.getLat() == 54.0901447);
    }
    
    @Test 
    public void nodesCorrect2()   {
        nodes = graph.getNodes();
        Node node = nodes.get(123l);
        assertTrue(node.getLon() == 12.2516513);
    }
    
    @Test
    public void arcsCorrect1()  {
        arcs = graph.getAdList();
        assertTrue(arcs[0].get(0).getNd2() == 1);
    }
    
    @Test
    public void arcsCorrect2()  {
        arcs = graph.getAdList();
        assertTrue(arcs[0].get(1).getNd2() == 2);
    }
    
    @Test
    public void arcsCorrect3()  {
        arcs = kumpula.getAdList();
        nodes = kumpula.getNodes();
        Node nd1 = nodes.get(469651581l);
        assertTrue(arcs[nd1.getID2()].get(0).getNd2() == 2911);
    }
    

}
