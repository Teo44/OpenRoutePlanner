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

15 arcs per node: Dijkstra's total 4798s, avg 1.919s
		  A* total 608s, avg 243ms


dijkstra totals in minutes: 22.2, 28.9, 32.8, 38.6, 43.7, 48.6, 52.1, 60.2, 59.2, 66.4, 67.6, 76.7, 81.1, 80
a* total times in minutes: 11.5 10.8 9.2 9.4 9.6 8.7 9.8 11.6 8.5 8.7 9.7 10.8 10.3 10.1
687 650 554 562 577 522 590 696 511 522 579 648 615 608


TODO: graphical representation of the results

![Average time to find a path](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/test1_avg_time_graph_v2.png)


![Total time for all paths](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/test1_total_time_minutes_graph_v2.png)


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
is further reduced to 5000 nodes.


TODO

## Performance tests in OSM maps

TODO: n random routes in Helsinki, Tokyo and Kanto OSM maps

### Random routes in an OSM map of Kumpula

Searched 500 shortest paths between two random nodes in an OSM map of Kumpula. 

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

### Random routes in an OSM map of Kanto region


## Memory usage comparisons

TODO: run tests in small graphs with all the algorithms, compare memory usage with psrecord


## JUnit tests 
 
