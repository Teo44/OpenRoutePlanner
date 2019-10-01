# Weekly report 5

## What was done

* Fixed A* and IDA* heuristic to always be admissible. 

Previously the heuristic function could return higher values than the possible minimum 
distance in some borderline cases, due to using the average longitude 
to kilometers conversion, instead of the minimum, which is guaranteed 
to be admissible. 

* Improved graph memory usage by replacing a note ID hashmap with an array

Work hours:
