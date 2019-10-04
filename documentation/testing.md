# Testing

## Performance in ramdomised graphs

Tests were (will be) performed using the automated testing in the program. 
To compare the performance of the algorithms in different kind of graphs, 
graphs with different amounts of nodes, arcs per nodes and maximum distances 
between the nodes were tested. 

The tests can all be easily reproduced with the automated testing. While the 
generated graphs will be different each time, large enough sample sizes will 
make up for the it to show the differences of the algorithms.


### The effect of graph density in large graphs

All tests were performed by searching 50 paths between randomly selected nodes 
in 50 different randomly generated graphs in a 500 000 node graph. 
The graphs were generated using the 
type 1 graphs of the graph generator, which places *n* nodes in random coordinates, 
and connects each of them to *b* other nodes. The timeout limit was set to 3 seconds, 
after which a search is terminated even if no path was found.  

IDA* was not used in these tests, as it times out for practically every path. 
The density of the nodes results in the threshold increasing very slowly, since 
the previous threshold is often exceeded by very little. The rate of the threshold's
incrementation cannot be increased, or the returned paths would no longer be 
guaranteed to be the shortest possible ones.

2 arcs per node: Dijkstra's total time 1332 seconds, average 532 ms per path.
		 A* total time 687s, average of 275ms per path
3 arcs per node: Dijkstra's total time 1723s, avg 689ms
		 A* 650s, avg 259ms
4 arcs per node: Dijkstra's total 1969s, avg of 787ms
		 A* total 554s, avg 221ms

TODO: graphical representation of the results



### The effect of graph density in small graphs

The premise of theses tests is the same as in the previous segment, but the graph size 
is reduced to 50 000 nodes. 

## Performance in OSM maps

TODO: n random routes in Helsinki, Tokyo and Kanto OSM maps

### Random routes in an OSM map of Helsinki

### Random routes in an OSM map of Tokyo

### Random routes in an OSM map of Kanto region


## Memory usage comparisons

TODO: run tests in small graphs with all the algorithms, compare memory usage with psrecord
 
