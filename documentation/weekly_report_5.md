# Weekly report 5

## What was done

* Fixed A* and IDA* heuristic to be admissible, previously it could overestimate
distances in large graphs.
* Improved graph memory usage by replacing a note ID hashmap with an array
(parsing a 4.7Gb OSM file now takes ~1 gigabyte less memory (8.3Gb))
* Added automated performance testing in OSM graphs and grid type random graphs
* Added couple more tests, coverage of all packages >90%
* Replaced last usages of Java data structures with own implementations.
* Added javadoc for the UI-class
* Working IDA* (finally)
* Continued the implementation documentation
* Started performance testing
* Started writing the manual

## Next week's plan

* More testing
* Implement measuring of algorithms memory usage.

Work hours: 12
