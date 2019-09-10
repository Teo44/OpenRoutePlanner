package ui;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import logic.Dijkstra;
import logic.Graph;
import parser.OSMParser;

public class ui {
    
    private OSMParser parser;
    private ArrayList<Integer> nodeIDList;
    private Scanner scanner;
    private File osmFile;
    private Graph graph;
    private Dijkstra dijkstra;
    private long startTime;
    private long nanoTime;
    private long msTime;
    
    public ui() {
         scanner = new Scanner(System.in);
    }
    
    public void start() {
        parseOSM();
        
        System.out.println("Found " + graph.getNodeCount() + " nodes");
        if (msTime > 1000)  {
            System.out.println("Parsing took " + msTime / 1000 + " seconds");
        } else  {
            System.out.println("Parsing took " + msTime + " milliseconds");
        }
        
        while(true) {
            dijkstra();
        }
        
    }
    
    private void startNanoTimer()    {
        startTime = System.nanoTime();
    }
    
    private void stopNanoTimer()    {
        nanoTime = System.nanoTime() - startTime;
        msTime = nanoTime / 1000000;
    }
   
    /**
     * Prompts the user to enter two nodes, the shortest distance between 
     * them will be calculated with Dijkstra.
     */
    public void dijkstra()  {
        dijkstra = new Dijkstra(graph);
        System.out.print("Enter the start node ID: ");
        long start = Long.parseLong(scanner.nextLine());
        System.out.print("Enter the destination node ID: ");
        long end = Long.parseLong(scanner.nextLine());
        dijkstra.shortestPath(start, end);
        
    }
    
    /**
     * Prompts the user for an OSM XML file to be parsed into a graph.
     */
    public void parseOSM()  {
        System.out.print("Enter name/path of OSM file to open: ");
        String osmFileName = scanner.nextLine();
        osmFile =  new File(osmFileName);
        parser = new OSMParser();
        startNanoTimer();
        graph = parser.parse(osmFile);
        stopNanoTimer();
    }
    
    public void printNodes()    {
        if (nodeIDList != null)  {
            System.out.println("arraylist not null");
            for (Integer i : nodeIDList) {
                System.out.println("node: " + i);
            }
        } else  {
            System.out.println("arraylist null");
        }
    }
    
}
