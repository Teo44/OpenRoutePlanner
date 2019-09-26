# OpenRoutePlanner

Finding the shortest routes using open map data and algorithms. A university course project.

## Documentation

* [Specifications](https://github.com/Teo44/OpenRoutePlanner/blob/master/documentation/specifications.md)

## Weekly reports

* [1. week](https://github.com/Teo44/OpenRoutePlanner/blob/master/documentation/weekly_report_1.md)

* [2. week](https://github.com/Teo44/OpenRoutePlanner/blob/master/documentation/weekly_report_2.md)

* [3. week](https://github.com/Teo44/OpenRoutePlanner/blob/master/documentation/weekly_report_3.md)

* [4. week](https://github.com/Teo44/OpenRoutePlanner/blob/master/documentation/weekly_report_4.md)

## Usage 

OpenRoutePlanner can find shortest paths in graphs parsed from OpenStreetMap data or generated randomly for testing. The start and goal nodes can be specified with their OSM IDs. The ID of a node in a specific location can be found from the map on openstreetmap.org by enabling map data (Layers -> check Map data). The nodes of randomly generated graphs are numbered 0 to n-1 when there are n nodes.

Included in the repository is a small OSM file covering the Kumpula Campus area, and more extracts can be downloaded from [many](https://download.bbbike.org/osm/bbbike/) sites. Note that multiple gigabyte files can require a lot of ram (~10Gb+) to parse.
