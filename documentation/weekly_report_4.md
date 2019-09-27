# Weekly report 4

## What was done

* Replaced Java hashmap with own implementation
* Added automated, configurable testing of the algorithms
* OSM parser optimisation
* IDA* (kind of) functional
* UI improvements: an actual menu, options, handling of invalid inputs, printing routes
* Added configurable timing out of algorithms

## Issues

IDA* is A LOT slower than Dijkstra and A* in most cases, something has to be 
wrong with the implementation. It seems the increase of the bound slows down 
as it nears the actual shortest distance, to the point where the bound might only 
increase a couple meters at a time, when there are still a few kilometers until the 
goal node can be reached.

Recording results for performance testing is also on hold until 
IDA* is fixed.



## Plan for next week

* Fix IDA*
* Add generating different kinds of graphs for further testing
* Run and document tests in different graphs
* Research the time and space complexities of the algorithms for the documentation


Work hours: 16
