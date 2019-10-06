# Testing

## Performance tests in ramdomised graphs

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
and connects each of them to *b* other nodes. The timeout for each search was set to 3 
seconds to perform the tests at a reasonable pace.  

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

5 arcs per node: Dijkstra's total 2315s, avg of 925ms
		 A* total 562s, avg of 224ms

6 arcs per node: Dijkstra's total 2622s, avg 1.048 seconds.
		 A* total 577, avg 230ms

7 arcs per node: Dijkstra's total 2917s, avg 1.166s
		 A* total 522s, avg 208ms

8 arcs per node: DIjkstra's total 3123s, total 1.253s
		 A* total 590sm avg 236ms

9 arcs per node: Dijkstra's total 3612s, avg 1,444s
		 A* total 696s, avg 278ms

10 arcs per node: Dijkstra's total 3553, avg 1.421s
		  A* total 511, avg 204ms

11 arcs per node: Dijkstra's total 3982s, avg 1.592s
		  A* total 522s, avg 208ms

12 arcs per node: Dijkstra's total 4056s, avg 1.622s
		  A* total 579s, avg 231ms

13 arcs per node: Dijkstra's total 4599s, avg 1.839
		  A* total 648s, avg 259ms

14 arcs per node: Dijkstra's total 4864s, avg 1.945
		  A* total 615s, avg 245ms

15 arcs per node: 



TODO: graphical representation of the results



### The effect of graph density in small graphs

The premise of theses tests is the same as in the previous segment, but the graph size 
is reduced to 50 000 nodes. 

TODO

## Performance tests in OSM maps

TODO: n random routes in Helsinki, Tokyo and Kanto OSM maps

### Random routes in an OSM map of Helsinki

### Random routes in an OSM map of Tokyo

### Random routes in an OSM map of Kanto region


## Memory usage comparisons

TODO: run tests in small graphs with all the algorithms, compare memory usage with psrecord


## JUnit tests 
 
