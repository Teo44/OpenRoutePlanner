package ui;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import data_structure.ArrayList;
import java.util.Scanner;
import algorithm.Astar;
import algorithm.Dijkstra;
import algorithm.IDAStar;
import graph.Graph;
import graph.RandomGraphGenerator;
import algorithm.Result;
import java.util.Random;
import parser.OSMParser;

public class ui {
    
    private OSMParser parser;
    private ArrayList<Integer> nodeIDList;
    final private Scanner scanner;
    private File osmFile;
    private Graph graph;
    private Dijkstra dijkstra;
    private Astar a_star;
    private IDAStar ida_star;
    private long startTime;
    private long nanoTime;
    private long msTime;
    private ArrayList<String> approvedTags;
    private boolean graphExists;
    private int timeOut;
    
    private long startNode;
    private long endNode;
    
    private Result dijkstraResult;
    private Result AstarResult;
    private Result IDAstarResult;
    
    private boolean useDijkstra;
    private boolean useAstar;
    private boolean useIDAstar;
    
    private int testGraphCount;
    private int testNodeCount;
    private int testArcCount;
    private int testPathCount;
    private boolean testConnected;
    private Graph testGraph;
    private int testGraphType;
    private int testGraphX;
    private int testGraphY;
    
    public ui() {
         scanner = new Scanner(System.in);
         useDijkstra = true;
         useAstar = true;
         useIDAstar = true;
         timeOut = 5;
    }
    
    /**
     * The main menu loop
     */
    public void start() {
        
//        printLogo();
        System.out.println("");
        System.out.println("OpenRoutePlanner");
        System.out.println("");
        System.out.println("Main menu: ");
        System.out.println("G - parse or generate a new graph");
        System.out.println("P - Find paths in the current graph");
        System.out.println("T - automated performance testing");
        System.out.println("Q - quit");
        while (true)    {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("g"))  {
                graphExists = true;
                System.out.println("");
                newGraph();
                System.out.println("");
            } else if (input.equalsIgnoreCase("t")) {
                System.out.println("");
                performanceTest();
                System.out.println("");
            } else if (input.equalsIgnoreCase("p")) {
                System.out.println("");
                if (!(graphExists))  {
                    System.out.println("No graph has been set");
                } else  {
                    pathFindingLoop();
                }
                System.out.println("");
            } else if (input.equalsIgnoreCase("q"))  {
                break;
            } else  {
                System.out.println("Not a valid option");
            }
            System.out.println("Main menu: ");
        }
    }
    
    /**
     * For timing algorithms and graph parsing
     */
    private void startNanoTimer()    {
        startTime = System.nanoTime();
    }
    
    private void stopNanoTimer()    {
        nanoTime = System.nanoTime() - startTime;
        msTime = nanoTime / 1000000;
    }
    
    /**
     * The path finding menu loop
     */
    private void pathFindingLoop()  {
        System.out.println("Pathfinding: ");
        System.out.println("P - enter two nodes and find the shortest path between them");
        System.out.println("S - print the path of the last search");
        System.out.println("A - choose the algorithms to be used (default: all)");
        System.out.println("T - set timeout length (default: 5 seconds)");
        System.out.println("Q - return to the main menu");
        while(true) {
            String input = scanner.nextLine();
            if(input.equalsIgnoreCase("p")) {
                askForNodes();
                findPath();
            } else if (input.equalsIgnoreCase("a")) {
                chooseAlgorithms();
            } else if (input.equalsIgnoreCase("t")) {
                setTimeOut();
            } else if (input.equalsIgnoreCase("s")) {
                System.out.println("Previous route: ");
                printPath();
                System.out.println("");
            } else if (input.equalsIgnoreCase("q"))  {  
                break;
            } else  {
                System.out.println("Not a valid option");
            }
            System.out.println("Pathfinding: ");
        }
    }
    
    /**
     * Menu to change the timeout limit of the algorithms
     */
    private void setTimeOut()   {
        System.out.println("Enter timeout limit in seconds: ");
        while(true) {
            try {
                timeOut = Integer.parseInt(scanner.nextLine());
                break;
            } catch(Exception e)    {
                System.out.println(e);
            }
        }
    }
    
    /**
     * An automated performance tests. Can generate multiple graphs, 
     * search random paths in them and keep track of each algorithms 
     * performance. Helpful for larger scale testing.
     */
    private void performanceTest()  {
        performanceTestOptions();
        System.out.println("");
        System.out.println("Testing: ");
        RandomGraphGenerator generator = new RandomGraphGenerator();
        Random random = new Random();
        long dijkstraTotalTime = 0;
        long AstarTotalTime = 0;
        long IDAstarTotalTime = 0; 
        int dijkstraFails = 0;
        int AstarFails = 0;
        int IDAstarFails = 0;
        if (testGraphType == 2 || testGraphType == 3) {
            for (int g = 0; g < testGraphCount; g++)    {
                System.out.println("Generating graph...");
                if (testGraphType == 2) {
                    testGraph = generator.generateGraph(testNodeCount, testArcCount, 10, 10, testConnected);
                } else  {
                    testGraph = generator.generateGraph2(testGraphX, testGraphY, testArcCount, 10, 10);
                }
                dijkstra = new Dijkstra(testGraph);
                a_star = new Astar(testGraph);
                ida_star = new IDAStar(testGraph);
                for (int p = 0; p < testPathCount; p++) {
                    int testStartNode = random.nextInt(testNodeCount);
                    int testEndNode = random.nextInt(testNodeCount);
                    if (useDijkstra)    {
                        System.out.print("Dijkstra's: ");
                        dijkstra(testStartNode, testEndNode);
                        if (dijkstraResult.timedOut())  {
                            dijkstraFails += 1;
                        } else  {
                            dijkstraTotalTime += msTime;
                        }
                    }
                    if (useAstar)   {
                        System.out.print("A*: ");
                        a_star(testStartNode, testEndNode);
                        if (AstarResult.timedOut()) {
                            AstarFails += 1;
                        } else  {
                            AstarTotalTime += msTime;
                        }
                    }
                    if (useIDAstar)    {
                        System.out.print("IDA*: ");
                        ida_star(testStartNode, testEndNode);
                        if (IDAstarResult.timedOut())   {
                            IDAstarFails += 1;
                        } else  {
                            IDAstarTotalTime += msTime;
                        }
                    }
                }
            }
        } else  {
            dijkstra = new Dijkstra(testGraph);
            a_star = new Astar(testGraph);
            ida_star = new IDAStar(testGraph);
            long[] realNodeID = testGraph.getRealNodeID();
            for (int p = 0; p < testPathCount; p++) {
                long testStartNode = realNodeID[random.nextInt(testNodeCount)];
                long testEndNode = realNodeID[random.nextInt(testNodeCount)];
                if (useDijkstra)    {
                    System.out.print("Dijkstra's: ");
                    dijkstra(testStartNode, testEndNode);
                    if (dijkstraResult.timedOut())  {
                        dijkstraFails += 1;
                    } else  {
                        dijkstraTotalTime += msTime;
                    }
                }
                if (useAstar)   {
                    System.out.print("A*: ");
                    a_star(testStartNode, testEndNode);
                    if (AstarResult.timedOut()) {
                        AstarFails += 1;
                    } else  {
                        AstarTotalTime += msTime;
                    }
                }
                if (useIDAstar)    {
                    System.out.print("IDA*: ");
                    ida_star(testStartNode, testEndNode);
                    if (IDAstarResult.timedOut())   {
                        IDAstarFails += 1;
                    } else  {
                        IDAstarTotalTime += msTime;
                    }
                }
            }
        }
        System.out.println("");
        if (useDijkstra)    {
            System.out.print("Dijkstra took ");
            if (dijkstraTotalTime > 1000)  {
                System.out.print(dijkstraTotalTime / 1000 + "." + dijkstraTotalTime % 1000 + " seconds ");
            } else  {
                System.out.print(dijkstraTotalTime + " milliseconds ");
            }
            System.out.print("in total ");
            System.out.print("and an average of ");
            long dijkstraAvgTime = dijkstraTotalTime / (testGraphCount * testPathCount);
            if (dijkstraAvgTime > 1000)  {
                System.out.print(dijkstraAvgTime / 1000 + "." + dijkstraAvgTime % 1000 + " seconds ");
            } else  {
                System.out.print(dijkstraAvgTime + " milliseconds ");
            }
            System.out.println("per path");
        }
        if (useAstar)    {
            System.out.print("A* took ");
            if (AstarTotalTime > 1000)  {
                System.out.print(AstarTotalTime / 1000 + "." + AstarTotalTime % 1000 + " seconds ");
            } else  {
                System.out.print(AstarTotalTime + " milliseconds ");
            }
            System.out.print("in total ");
            System.out.print("and an average of ");
            long AstarAvgTime = AstarTotalTime / (testGraphCount * testPathCount);
            if (AstarAvgTime > 1000)  {
                System.out.print(AstarAvgTime / 1000 + "." + AstarAvgTime % 1000 + " seconds ");
            } else  {
                System.out.print(AstarAvgTime + " milliseconds ");
            }
            System.out.println("per path");
        }
        
        if (useIDAstar)    {
            System.out.print("IDA* took ");
            if (IDAstarTotalTime > 1000)  {
                System.out.print(IDAstarTotalTime / 1000 + "." + IDAstarTotalTime % 1000 + " seconds ");
            } else  {
                System.out.print(IDAstarTotalTime + " milliseconds ");
            }
            System.out.print("in total ");
            System.out.print("and an average of ");
            long IDAstarAvgTime = IDAstarTotalTime / (testGraphCount * testPathCount);
            if (IDAstarAvgTime > 1000)  {
                System.out.print(IDAstarAvgTime / 1000 + "." + IDAstarAvgTime % 1000 + " seconds ");
            } else  {
                System.out.print(IDAstarAvgTime + " milliseconds ");
            }
            System.out.println("per path");
        }
        if (dijkstraFails > 0)  {
            System.out.println("Dijkstra failed " + dijkstraFails + " times");
        } 
        if (AstarFails > 0) {
            System.out.println("A* failed " + AstarFails + " times");
        }
        if (IDAstarFails >  0)  {
            System.out.println("IDA* failed " + IDAstarFails + " times");
        }
    }
    
    /**
     * The menu to set the variables of the performance test, like what kind 
     * of graph, which algorithms will be used and how many paths searched.
     */
    private void performanceTestOptions()  {
        System.out.println("Set the algorithms to use: ");
        chooseAlgorithms();
        System.out.println("Use an OSM graph or generate random graph? [O]SM/[R]andom");
        while (true)    {
            try {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("o"))    {
                    testGraphType = 1;
                    break;
                } 
                if (input.equalsIgnoreCase("r"))    {
                    System.out.println("Select graph type 1 or 2?");
                    while(true) {
                        String input2 = scanner.nextLine();
                        if (input2.equalsIgnoreCase("1"))   {
                            testGraphType = 2;
                            break;
                        } 
                        if (input2.equalsIgnoreCase("2"))   {
                            testGraphType = 3;
                            break;
                        }
                    }
                    break;
                }
            } catch (Exception e)   {
                System.out.println(e);
            }
        }
        if (testGraphType == 2) {
            System.out.print("How many different graphs to generate? ");
            while (true)    {
                try {
                    testGraphCount = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (Exception e)   {
                    System.out.println(e);
                }
            }
            System.out.print("How many nodes to generate per graph? ");
            while (true)    {
                try {
                    testNodeCount = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (Exception e)   {
                    System.out.println(e);
                }
            }
            System.out.println("How many arcs to generate per node? ");
            while (true)    {
                try {
                    testArcCount = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (Exception e)   {
                    System.out.println(e);
                }
            }
            System.out.println("How many paths to find in each graph? ");
            while (true)    {
                try {
                    testPathCount = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (Exception e)   {
                    System.out.println(e);
                }
            }
            System.out.println("Make sure graph is connected? ([Y]es/[N]o) ");
            while (true)    {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("y"))    {
                    testConnected = true;
                    break;
                } else if (input.equalsIgnoreCase("n")) {
                    testConnected = false;
                    break;
                }
            }
        } else if (testGraphType == 3)   {
            System.out.print("How many different graphs to generate? ");
            while (true)    {
                try {
                    testGraphCount = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (Exception e)   {
                    System.out.println(e);
                }
            }
            System.out.println("Enter dimension x of the graph: ");
            while (true)    {
                try {
                    testGraphX = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (Exception e)   {
                    System.out.println(e);
                }
            }
            System.out.println("Enter dimension y of the graph: ");
            while (true)    {
                try {
                    testGraphY = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (Exception e)   {
                    System.out.println(e);
                }
            }
            System.out.println("Enter the amount of ways leading through the graph: ");
            while (true)    {
                try {
                    testArcCount = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (Exception e)   {
                    System.out.println(e);
                }
            }
            System.out.println("How many paths to find in each graph? ");
            while (true)    {
                try {
                    testPathCount = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (Exception e)   {
                    System.out.println(e);
                }
            }
            testNodeCount = testGraphX * testGraphY;
        } else  {
            askForApprovedWays();
                parseOSMForTest();
                testNodeCount = testGraph.getNodeCount();
                testGraphCount = 1;
                System.out.println("Found " + testGraph.getNodeCount() + " nodes");
                System.out.println("Found " + testGraph.getArcCount() + " approved arcs");
                if (msTime > 1000)  {
                    System.out.println("Parsing took " + msTime / 1000 +  "seconds");
                } else  {
                    System.out.println("Parsing took " + msTime + " milliseconds");
                }
                System.out.println("How many paths to find in the graph? ");
                while (true)    {
                    try {
                        testPathCount = Integer.parseInt(scanner.nextLine());
                        break;
                    } catch (Exception e)   {
                        System.out.println(e);
                    }
                }
        }
        setTimeOut();
    }
    
    /**
     * The menu for choosing algorithms to use.
     */
    private void chooseAlgorithms() {
        System.out.print("Dijkstra's - ");
        if (useDijkstra)    {
            System.out.print("enabled, ");
        } else  {
            System.out.print("disabled, ");
        }
        System.out.println("D to toggle");
        System.out.print("A* - ");
        if (useAstar)   {
            System.out.print("enabled, ");
        } else  {
            System.out.print("disabled, ");
        }
        System.out.println("A to toggle");
        System.out.print("IDA* - ");
        if (useIDAstar) {
            System.out.print("enabled, ");
        } else  {
            System.out.print("disabled, ");
        }
        System.out.println("I to toggle");
        System.out.println("Q - return");
        System.out.println("");
        while (true)    {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("d")) {
                useDijkstra = !useDijkstra;
                System.out.println("Toggled dijkstra's");
            } else if (input.equalsIgnoreCase("a")) {
                useAstar = !useAstar;
                System.out.println("Toggled A*");
            } else if (input.equalsIgnoreCase("i")) {
                useIDAstar = !useIDAstar;
                System.out.println("Toggled IDA*");
            } else if (input.equalsIgnoreCase("q")) {
                break;
            }
        }
    }
    
    /**
     * Method to search for a path with all the enabled algorithms.
     */
    private void findPath() {
        if (useDijkstra)    {
            dijkstra(startNode, endNode);
            if (msTime > 1000)  {
                System.out.println("Dijkstra took: " + msTime / 1000 + "." + msTime % 1000 + " seconds");
            } else  {
                System.out.println("Dijkstra took: " + msTime + " milliseconds");
            }
            System.out.println("");
        }
        if (useAstar)   {
            a_star(startNode, endNode);
            if (msTime > 1000)  {
                System.out.println("A* took: " + msTime / 1000 + "." + msTime % 1000 + " seconds");
            } else  {
                System.out.println("A* took: " + msTime + " milliseconds");
            }
            System.out.println("");
        }
        if (useIDAstar) {
            ida_star(startNode, endNode);
            if (msTime > 1000)  {
                System.out.println("IDA* took: " + msTime / 1000 + "." + msTime % 1000 + " seconds");
            } else  {
                System.out.println("IDA* took: " + msTime + " milliseconds");
            }
            System.out.println("");
        }
    }
    
    /**
     * Menu for setting the way filtering for parsing an OSM map.
     */
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
    
    private void printLogo()    {
        // doesn't really work without a monospace font...
        System.out.println("");
        System.out.println("	+--------------------+");
        System.out.println("	|  OpenRoutePlanner  |");
        System.out.println("	+--------------------+");
        System.out.println("");
    }
    
    /**
     * The menu for parsing an OSM map or generating a random one.
     */
    private void newGraph() {
        System.out.println("Parse an OSM or generate a random graph? [O]SM/[R]andom");
        while(true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("o"))    {
                askForApprovedWays();
                parseOSM();
                System.out.println("Found " + graph.getNodeCount() + " nodes");
                System.out.println("Found " + graph.getArcCount() + " approved arcs");
                if (msTime > 1000)  {
                    System.out.println("Parsing took " + msTime / 1000 +  "seconds");
                } else  {
                    System.out.println("Parsing took " + msTime + " milliseconds");
                }
                dijkstra = new Dijkstra(graph);
                a_star = new Astar(graph);
                ida_star = new IDAStar(graph);
                break;
            } else if (input.equalsIgnoreCase("r")) {
                System.out.println("Select graph type: ");
                System.out.println("1 - A completely random graph with n nodes and m ways");
                System.out.println("2 - A graph with x by y evenly distributed nodes");
                System.out.println("    and n ways leading through the graph");
                System.out.println("");
                System.out.println("    Graph 2 is not guaranteed to be connected, but nodes ");
                System.out.println("    0 to x - 1 are always connected and will have routes to most nodes");
                while (true)    {
                    try {
                        int type = Integer.parseInt(scanner.nextLine());
                        if (type == 1)  {
                            randomGraph();
                            break;
                        }
                        if (type == 2)  {
                            randomGraph2();
                            break;
                        }
                        System.out.println("Not a valid option");
                    } catch(Exception e)    {
                        System.out.println(e);
                    }
                }
                System.out.println("Generated " + graph.getNodeCount() + " nodes");
                System.out.println("Generated " + graph.getArcCount() + " approved arcs");
                if (msTime > 1000)  {
                    System.out.println("Generating took " + msTime / 1000 +  "seconds");
                } else  {
                    System.out.println("Generating took " + msTime + " milliseconds");
                }
                dijkstra = new Dijkstra(graph);
                a_star = new Astar(graph);
                ida_star = new IDAStar(graph);
                break;
            } else  {
                System.out.println("Not a valid option");
            }
        }
    }
    
    /**
     * The method to enter the start and goal nodes for a search.
     */
    private void askForNodes()  {
        System.out.print("Enter the start node ID: ");
        while (true)    {
            try {
                startNode = Long.parseLong(scanner.nextLine());
                break;
            } catch (Exception e)   {
                System.out.println(e);
            }
        }
        System.out.print("Enter the destination node ID: ");
        while (true)    {
            try {
                endNode = Long.parseLong(scanner.nextLine());
                break;
            } catch (Exception e)   {
                System.out.println(e);
            }
        }
        System.out.println("");
    }
   
    /**
     * Uses Dijkstra to search for a path between the given nodes.
     * @param start
     * @param end 
     */
    public void dijkstra(long start, long end)  {
        dijkstra.setTimeOut(timeOut);
        startNanoTimer();
        dijkstraResult = dijkstra.shortestPath(start, end);
        stopNanoTimer();
        if (dijkstraResult.getDist() == Double.MAX_VALUE)   {
            System.out.println("No route was found");
        } else  {
            DecimalFormat df = new DecimalFormat("#.###");
            df.setRoundingMode(RoundingMode.CEILING);
            System.out.println("Shortest distance to node: " + df.format(dijkstraResult.getDist()) + "km");    
        }
    }
    
    /**
     * Uses A* to search for a path between the given nodes.
     * @param start
     * @param end 
     */
    public void a_star(long start, long end) {
		a_star.setTimeOut(timeOut);
        startNanoTimer();
        AstarResult = a_star.shortestPath(start, end);
        stopNanoTimer();
        if (AstarResult.getDist() == Double.MAX_VALUE)   {
            System.out.println("No route was found");
        } else  {
            DecimalFormat df = new DecimalFormat("#.###");
            df.setRoundingMode(RoundingMode.CEILING);
            System.out.println("Shortest distance to node: " + df.format(AstarResult.getDist()) + "km");    
        }
    }
    
    /**
     * Uses IDA* to search for a path between the given nodes.
     * @param start
     * @param end 
     */
    public void ida_star(long start, long end)  {
        ida_star.setTimeOut(timeOut);
        startNanoTimer();
        IDAstarResult = ida_star.shortestPath(start, end);
        stopNanoTimer();
                if (IDAstarResult.getDist() == Double.MAX_VALUE)   {
            System.out.println("No route was found");
        } else  {
            DecimalFormat df = new DecimalFormat("#.###");
            df.setRoundingMode(RoundingMode.CEILING);
            System.out.println("Shortest distance to node: " + df.format(IDAstarResult.getDist()) + "km");
        }
    }
    
    /**
     * Prompts the user for an OSM XML file's name to be parsed into a graph.
     */
    public void parseOSM()  {
        while (true)    {
            try {
                System.out.print("Enter name/path of OSM file to open: ");
                String osmFileName = scanner.nextLine();
                osmFile =  new File(osmFileName);
                if (osmFile.exists())   {
                    break;
                }
                System.out.println("Not a valid file");
            } catch (Exception e)   {
                System.out.println(e);
            }
        }
        System.out.println("Parsing...");
        parser = new OSMParser();
        startNanoTimer();
        graph = parser.parse(osmFile, approvedTags);
        stopNanoTimer();
    }
    
    /**
     * Prompts the user for an OSM XML file's name to be parsed into a graph for the 
     * automated testing.
     */
    public void parseOSMForTest()  {
        while (true)    {
            try {
                System.out.print("Enter name/path of OSM file to open: ");
                String osmFileName = scanner.nextLine();
                osmFile =  new File(osmFileName);
                if (osmFile.exists())   {
                    break;
                }
                System.out.println("Not a valid file");
            } catch (Exception e)   {
                System.out.println(e);
            }
        }
        parser = new OSMParser();
        startNanoTimer();
        testGraph = parser.parse(osmFile, approvedTags);
        stopNanoTimer();
    }
    
    /**
     * Takes the variables for generating a type 1 random graph.
     */
    private void randomGraph()  {
        System.out.print("Enter amount of nodes to generate: ");
        int nodeAmount = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter amount of arcs per node: ");
        int arcAmount = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the latitude range between nodes: (1-90)");
        int maxLatDiff = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the longitude range between nodes: (1-180)");
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
    
    /**
     * Takes the variables for generating a type 2 random graph.
     */
    private void randomGraph2() {
        System.out.println("Enter dimension x of the graph: ");
        int x = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter dimension y of the graph: ");
        int y = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the amount of ways leading through the graph: ");
        int ways = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the latitude range between nodes: (1-90)");
        int maxLatDiff = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the longitude range between nodes: (1-180)");
        int maxLonDiff = Integer.parseInt(scanner.nextLine());
        RandomGraphGenerator generator = new RandomGraphGenerator();
        startNanoTimer();
        graph = generator.generateGraph2(x, y, ways, maxLatDiff, maxLonDiff);
        stopNanoTimer();
    }
    
    /**
     * Prints out the previously searched path in the form of the 
     * IDs of the nodes.
     */
    private void printPath() {
        int[] prevNode = dijkstraResult.getPreviousNode();
        int prev = graph.getNodes().get(endNode).getID2();
        int start = graph.getNodes().get(startNode).getID2();
        long[] hash = graph.getRealNodeID();
        ArrayList<Long> path = new ArrayList<>();
        
        path.add(hash[prev]);
        while (prev != start) {
            path.add(hash[(prevNode[prev])]);
            prev = prevNode[prev];
        }
        
        while(!path.isEmpty())  {
            System.out.println(path.pollLast());
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
