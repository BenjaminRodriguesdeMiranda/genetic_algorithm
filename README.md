# genetic_algorithm
The knapsack problem and a modification of the travelling salesman problem tackled with a genetic algorithm.

- Compile and run Main.java to run a simulation with default parameters.
- The parameters can be altered.
  - Add a type parameter by adding either 1 for the knapsack problem or 2 for the traveling salesman problem on the command line. 
	  For example: "java Main 1" or "java main 2".
  - Choose all parameters for the knapsack simulation by adding type (1 to represent a napsack problem), number of generations (an 
	  integer representing how many iterations the simulation will go through), mutation rate (a double between 0 and 1), population 
	  size (an integer), number of items per individual (an integer representing the number of items to be packed into a napsack), a 
	  limit (an double representing the maximum weight allowed in a knapsack), and the number of types of items (an integer 
	  representing the number of items which can be chosen from).
	  For example: "java Main 1 <integer> <integer> <double> <integer> <integer> <double> <integer>".
  - Choose all parameters for the traveling salesman problem by adding type (2 to represent a traveling salesman problem), number 
	  of generations (an integer representing how many iterations the simulation will go though), mutation rate (a double between 0 
	  and 1), population size (an integer), and the number of items per individual (an integer representing the number of cities which 
    will be traversed).
	  For example: "java Main 1 <integer> <integer> <double> <integer> <integer> <double> <integer>".
- Default parameters are: 1, 100, 0.1, 10, 10, 50, 10 for knapsack and 1, 100, 0.1, 10, 10 for travelling salesman.
- The output shows information about each individual in the population for each generation. 
- Due to the nature of genetic algorithms, the simluation takes a few seconds to run with default parameters. If the parameters are
  altered then the run time may be longer. 
