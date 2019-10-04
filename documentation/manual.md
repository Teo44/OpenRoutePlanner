# OpenRoutePlanner manual

## Pathfinding

### Parsing or generating to graph.

OpenRoutePlanner can parse OpenStreetMap's XML files into pathfindable
graphs:

* Place the XML file in the same directory as the ORP jar-file, or in a 
sub-directory below it. 
* Press __G__ to enter the graph adding menu, and __O__ to parse a file.
* Choose the tags by which OSM ways will be approved. The default highway 
option usually works well. 
* Enter the filepath of the file, parsing will begin. Large files can take 
minutes to parse, depending largely on the available processing power. A lot 
of ram could also be needed, as the graph representation usually needs ~2 times 
as much memory as is the size of the OSM-file. The test_files folder includes 
an OSM map of the Kumpula area, named Kumpula.osm.

Random graphs can also be generated for testing purposes:
* Enter the graph adding menu with __G__, and choose random graph with __R__
* Choose the type of graph to be generated. Type 1 graph places *n* nodes in random 
coordinates within specified bounds, and adds *b* arcs from each node to a random one.
Type 2 graphs places *x\*y* nodes at equal distance in a grid, and adds *m* ways 
leading through the graph. The nodes 0 to x-1 are always connected to ensure that 
some path can be found even in sparse graphs.


### Finding a path beween nodes

The node IDs in OSM maps can be aquired from openstreetmap.org, by enabling 
map data in Layers -> Map Data. Now the ways will be highlighted in blue when 
zoomed close enough, and clicking a way displays the IDs of it's nodes.

* Enter the pathfinding menu with __P__
* Optionally disable or enable some algorithms with __A__. All three are used 
by default.
* Optionally change the time before an algorithm is timed out with __T__.
Very large graphs can require more than the default five seconds for any of the 
algorithms to find a shortest path.
* Enter the start and end node by pressing __P__ again and entering their IDs.
When using a randomly generated graph, the nodes are numbered from 0 to n-1.
* If a path was found, optionally print out the path with __S__.


### Using the automated performance testing.

OpenRoutePlanner has automated performance testing, which can be used to find 
multiple random paths in multiple random graphs or a single OSM map, and track 
the average and total time taken by each algorithm.