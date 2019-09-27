package graph;

import algorithm.Dijkstra;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

public class RandomGraphGeneratorTest {
    
    private static RandomGraphGenerator gen;
    Graph graph;
    
    @BeforeClass()
    public static void setUp() {
        gen = new RandomGraphGenerator();
    }
    
    @Test
    public void arcCountCorrect1() {
        graph = gen.generateGraph(10, 10, 1, 1, false);
        assertEquals(200, graph.getArcCount());
    }
    
    @Test
    public void arcCountCorrect2()  {
        graph = gen.generateGraph(10, 10, 1, 1, true);
        assertEquals(218, graph.getArcCount());
    }
    
    @Test
    public void nodeCountCorrect1() {
        graph = gen.generateGraph(10, 10, 1, 1, true);
        assertEquals(10, graph.getNodeCount());
    }
    
    @Test
    public void nodeCountCorrect2() {
        graph = gen.generateGraph(123809, 1, 1, 1, false);
        assertEquals(123809, graph.getNodeCount());
    }
    
    @Test
    public void graphConnected()    {
        graph = gen.generateGraph(100, 0, 1, 1, true);
        Dijkstra d = new Dijkstra(graph);
        boolean routeFound = true;
        for (long i = 0; i < 99; i++)   {
            for (long j = 0; j < 99; j++)    {
                if (d.shortestPath(i, j).getDist() == Double.MAX_VALUE) {
                    routeFound = false;
                }
            }
        }
        assertTrue(routeFound);
    }
    
    @Test
    public void graphType2NodeCountCorrect()    {
        graph = gen.generateGraph2(100, 100, 2, 1, 1);
        assertEquals(10000, graph.getNodeCount());
    }
    
    @Test
    public void graphType2FirstRowConnected()   {
        graph = gen.generateGraph2(100, 100, 2, 1, 1);
        Dijkstra d = new Dijkstra(graph);
        boolean routeFound = true;
        for (long i = 1; i < 99; i++)   {
            if (d.shortestPath(0l, i).getDist() == Double.MAX_VALUE) {
                routeFound = false;
            }
        }
        assertTrue(routeFound);
    }
    
    @Test
    public void graphType2ArcCountCorrect() {
        graph = gen.generateGraph2(100, 100, 2, 1, 1);
        assertEquals(594, graph.getArcCount());
    }
    
}
