// A class for a population of individuals which can undergo an evolution simulation.
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
public class Population
{
  private static final Random RAND = new Random();
  private static int type; // 1 for knapsack problem 2 for traveling salesman problem.
  private Individual[] individuals; // Array of individuals in a population.
  private double[] fittestIndividuals; // Array of the fittest individuals from each generation
  private double mutationRate; // Chance of a mutation for an individual.
  private double limit; // Maximum weight capcity of a napsack.
  private int generations; // Number of iterations in the evolution simulation.
  private int size; // Number of individuals in a population.
  private int itemTypes; // Number of possible iterms for in a napsack.
  private int itemsPerIndividual; // Number of items to be packed or cities to be traversed
  // Constructor for Napsack individual.
  public Population(int type, int generations, double mutationRate, int size, int itemsPerIndividual, double limit, int itemTypes)
  {
    this.type = type;
    this.generations = generations;
    this.mutationRate = mutationRate;
    this.size = size;
    this.itemsPerIndividual = itemsPerIndividual;
    this.limit = limit;
    this.itemTypes = itemTypes;
    this.individuals = new Knapsack[size];
    this.fittestIndividuals = new double[this.generations];
    fillPopulationKnapsack();
    this.quickSort(0, size-1);
    this.simulate();
  }
  // Constructor for Salesman individual.
  public Population(int type, int generations, double mutationRate, int size, int itemsPerIndividual)
  {
    this.type = type;
    this.generations = generations;
    this.mutationRate = mutationRate;
    this.size = size;
    this.itemsPerIndividual = itemsPerIndividual;
    this.individuals = new Salesman[size];
    this.fittestIndividuals = new double[this.generations];
    fillPopulationTSP();
    this.quickSort(0, size-1);
    this.simulate();
  }
  // Fill the initial population with potential napsack problem solutions.
  private void fillPopulationKnapsack()
  {
    System.out.println("original population");
    for(int i = 0; i < this.size; i++)
    {
      Individual knapsackIndividual = new Knapsack(this.itemsPerIndividual, this.limit, this.itemTypes);
      this.individuals[i] = knapsackIndividual;
    }
    this.quickSort(0, this.size-1);
    for(int i = 0; i < this.size; i++)
    {
      this.individuals[i].display();
    }
    System.out.println();
  }
  // Fill the initial population with potential traveling salesman problem solutions.
  private void fillPopulationTSP()
  {
    System.out.println("original population");
    for(int i = 0; i < this.size; i++)
    {
      Individual salesmanIndividual = new Salesman(this.itemsPerIndividual);
      this.individuals[i] = salesmanIndividual;
      this.individuals[i].display();
    }
    this.quickSort(0, this.size-1);
    for(int i = 0; i < this.size; i++)
    {
      this.individuals[i].display();
    }
    System.out.println();
  }
  // Simulate evolution.
  private void simulate()
  {
    for(int i = 0; i < this.generations; i++)
    {
      this.mutate();
      this.quickSort(0, this.size-1);
      this.fittestIndividuals[i] = this.individuals[size-1].getFitness();
      this.crossover();
      System.out.println("generation: "+(i+1));
      for(int j = 0; j < size; j++)
      {
        this.individuals[j].display();
      }
      System.out.println();
      System.out.println();
    }
  }
  // Mutate random chromosomes.
  private void mutate()
  {
    if(type == 1) // Swap a random element of a chromosome with a random element from a dictionary of all possible items.
    {
      for(int i = 0; i < this.size; i++)
      {
        if(RAND.nextDouble() < this.mutationRate)
        {
          int chromosomeIndex = RAND.nextInt(this.itemsPerIndividual);
          int dictionaryIndex = RAND.nextInt(this.itemTypes);
          Knapsack temp = new Knapsack(this.itemsPerIndividual, this.limit, this.itemTypes);
          this.individuals[i].getItems()[chromosomeIndex] = temp.getDictionary()[dictionaryIndex];
        }
      }
    }
    else if(type == 2) // Swap the positions of 2 genes in a chromosome.
    {
      for(int i = 0; i < individuals.length; i++)
      {
        if(RAND.nextDouble() < this.mutationRate)
        {
          int chromosomeIndex1 = RAND.nextInt(this.itemsPerIndividual);
          int chromosomeIndex2 = RAND.nextInt(this.itemsPerIndividual);
          double[] temp = this.individuals[i].getItems()[chromosomeIndex1];
          this.individuals[i].getItems()[chromosomeIndex1] = this.individuals[i].getItems()[chromosomeIndex2];
          this.individuals[i].getItems()[chromosomeIndex2] = temp;
        }
      }
    }
  }
  // Combine two individuals and replace a weak individuals with the results.
  private void crossover()
  {
    List<Integer> selectedIndexes = new ArrayList<Integer>();
    double chanceDifference = 100/this.size; // Percentage based.
    double selectionChance = chanceDifference;
    for(int i = 0; i < this.size; i++) // Increase the chance of being chosen for selection as the individuals array index increases because the list is sorted from least fit to most fit.
    {
      if(RAND.nextDouble()*100 < selectionChance)
      {
        selectedIndexes.add(i);
      }
      selectionChance += chanceDifference;
    }
    if(selectedIndexes.size() % 2 != 0) // Ensure that there are an even number of parents.
    {
      selectedIndexes.remove(0);
    }
    Individual[] offspring = new Individual[selectedIndexes.size()/2];
    int offspringIndex = 0;
    while(!selectedIndexes.isEmpty()) // Generate a list of new chromosomes.
    {
      Individual parent1 = this.individuals[selectedIndexes.get(0)];
      int parent2Index = RAND.nextInt(selectedIndexes.size()-1)+1;
      Individual parent2 = this.individuals[selectedIndexes.get(parent2Index)];
      offspring[offspringIndex] = breed(parent1.getItems(), parent2.getItems());
      offspringIndex++;
      selectedIndexes.remove(parent2Index);
      selectedIndexes.remove(0);
    }
    for(int i = 0; i < offspring.length; i++) // Replace the least fit individuals with the new individuals.
    {
      this.individuals[i] = offspring[i];
    }
  }
  // Create an an child from two parents.
  private Individual breed(double[][] chromosome1, double[][] chromosome2)
  {
    double[][] items = new double[chromosome1.length][chromosome1[0].length];
    if(type == 1) // Select a gene from parent 1 or parent 2 with an equal chance.
    {
      for(int i = 0; i < chromosome1.length; i++)
      {
        if(RAND.nextDouble() < 0.5)
        {
          items[i][0] = chromosome1[i][0];
          items[i][1] = chromosome1[i][1];
        }
        else
        {
          items[i][0] = chromosome2[i][0];
          items[i][1] = chromosome2[i][1];
        }
      }
      return new Knapsack(items, this.limit, this.itemTypes);
    }
    else // Search for the index of the each city in each parent starting at 1 and restart if the result is not a permutation.
    {
      for(int i = 0; i < this.itemsPerIndividual; i++)
      {
        int occurence1 = 0;
        int occurence2 = 0;
        for(int j = 1; j < chromosome1.length; j++)
        {
          if(i+1 == (int)chromosome1[j][0])
          {
            occurence1 = j;
          }
          if(i+1 == (int)chromosome2[j][0])
          {
            occurence2 = j;
          }
        }
        double decider = RAND.nextDouble();
        if((int)items[occurence1][0] == 0 && (int)items[occurence2][0] != 0)
        {
          items[occurence1][0] = chromosome1[occurence1][0];
          items[occurence1][1] = chromosome1[occurence1][1];
        }
        else if((int)items[occurence1][0] != 0 && (int)items[occurence2][0] == 0)
        {
          items[occurence2][0] = chromosome2[occurence2][0];
          items[occurence2][1] = chromosome2[occurence2][1];
        }
        else if((int)items[occurence1][0] == 0 && (int)items[occurence2][0] == 0 && decider < 0.5)
        {
          items[occurence1][0] = chromosome1[occurence1][0];
          items[occurence1][1] = chromosome1[occurence1][1];
        }
        else if((int)items[occurence1][0] == 0 && (int)items[occurence2][0] == 0 && decider >= 0.5)
        {
          items[occurence2][0] = chromosome2[occurence2][0];
          items[occurence2][1] = chromosome2[occurence2][1];
        }
        else // Result is not a permutation resetting and trying again.
        {
          for(int j = 0; j < this.itemsPerIndividual; j++)
          {
            items[j][0] = 0;
            items[j][1] = 0;
          }
          i = -1;
        }
      }
      return new Salesman(items);
    }
  }
  private void quickSort(int left, int right) // A quicksort implementation which does not need to create a new array each time.
  {
    int length = right-left; //check this
    if(length > 1)
    {
      //int index = length;
      int index = RAND.nextInt(length);
      double pivot = this.individuals[index].getFitness();
      while(left<=right)
      {
        while(this.individuals[left].getFitness() < pivot)
        {
          left = left+1;
        }
        while(this.individuals[right].getFitness() > pivot) //double check to make sure this does not go past index
        {
          right = right-1;
        }
        if(left <= right)
        {
          Individual temp = this.individuals[left];
          this.individuals[left] = this.individuals[right];
          this.individuals[right] = temp;
          left = left+1;
          right = right-1;
          quickSort(0, right);
          quickSort(left, length);
        }
      }
    }
  }
  // Getters
  public double[] getFittestIndividuals()
  {
    return this.fittestIndividuals;
  }
}
