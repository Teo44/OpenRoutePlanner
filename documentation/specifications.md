# Specifications

## Algorithms and data structures

A\* and Dijkstra will be implemented for the program, as well the heap and priority queue required for them.

## The problem

The goal is to find the shortest/fastest route between two street adresses. A\* was chosen due to it being one of the most well known shortest path algorithm, while Dijkstra is a simpler option that A\* expands upon. The comparison between should showcase how much heuristics can improve shortest path algorithms.

## The data

The current plan is to use data from openstreetnmap.org, and calculate shortest paths between real locations. Later on the program may be expanded to find the fastest routes, instead of the shortest, by using the average speeds that different routes allow.

## Performance

Well implemented Dijkstra should have time complexity O(n + m log n), which I will also aim for. While the complexity of A\* depends on the heuristic used, I aim is to make it noticeably faster than Dijkstra for my use case.


## Sources

https://en.wikipedia.org/wiki/A\*_search_algorithm

