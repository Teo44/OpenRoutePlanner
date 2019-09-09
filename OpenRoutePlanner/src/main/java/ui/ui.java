package ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
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
    
    public ui() {
         scanner = new Scanner(System.in);
    }
    
    public void start() {
        parseOSM();
        while(true) {
            dijkstra();
        }
        
    }
    
    public void dijkstra()  {
        dijkstra = new Dijkstra(graph);
        System.out.print("Enter the start node ID: ");
        long start = Long.parseLong(scanner.nextLine());
        System.out.print("Enter the destination node ID: ");
        long end = Long.parseLong(scanner.nextLine());
        dijkstra.shortestPath(start, end);
        
    }
    
    public void parseOSM()  {
        System.out.print("Enter name/path of OSM file to open: ");
        String osmFileName = scanner.nextLine();
        osmFile =  new File(osmFileName);
        parser = new OSMParser();
        graph = parser.parse(osmFile);
        System.out.println("Found " + graph.getNodeCount() + " nodes");
        
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
