// A class for an individual of type Salesman.
import java.util.Random;
import java.lang.Math;
import java.util.List;
import java.util.ArrayList;
public class Salesman implements Individual
{
  private final static Random rand = new Random();
  private final static double radius = generateRadius(); // The radius of the circle on which the cities lie.
  private double[][] items; // Cities which are represented by a number and an angle on the circle.
  private double fitness; // The fitness of a solution which is an inverted version of the distance.
  private int itemsPerIndividual; // Number of cities in a problem.
  // Constructor for a alesman for before the evolution simulation.
  public Salesman(int itemsPerIndividual)
  {
    this.itemsPerIndividual = itemsPerIndividual;
    this.items = generateItems();
    this.fitness = calculateFitness();
  }
  // Constructor for a Salesman for during the evolution simulation.
  public Salesman(double[][] items)
  {
    this.items = items;
    this.itemsPerIndividual = items.length;
    this.fitness = calculateFitness();
  }
  // Randomly generate a radius between 1000000km and 10000000km.
  private static double generateRadius()
  {
    int minRadius = 1000000;
    int maxRadius = 10000000;
    double radius = rand.nextDouble()*(maxRadius-minRadius)+minRadius;
    System.out.println("radius: "+radius);
    System.out.println();
    return radius;
  }
  // Generate a random solution for the initial population.
  private double[][] generateItems()
  {
    double[][] items = new double[this.itemsPerIndividual][2]; // Array of cities on the circle in order.
    for(int i = 0; i < this.itemsPerIndividual; i++)
    {
      double angle = i*(2*Math.PI/this.itemsPerIndividual);
      items[i][0] = i+1;
      items[i][1] = angle;
    }
    List<Integer> indexes = new ArrayList<Integer>();
    for(int i = 0; i < items.length; i++)
    {
      indexes.add(i);
    }
    double[][] randomizedItems = new double[this.itemsPerIndividual][2]; // Array of cities on the circle in a random order.
    int counter = 0;
    while(indexes.size() > 0)
    {
      int index = rand.nextInt(indexes.size());
      randomizedItems[counter][0] = items[indexes.get(index)][0];
      randomizedItems[counter][1] = items[indexes.get(index)][1];
      counter++;
      indexes.remove(index);
    }
    return randomizedItems;
  }
  // Calculate fitness as an inverted version of total distance traveled such that a higher fitess corresponds to a lower total distance traveled.
  private double calculateFitness()
  {
    double distanceTraveled = 0;
    for(int i = 0; i < this.itemsPerIndividual-1; i++)
    {
      distanceTraveled += 2*radius*Math.sin(Math.abs(this.items[i][1]-this.items[i+1][1])/(2*radius)); // Adding distance between cities based on a the formula for the length of a chord.
    }
    double worstCase; // A number which is larger than anything which could be subtracted from it in the fitness calculation to ensure that the fitness is posative. This value is larger than 2 * the largest possible total distance.
    if(this.itemsPerIndividual%4 == 0)
    {
      worstCase = (this.itemsPerIndividual)*(2*radius);
    }
    else
    {
      worstCase = ((this.itemsPerIndividual+1)/2)*(2*radius);
    }
    int reductionFactor = 100000;
    double fitness = worstCase-reductionFactor*distanceTraveled; // Multiplied by a deduction factor to heavily discourage traveling extra distance.
    return fitness;
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
  // Display information about a solution.
  public void display()
  {
    for(int i = 0; i < this.items.length; i++)
    {
      System.out.print("city number: "+this.items[i][0]+"   ");
      System.out.println("city angle: "+this.items[i][1]);
    }
    System.out.println("fitness: "+this.fitness);
    System.out.println();
  }
}
