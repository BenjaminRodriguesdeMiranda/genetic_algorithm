//class to run which accepts command line arguments
public class Main
{
  public static void main(String[] args)
  {
    if(args.length == 0 || args.length == 1 && args[0].equals("1")) //no command line arguments provided or only type 1 specified
    {
      Population population = new Population(1, 100, 0.1, 10, 10, 50, 10); //use default parameters to solve napsack problem
    }
    else if(args.length == 1 && args[0].equals("2")) //only type 2 specified
    {
      Population population = new Population(2, 100, 0.1, 10, 10); //solve travaling salesman problem with default parameters
    }
    else if(args.length == 7 && args[0].equals("1")) //all arguments provided and type 1 specified
    {
      Population population = new Population(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Double.parseDouble(args[2]),
                                             Integer.parseInt(args[3]), Integer.parseInt(args[4]), Double.parseDouble(args[5]),
                                             Integer.parseInt(args[6])); //solve knapsack problem with the provided parameters.
    }
    else if(args.length == 5 && args[0].equals("2")) //all arguments provided and type 2 specified.
    {
      Population population = new Population(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Double.parseDouble(args[2]),
                                             Integer.parseInt(args[3]), Integer.parseInt(args[4])); //solve traveling salesman problem with the provided parameters.
    }
    else //incorrect input format
    {
      System.out.println("Incorrect input format. Please check README.txt"); //error message.
    }
  }
}
