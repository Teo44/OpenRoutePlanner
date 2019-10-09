# Specifications

## Algorithms and data structures

A\* and Dijkstra will be implemented for the program, as well the heap and priority queue required for them.

## The problem

The goal is to find the shortest/fastest route between two street adresses. A\* was chosen due to it being one of the most well known shortest path algorithm, while Dijkstra is a simpler option that A\* expands upon. The comparison between them should showcase how much heuristics can improve shortest path algorithms.

## The data

The current plan is to use data from openstreetmap.org, and calculate shortest paths between real locations. Later on the program may be expanded to find the fastest routes, instead of the shortest, by using the average speeds that different routes allow.

Openstreetmap data can be downloaded in XML format with a specific OSM schema, from which nodes and ways (arcs in the graphs) can be parsed.

## Performance

Well implemented Dijkstra should have time complexity O(n + m log n), which I will also aim for. While the complexity of A\* depends on the heuristic used, I aim to make it noticeably faster than Dijkstra for my use case.


## Sources

https://en.wikipedia.org/wiki/A*_search_algorithm

