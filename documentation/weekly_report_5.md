# Weekly report 5

## What was done

* Fixed A* and IDA* heuristic to be admissible. 

Previously the heuristic function could return higher values than the possible minimum 
distance in some borderline cases, due to using the average longitude 
to kilometers conversion, instead of the minimum, which is guaranteed 
to be admissible. 

* Improved graph memory usage by replacing a note ID hashmap with an array
(parsing a 4.7Gb OSM file now takes ~1 gigabyte less memory (8.3Gb))

* Added automated performance testing in OSM graphs

* Added couple more tests, coverage of all packages >90%

* Replaces last usages of Java data structures with own implementations.

Work hours:
