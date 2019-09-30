# Implementation

## UI

OpenRoutePlanner has a basic text UI, from which users can find shortest paths 
in OSM maps and randomly generated graphs, and run automated tests. 

## Graphs

### OSM XML parsing

OpenRoutePlanner can parse Open street map's XML files, that contain 
real world maps represented by nodes and ways between the nodes. Ways can 
be filtered according to their tags, and all the nodes that have approved 
ways leading to them will be added to the graph.  

### Random generation

OpenRoutePlanner can also generate random graphs for testing the algorithms. 
The graph generator takes a node count, arc count and maximum difference in the 
nodes' latitude and longitude as variables, and generates a graph accordingly.


## Algorithms

OpenRoutePlanner has three pathfinding algorithms, with A* being quite 
similar to dijkstra, while IDA* is a very different from the two.

### Dijkstra

Dijkstra checks the neighbours of each node, first the start node and then the unvisited node 
with the smallest distance, until the target node is found. 

In the worst case Dijkstra's algorithm goes through all the nodes 
and their arcs in the graph before finding the targer, making the search *O(n + m)*. 
In addition all the arcs could be added and removed from the heap once, both being
 *O(m log m)* and increasing the worst case performance to *O(n + m log m)*[1].

### A*

A* differs from Dijkstra only with the addition of a heuristic 
function *h(n)* which calculates the direct distance from node n 
to the goal node. This heuristic is used to guide the search toward 
the goal node instead of spreading uniformly across the graph[2]. The weight of each 
node is calculated as the distance to reach it + the estimated shortest distance to the 
target node. This results in nodes that are physically closer to the target being explored 
first, which cuts down the time spent exploring paths leading the wrong way. 

While A* should always explore less nodes than Dijkstra, (test this?) 
in practice A* can be slower in very small graphs due to the time taken 
by the heuristic calculations. (link to testing doc here?)


### IDA*

Iterative deepening A* is a variant of the iterative deepening depth-first search algorithm 
that uses a heuristic to guide its search. The algorithm will call a depth-first search, that 
is cut off when a threshold is reached. Then the threshold is increased by the smallest 
distance that exceeded it, and a new depth-first search is called with this threshold. The 
depth-first searches with a threshold are called until the target node is found. 

The heuristic is used to set the initial threshold as the shortest possible distance to the 
target node, and the weight of each new node is calculated as the actual distance + the 
estimated shortest distance to the target, like in A*.

## Automated testing

Automated testing can be used to test any of the algorithms in multiple 
randomly generated graphs. The average and total times taken by each 
algorithm are calculated and displayed at the end of a test.

## Sources

[1] A. Laaksonen, Tietorakenteet ja algoritmit. 

[2] n.d., "A\* search algorithm", Wikipedia. 
https://en.wikipedia.org/wiki/A*_search_algorithm
