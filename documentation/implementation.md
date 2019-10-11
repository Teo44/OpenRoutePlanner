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

### Dijkstra's

In the worst case Dijkstra's algorithm goes through all the nodes 
and arcs in the graph, making it *O(n + m)*. In addition all the arcs 
could be added and removed from the heap, both being *O(m log m)*, 
increasing the worst-case performance to *O(n + m log m)*[1]. The worst-case
space complexity is *O(b^d)*, where *b* is the branching factor the *d* the depth 
of the search, since in the worst case all neighbours of all nodes are added to the 
heap.


### A*

A* differs from Dijkstra only with the addition of a heuristic 
function *h(n)* which calculates the direct distance from node n 
to the goal node. This heuristic is used to guide the search toward 
the goal node instead of spreading uniformly across the graph[2].

The worst-case space complexity of A* is the same *O(b^d)* as Dijkstra's, 
as Dijkstra's can be seen as a special case of A*. In practice A* should use less 
memory than Dijkstra's, since unfavorable paths are not explored.

While A* should always explore less nodes than Dijkstra, (test this?) 
in practice A* can be slower in small graphs due to the time taken 
by the heuristic calculations. (link to testing doc here?)


### IDA*

IDA* performs a series a depth-first searches, which are cut off at a when their total 
cost *f(n)* exceeds a given threshold. *f(n) = g(n) + h(n)*, where *g* is the real cost to reach the current node, and 
*h* is the heuristic estimation for the path *n...goal*. On the first iteration, the 
threshold is set to *h(start)*, and then incremented to the minimum value of all the 
*f(n)* that exceeded the previous threshold[3].

In the worst case, only one additional node in expanded on each iteration, resulting 
in the worst case time complexity of *O(b^d)*, where *b* is the branching factor and 
*d* is the depth of the result[4]. Thus IDA* can get severely outperformed by both A* 
and Dijkstra's in large and dense graphs. However IDA* is very space efficient compared 
to A* and Dijkstra's, as it only keeps one path at a time in memory, resulting in linear 
*O(d)* space complexity[3].

In this implementation the threshold is always increased by at least 10 meters. Because of this the 
path found by IDA* can be up to 10 meters longer than the actual optimal path. This tradeoff was chosen 
since it improved IDA*'s performance a lot, and an inaccuracy of >10 meters would be acceptable in 
many practical applications of pathfinding algorithms.

## Automated testing

Automated testing can be used to test any of the algorithms in multiple 
randomly generated graphs or an OSM map. The average and total times taken by each 
algorithm are calculated and displayed at the end of a test.

## Sources

[1] A. Laaksonen, Tietorakenteet ja algoritmit. 

[2] n.d., "A\* search algorithm", Wikipedia. 
https://en.wikipedia.org/wiki/A*_search_algorithm

[3] n.d., "Iterative deepening A\*", Wikipedia.
https://en.wikipedia.org/wiki/Iterative_deepening_A*


[4] Richard E. Korf, Depth-First Iterative-Deepening: An Optimal Admissible Tree Search.
Artificial Intelligence 27. https://cse.sc.edu/~mgv/csce580f09/gradPres/korf_IDAStar_1985.pdf



