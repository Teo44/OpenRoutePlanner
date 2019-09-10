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
    private long startTime;
    private long nanoTime;
    private long msTime;
    private ArrayList<String> approvedTags;
    
    public ui() {
         scanner = new Scanner(System.in);
    }
    
    public void start() {
        
        askForApprovedWays();
        parseOSM();
        
        System.out.println("Found " + graph.getNodeCount() + " nodes");
        System.out.println("Found " + graph.getArcCount() + " approved arcs");
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
    
    private void askForApprovedWays()   {
        approvedTags = new ArrayList<>();
        System.out.print("Filter ways by tags? (Yes/No/Default) ");
        while (true)    {    
            String filter = scanner.nextLine();
            if (filter.equalsIgnoreCase("y"))   {
                System.out.println("Type \"quit\" to finish.");
                while(true) {
                    System.out.print("Add tag: ");
                    String tag = scanner.nextLine();
                    if (tag.equalsIgnoreCase("quit"))   {
                        break;
                    } 
                    approvedTags.add(tag);
                }
            } else if (filter.equalsIgnoreCase("n")) {
                break;
            } else if (filter.equalsIgnoreCase("d"))    {
                approvedTags.add("highway");
                break;
            }
        }
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
        graph = parser.parse(osmFile, approvedTags);
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
