Harshitha Komatineni
1001968082

Programming language: Java V8

Code Structure:
I implemented the uninformed and informed graph searches using A* Search Algorithms with heuristic files to determine the optimal route from the routes given in the input.

Classes : 

InputPath.java	:Model class representing all routes available from input file.
City.java		:City model class includes city details, parent details, and child details of each city
CityComparator.java:In this class, the cities in fringe are sorted by cost using the Comparator interface.
find_route.java	:In this class there is actual logic to process the input, which is outlined in the following steps.

	*Parses the input and determines start node and goal bode
	*Starts traversing from the start node by adding it into fringe
	*For each traversal of the city add its parent and child details using the function getChildCities.
	*Traversing will be done by popping the city from fringe until the following conditions are met
		*If the traversing city = goal node  ->Reached goal node so route can be printed
		*If there are no cities in fringe.   ->No route available to goal city
           	*displayRoute function is used to print when goal node is reached.
	
Instructions to run the code:

Once the files (InputPath.java, City.java, CityComparator.java, find_route.java) have been added to Omega machine, compile the code by running javac find_route.java.
or the code can be run on the terminal with the an installed jdk.

Having successfully compiled the code, run the following code to calculate the optimal route between the source and destination:
For Uninformed search : java find_route inputfile source destination 
For informed search     : java find_route inputfile source destination heuristicfile
			