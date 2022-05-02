// A class for an individual of type knapsack.
import java.util.Random;
import java.lang.Math;
public class Knapsack implements Individual
{
  private static final Random RAND = new Random();
  private double[][] dictionary; // All possible items for in a napsack.
  private double[][] items; // All items actually in a napsack reprented by a weight and a unique value.
  private double limit; // The weight limit for a knapsack.
  private double weight; // Total weight of all items in a napsack.
  private double value; // Total value of all items in a napsack.
  private double fitness; // Firness of a napsack individual.
  private int itemTypes; // Number of types possible items.
  private int itemsPerIndividual; // Number of items to be fit into a napsack.
  // Constructor for a Napsack for before the evolution simulation.
  public Knapsack(int itemsPerIndividual, double limit, int itemTypes)
  {
    this.limit = limit;
    this.itemTypes = itemTypes;
    this.itemsPerIndividual = itemsPerIndividual;
    this.dictionary = generateDictionary();
    this.items = generateItems();
    this.weight = calculateWeight();
    this.value = calculateValue();
    this.fitness = calculateFitness();
  }
  // Constructor for a Napsack for during the evolution simulation.
  public Knapsack(double[][] items, double limit, int itemTypes)
  {
    this.limit = limit;
    this.itemTypes = itemTypes;
    this.itemsPerIndividual = items.length;
    this.dictionary = generateDictionary();
    this.items = items;
    this.weight = calculateWeight();
    this.value = calculateValue();
    this.fitness = calculateFitness();
  }
  // Create a dictionary of items.
  private double[][] generateDictionary()
  {
    double[][] dictionary = new double[this.itemTypes][2];
    for(int i = 0; i < this.itemTypes; i++)
    {
      int weightRange = 10; // Item weights can be up to 10
      double minWeight = 0.01; // Item weights must be at least 0.1
      dictionary[i][0] = RAND.nextDouble()*weightRange+minWeight;
      dictionary[i][1] = i+1;
    }
    return dictionary;
  }
  // Generate a random solution for the initial population.
  private double[][] generateItems()
  {
    double[][] items = new double[this.itemsPerIndividual][2];
    int RandomItemIndex;
    for(int i = 0; i < this.itemsPerIndividual; i++)
    {
      RandomItemIndex = RAND.nextInt(this.itemTypes);
      items[i][0] = this.dictionary[RandomItemIndex][0];
      items[i][1] = this.dictionary[RandomItemIndex][1];
    }
    return items;
  }
  // Calculate total weight of items for in a napsack.
  private double calculateWeight()
  {
    double total = 0;
    for(int i = 0; i < this.items.length; i++)
    {
      total = total+items[i][0];
    }
    return total;
  }
  // Calculate total value of items for in a napsack.
  private double calculateValue()
  {
    double total = 0;
    for(int i = 0; i < this.items.length; i++)
    {
      total = total+this.items[i][1];
    }
    return total;
  }
  // Calculate the fitness of an individual solution.
  private double calculateFitness()
  {
    double fitness = this.value; // Ensuring that the fitness cannot be below 0. The use of this.itemTypes is because the value increments 1 for each new type so the number of item types is the same as the most valuable item.
    if(this.weight > this.limit)
    {
      int deductionFactor = 2;
      fitness -= 2*(this.weight-this.limit); // Multiplied by a deduction factor because being too heavy for the napsack is worse than being too light.
      fitness -= 1; // An additional point subtracted to ensure that when the items are just a fraction overweight the fitness still decreases.
    }
    else if(this.weight < this.limit)
    {
      fitness -= (this.limit-this.weight);
    }
    return fitness;
  }
  // Find the heaviest item
  private double findHeaviestItem()
  {
    double max = this.items[0][0];
    for(int i = 0; i < itemsPerIndividual; i++)
    {
      if(this.items[i][0] > max)
      {
        max = this.items[i][0];
      }
    }
    return max;
  }
  // Getters
  public double[][] getItems()
  {
    return this.items;
  }
  public double getFitness()
  {
    return this.fitness;
  }
  public double[][] getDictionary()
  {
    return this.dictionary;
  }
  // Display information about a potential solution.
  public void display()
  {
    for(int i = 0; i < this.items.length; i++)
    {
      System.out.print("item weight: "+this.items[i][0]+"   ");
      System.out.println("item value: "+this.items[i][1]);
    }
    System.out.println("total weight: "+this.weight);
    System.out.println("total value: "+this.value);
    System.out.println("fitness: "+this.fitness);
    System.out.println();
  }
}
