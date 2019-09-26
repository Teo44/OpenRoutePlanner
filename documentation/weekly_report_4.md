# Weekly report 4

## What was done

* Replaced Java hashmap with own implementation
* Added automated, configurable testing of the algorithms
* OSM parser optimisation
* IDA* (kind of) functional
* UI improvements: an actual menu, options, handling of invalid inputs, printing routes
* Added configurable timing out of algorithms

## Issues

IDA* is A LOT slower than Dijkstra and A* in most cases, something 
wrong with the implementation. It seems the bound starts increasing slower 
as it nears the actual shortest distance, 

Recording results for performance testing is also on hold until 
IDA* is fixed.

## Plan for next week

* Figure out IDA*
* Random generation of different kinds of graphs for further testing


Work hours: 13
