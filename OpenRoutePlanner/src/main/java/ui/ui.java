package ui;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import data_structure.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import algorithm.Astar;
import algorithm.Dijkstra;
import graph.Graph;
import graph.RandomGraphGenerator;
import algorithm.Result;
import parser.OSMParser;

public class ui {
    
    private OSMParser parser;
    private ArrayList<Integer> nodeIDList;
    final private Scanner scanner;
    private File osmFile;
    private Graph graph;
    private Dijkstra dijkstra;
    private Astar a_star;
    private long startTime;
    private long nanoTime;
    private long msTime;
    private ArrayList<String> approvedTags;
    
    private long startNode;
    private long endNode;
    
    public ui() {
         scanner = new Scanner(System.in);
    }
    
    public void start() {
        
        System.out.println("Parse an OSM or generate a random graph? [O]SM/[R]andom");
        while(true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("o"))    {
                askForApprovedWays();
                parseOSM();
                break;
            } else if (input.equalsIgnoreCase("r")) {
                randomGraph();
                break;
            }
        }
        
        System.out.println("Found " + graph.getNodeCount() + " nodes");
        System.out.println("Found " + graph.getArcCount() + " approved arcs");
        if (msTime > 1000)  {
            System.out.println("Parsing took " + msTime / 1000 +  "seconds");
        } else  {
            System.out.println("Parsing took " + msTime + " milliseconds");
        }
        
        System.out.println("");
        
        dijkstra = new Dijkstra(graph);
        a_star = new Astar(graph);
        
        while(true) {
            askForNodes();
            System.out.println("");
            dijkstra(startNode, endNode);
            if (msTime > 1000)  {
                System.out.println("Dijkstra took: " + msTime / 1000 + "." + msTime % 1000 + " seconds");
            } else  {
                System.out.println("Dijkstra took: " + msTime + " milliseconds");
            }
            System.out.println("");
            a_star(startNode, endNode);
            if (msTime > 1000)  {
                System.out.println("A* took: " + msTime / 1000 + "." + msTime % 1000 + " seconds");
            } else  {
                System.out.println("A* took: " + msTime + " milliseconds");
            }
            System.out.println("");
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
        System.out.println("Filter ways by tags? [Y]es/[N]o/[D]efault (highway) ");
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
    
    private void askForNodes()  {
        System.out.print("Enter the start node ID: ");
        startNode = Long.parseLong(scanner.nextLine());
        System.out.print("Enter the destination node ID: ");
        endNode = Long.parseLong(scanner.nextLine());
    }
   
    /**
     * Prompts the user to enter two nodes, the shortest distance between 
     * them will be calculated with Dijkstra.
     */
    public void dijkstra(long start, long end)  {
        startNanoTimer();
        Result result = dijkstra.shortestPath(start, end);
        stopNanoTimer();
        if (result.getDist() == Double.MAX_VALUE)   {
            System.out.println("No route was found");
        } else  {
            DecimalFormat df = new DecimalFormat("#.###");
            df.setRoundingMode(RoundingMode.CEILING);
            System.out.println("Shortest distance to node: " + df.format(result.getDist()) + "km");    
        }
        
        //printPath(result.getPreviousNode());
    }
    
    public void a_star(long start, long end) {
        startNanoTimer();
        Result result = a_star.shortestPath(start, end);
        stopNanoTimer();
        if (result.getDist() == Double.MAX_VALUE)   {
            System.out.println("No route was found");
        } else  {
            DecimalFormat df = new DecimalFormat("#.###");
            df.setRoundingMode(RoundingMode.CEILING);
            System.out.println("Shortest distance to node: " + df.format(result.getDist()) + "km");    
        }
        
        //printPath(result.getPreviousNode());
    }
    
    /**
     * Prompts the user for an OSM XML file's name to be parsed into a graph.
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
    
    private void randomGraph()  {
        System.out.print("Enter amount of nodes to generate: ");
        int nodeAmount = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter amount of arcs per node: ");
        int arcAmount = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter max difference in latitude between nodes: (1-180)");
        int maxLatDiff = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter max difference in longitude between nodes: (1-360)");
        int maxLonDiff = Integer.parseInt(scanner.nextLine());
        System.out.println("Make sure graph is connected? [Y]es/[N]o");
        boolean connected;
        while (true)    {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("y"))    {
                connected = true;
                break;
            } else if (input.equalsIgnoreCase("n")) {
                connected = false;
                break;
            }
        }
        RandomGraphGenerator generator = new RandomGraphGenerator();
        startNanoTimer();
        graph = generator.generateGraph(nodeAmount, arcAmount, maxLatDiff, maxLonDiff, connected);
        stopNanoTimer();
    }
    
    private void printPath(int[] previousNode) {
        
        System.out.println("Printing route: ");
        int prev = graph.getNodes().get(endNode).getID2();
        int start = graph.getNodes().get(startNode).getID2();
        HashMap hash = graph.getRealNodeID();
        
        while (prev != start) {
            System.out.println("Node: " + hash.get(previousNode[prev]));
            prev = previousNode[prev];
        }
    }
    
    private void printNodes()    {
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
