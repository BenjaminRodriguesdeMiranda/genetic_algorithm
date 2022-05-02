//interface outlining an individual of a population
interface Individual
{
  public abstract double[][] getItems(); //access the weights and values of an individual
  public abstract double getFitness(); //access the fitess score of an individual
  public abstract void display(); //print the details of an individual's chromosome
}
