package logic;

import algorithm.Dijkstra;
import graph.Arc;
import graph.Node;
import graph.Graph;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import parser.OSMParser;

public class DijkstraTest {
    
    static OSMParser parser;
    static File test;
    static File test2;
    static ArrayList<String> approvedTags;
    static ArrayList<String> approvedTags2;
    static Graph graph;
    static Graph kumpula;
    static HashMap<Long, Node> nodes;
    static ArrayList<Arc>[] arcs;
    static Dijkstra dijkstra;
    static Dijkstra dijkstraKumpula;

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
    
    @BeforeClass
    public static void before()    {
        dijkstra = new Dijkstra(graph);
        dijkstraKumpula = new Dijkstra(kumpula);
    }
    
    @Test
    public void sameStartAndEndNode()    {
        assertEquals(0, dijkstra.shortestPath(123l, 123l).getDist(), 0);
    }
    
    @Test
    public void testGraphPath1()    {
        assertEquals(0.48941663126567214, dijkstra.shortestPath(123l, 321l).getDist(), 0.00001);
    }
    
    @Test
    public void testGraphPath2()    {
        assertEquals(0.6386424878158962, dijkstra.shortestPath(321l, 666l).getDist(), 0.00001);
    }

    @Test
    public void testKumpulaPath1()  {
        assertEquals(1.0597847879291002, dijkstraKumpula.shortestPath(34399420l, 364533819l).getDist(), 0.00001);
    }
    
    @Test
    public void testKumpulaPath2()  {
        assertEquals(0.09573535075927543, dijkstraKumpula.shortestPath(34399420l, 583267403l).getDist(), 0.00001);
    }
}
