# Testing

## Performance tests in ramdomised graphs

Tests were (will be) performed using the automated testing in the program. 
To compare the performance of the algorithms in different kind of graphs, 
graphs with different amounts of nodes, arcs per nodes and maximum distances 
between the nodes were tested. 

The tests can all be easily reproduced with the automated testing. While the 
generated graphs will be different each time, large enough sample sizes will 
make up for the it to show the differences of the algorithms. None of the graphs 
were guaranteed to be connected and the rest of the graph generation variables 
are written down for each test for replication.


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
is reduced to 50 000 nodes. IDA* was still too slow to be included. Even with the timeout 
at 10 seconds, IDA* timed our for 2022 out of the 2500 paths even with 1 arc per node, and was 
thus not included in the this test.

![Average time per path](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/test2_avg_time_graph.png)


### The effect of graph density in very small graphs.

The premise of theses tests is the same as in the previous segment, but the graph size 
is further reduced to 5000 nodes. The performance of IDA* in these graphs was good enough 
to include it in the testing, though it still times out for a good portion of the paths with 
the timeout at 5 seconds. 

The performance of IDA* is orders of magnitude slower than Dijkstra's 
and A*, so its results were visualised separately. The timeout was again set to 5 seconds to make 
the tests' running times practical, resulting in IDA* still failing a portion of the paths. The 
amount of timeouts was also recorded and displayed in the results. There are also two average 
times per path displayed for IDA*. The first one is calculated only from the successfully found 
paths, while the second one gives the minimum actual average time, where each fails is calculated 
to have taken 5 seconds.

| Dijkstra's and A* total times | IDA* timeouts per test|
| --- | --- | 
| ![Dijkstra's and A* total times](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/test3_total_times.png) | ![IDA* timeouts per test](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/test3_ida_timeouts.png) |
| --- | --- | 
| IDA* averages of successful searches | IDA* minimum actual averages | 
| --- | --- | 
| ![IDA* success averages](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/test3_ida_avg.png) | ![IDA* minimum actual averages](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/test3_ida_real_avg.png) |
	  

## Performance tests in OSM maps

Each test was performed by searching 500 shortest paths between two random nodes with each algorithm.

### Random routes in an OSM map of Kumpula

![](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/kumpula_osm_area.png)

The test was performed in a ~1km by ~1km OpenStreetMap extract of the Kumpula Campus area with a 5 second time out limit.

Dijkstra took 598 milliseconds and A* 540 milliseconds in total, meaning an average time 
of ~1 millisecond for both of them. We can see how the heuristic helps a little even in such a 
small graph.

IDA* meanwhile only found 353 out of 500 paths within the time out limit of 5 seconds. The average time for 
these 353 paths was 383 milliseconds. We can calculate the total time to find all the paths with IDA* to be in the excess of 
29 minutes, making IDA* over 1400 times slower than Dijkstra in this graph.

The map is a custom extract using https://extract.bbbike.org.

### Random routes in an OSM map of Helsinki

![](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/helsinki_osm_area.png)

Graph was a parsed OSM map of Helsinki, timeout set to 10 seconds.

Dijktra and A* were still very close, with average time to find a path at 492 and 529 milliseconds respectively. 

IDA* only managed to find 7 out of the 500 paths within 10 seconds, which makes IDA* pretty much unusable at this 
scale, unless memory usage is very constrained. 

[Map download](https://download.bbbike.org/osm/bbbike/Helsinki/)

### Random routes in an OSM map of Tokyo

![](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/tokyo_osm_area.png)

Again searching 500 shortest paths between random notes, now in a graph parsed from a 
24km by 30km OpenStreetMap extract of Tokyo.

Dijstra: 187 seconds in total, average 373 milliseconds
A*: 183 seconds total, average 365 milliseconds

Interestingly Dijkstra failed in exactly one path with the 5 second timeout, even though it performed very close to A* on average.

[Map download](https://download.bbbike.org/osm/bbbike/Tokyo/)

### Random routes in an OSM map of Kanto region

![](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/kanto_osm_area.png)

The area of Kanto, Japan. The largest map I could reasonably parse for testing, around 9-10 gigabytes of RAM is required. Timeout was set to 20 seconds, since some paths in this graphs can take very long to find.

Even in a graph this large the difference of the averages was small:
Dijkstra's average was 7.794 seconds, A*'s 7.831 seconds.

Total times for the 500 paths were both ~65 minutes.

[Image credit and map download](https://download.geofabrik.de/asia/japan/kanto.html)

## JUnit tests 
 
