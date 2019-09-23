# Implementation

## General structure


## Graphs

### OSM XML parsing

### Random generation


## Algorithms

The program has three pathfinding algorithms, with A* being quite 
similar to dijkstra, while IDA* is a very different from the two.

### Dijkstra

In the worst case Dijkstra's algorithm goes through all the nodes 
and arcs in the graph, making it O(n + m). In addition all the arcs 
could be added and removed from the heap, both being O(m log m), 
increasing the worst case performance to O(n + m log m)[1].

### A*

A* differs from Dijkstra only with the addition of a heuristic 
function h(n) which calculates the direct distance from node n 
to the goal node. This heuristic is used to guide the search toward 
the goal node instead of spreading uniformly across the graph[2]. 

While A* should always explore less nodes than Dijkstra, (test this?) 
in practice A* can be slower in small graphs due to the time taken 
by the heuristic calculations. (link to testing doc here?)


### IDA*


## Sources

[1] A. Laaksonen, Tietorakenteet ja algoritmit. 

[2] n.d., "A\* search algorithm", Wikipedia. 
https://en.wikipedia.org/wiki/A*_search_algorithm
