# Weekly report 2

## What was done

* OSM XML file parsing to graphs
* Basic Dijkstra's algorithm using Java data structures
* Started working on A*, currently little broken and slower 
than Dijkstra due to slow heuristic calculations
* Text UI, user can define the file to parse, the type of ways 
to include and calculate shortest distance and route between nodes.
* Unit testing for the parser and Dijkstra

## Issues

Jacoco keeps giving "Skipping JaCoCo execution due to missing execution data file." 
error, can't figure it out.

A* seems to only be faster than Dijkstra on the first run, then presumably 
some optimisations or caching make Dijkstra faster. Either A* needs more 
optimisation or the graph needs to be larger. 

The XML-parser is too inefficient to parse larger files, somewhere between 
500Mb and 4.7Gb the parsing will fail due to Java garbage collection taking 
up most of the CPU time. Haven't managed to fix this yet, might have to 
redo the parsing differently.

## Plan for next week
* Fix and finish A*
* Start working on IDA*
* Start replacing Java's datastructures with own implementations
* General cleanup of the code
* Betterments to the UI?

Work hours: 11