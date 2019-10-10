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

We can see how the running time of Dijkstra increases quite linearly with the graph density, 
while A* is practically unaffected.

|Average times | Total time |
| --- | --- |
| ![Average time to find a path](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/test1_avg_time_graph_v2.png) | ![Total time for all paths](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/test1_total_time_minutes_graph_v2.png) |







### The effect of graph density in small graphs

The premise of theses tests is the same as in the previous segment, but the graph size 
is reduced to 50 000 nodes. IDA* was still too slow to be included. With the timeout 
at 10 seconds, IDA* averaged 8.3 seconds per path with 2 arcs per node, making including it 
infeasible.

2 arcs per node: Dijkstra 76s total, 30ms avg
		 A* 39s total, 15ms avg
		 IDA* 20928s, avg 8.371s

3 arcs per node: Dijkstra's 97s total, 38ms avg
		 A* 34s total, 13ms avg
		 IDA* 20165s totak, 8.66s avg

4 arcs per node: Dijkstra's 133s total, avg 53ms
		 A* 38s total, 15ms avg

5 arcs per node: Dijkstra's 158s total, avg 63ms
		 A* 36s total, avg 14ms

6 arcs: Dijkstra's total 174s, avg 69ms
	A* 35s, avg 13ms

7 arcs: Dijkstra's total 192s, avg 76ms
	A* total 33s, avg 13ms

8 arcs: Dijkstra's total 214s, avg 85ms
	A* total 33s, avg 13ms

9 arcs: Dijkstra's total 232s, avg 92ms
	A* toal 32s, avg 12ms

10 arcs: D total 261s, avg 104ms
	 A* total 34s, avg 13ms

11 arcs: D total 277s, avg 110ms
	 A* total 33s, avg 13ms

12 arcs: D total 295s, avg 117ms
	 A* total 33s, avg 13ms

13 arcs: D total 103s, avg 123ms
	 A* total 32s, avg 12ms

15 arcs: D total 355s, avg 141ms
	 A* total 33s, avg 13ms

16 arcs: D total 381s, avg 152ms
	 A* 33s, avg 13ms

20 arcs: D total 461s, avg 184ms
	 A* total 35s, avg 13ms

25 arcs: D total 560s, avg 224ms
	 A* total 39s, avg 15ms

30 arcs: D total 640s, 256ms avg
 	 A* total 39s, 15ms avg

35 arcs: D total 742s. 296ms avg
	 A* total 40s, 15ms avg

40 arcs: D total 833s, avg 333ms
	 A* total 45s, avg 17ms

45 arcs: D total 886s, avg 354ms
	 A* total 45s, avg 17ms

50 arcs: D total 992s, avg 396ms
	 A* total 48s, avg 19ms

55 arcs: D total 1062s, avg 424ms
	 A* total 61s, avg 24ms

60 arcs: D total 1153s, avg 461ms
	 A* 66s, avg 26ms

65 arcs: D total 1213s, avg 485ms
	 A* 68s, avg 27ms

### The effect of graph density in very small graphs.

The premise of theses tests is the same as in the previous segment, but the graph size 
is further reduced to 5000 nodes. The performance of IDA* in these graph was good enough 
to include it in the testing. 


TODO

## Performance tests in OSM maps

TODO: n random routes in Helsinki, Tokyo and Kanto OSM maps

### Random routes in an OSM map of Kumpula

Searched 500 shortest paths between two random nodes in an OSM map of Kumpula. The map is 
a ~1km by ~1km OpenStreetMap extract of the Kumpula Campus area.

Dijkstra took 626 milliseconds and A* 623 milliseconds in total, meaning an average time 
of ~1 millisecond for both of them. Here we can see how the addition of a heuristic can be 
unhelpful in a simple enough graph. 

Dijktra tood 626ms in total, ~1ms avg
A* took 623ms in total, ~1ms avg
IDA* took 2632ms in total, with avg of 529ms, but failed 495 with timeout of 5 seconds.

Even with timeout at 10 seconds IDA* still failed 494 out of 500 times.

### Random routes in an OSM map of Helsinki

timeout 10s, 500 random routes

Dijkstra 264s total, 528ms avg
A* 255s total, 509 avg
IDA* 8.2s total, 1029ms avg. 492/500 searches timed out.

### Random routes in an OSM map of Tokyo

Again searching 500 shortest paths between random notes, now in a graph parsed from a 
24km by 30km OpenStreetMap extract of Tokyo.

### Random routes in an OSM map of Kanto region


## Memory usage comparisons

TODO: run tests in small graphs with all the algorithms, compare memory usage with psrecord


## JUnit tests 
 
