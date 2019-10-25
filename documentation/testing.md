# Testing

## Performance tests in randomised graphs

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

We can see how IDA*'s performance gets better the denser the graph, probably because less 
iterations are needed to find a path. Dijkstra's on the other hand slow down in the 
denser graphs with more arcs to examine per node.


| Dijkstra's and A* total times | IDA* timeouts per test|
| --- | --- | 
| ![Dijkstra's and A* total times](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/test3_total_times.png) | ![IDA* timeouts per test](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/test3_ida_timeouts.png) |

| IDA* averages of successful searches | IDA* minimum actual averages | 
| --- | --- | 
| ![IDA* success averages](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/test3_ida_avg.png) | ![IDA* minimum actual averages](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/test3_ida_real_avg.png) |
	  
	  
### The effect of graph size with same density.

In these tests 50 paths in 50 random graphs were searched, all with 4 arcs per node, node count increasing 
from 1000 to 10 000 and timeout limit of 5 seconds. IDA* was again orders of magnitude slower, and thus represented 
in its own graph. It also failed a signigant portion of the searches, which is also visualised below.

| Dijkstra's and A* total times | IDA* total times |
| --- | --- | 
| ![Dijkstra's and A* total times](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/test4_total_times_1.png) | ![IDA* total times](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/test4_total_times_2.png) |

| IDA* timeouts |
| --- | 
| ![IDA* timeouts](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/test4_ida_timeouts.png =400x276) |




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

| | Dijkstra's | A* | IDA* |
| --- | --- | --- | --- |
| Total | 598ms | 540ms | 135s (353/500) | 
| Average | ~1ms | ~1ms | 383ms |

The map is a custom extract using https://extract.bbbike.org.

### Random routes in an OSM map of Helsinki

![](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/helsinki_osm_area.png)

Graph was a parsed OSM map of Helsinki, timeout set to 10 seconds.

Dijktra and A* were still very close, with average time to find a path at 492 and 529 milliseconds respectively. 

IDA* only managed to find 7 out of the 500 paths within 10 seconds, which makes IDA* pretty much unusable at this 
scale, unless memory usage is very constrained. 

| | Dijkstra's | A* |
| --- | --- | --- |
| Total | 529ms | 492ms |
| Average | ~1ms | ~1ms |

[Map download](https://download.bbbike.org/osm/bbbike/Helsinki/)

### Random routes in an OSM map of Tokyo

![](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/tokyo_osm_area.png)

Again searching 500 shortest paths between random notes, now in a graph parsed from a 
24km by 30km OpenStreetMap extract of Tokyo.

Interestingly Dijkstra failed in exactly one path with the 5 second timeout, even though it performed very close to A* on average.

| | Dijkstra's | A* |
| --- | --- | --- |
| Total | 187s | 183ms |
| Average | 373ms | 365ms |

[Map download](https://download.bbbike.org/osm/bbbike/Tokyo/)

### Random routes in an OSM map of Kanto region

![](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/kanto_osm_area.png)

The area of Kanto, Japan. The largest map I could reasonably parse for testing, around 9-10 gigabytes of RAM is required. Timeout was set to 20 seconds, since some paths in this graphs can take very long to find.

Even in a graph this large the difference of the averages was small:

| | Dijkstra's | A* |
| --- | --- | --- |
| Total | 65min | 65min |
| Average | 7.79s | 7.83s |

Total times for the 500 paths were both ~65 minutes.

[Image credit and map download](https://download.geofabrik.de/asia/japan/kanto.html)

## JUnit tests 

All classes with complex functions (not storage classes) have JUnit tests for them, 
except the UI which is not very feasible to test.
All implemented data structures and algorithms are tested. The algorithm tests 
also require the data structures and graph representation classes to function 
correctly. 88% or more of coverage for all classes is achieved. 
Most missed instructions are ones rather difficult to test, mostly the timing out of 
algorithms. 

Jacoco coverage report:

![Jacoco coverage](https://raw.githubusercontent.com/Teo44/OpenRoutePlanner/master/documentation/pictures/jacoco_coverage.png)


