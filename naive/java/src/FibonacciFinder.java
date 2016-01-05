/**
 * A program to naively identify the nth Fibonacci number, with the option for finding it via iterative approaches, or
 * recursive approaches
 */
public class FibonacciFinder
{
    /**
     * The main driver class showing the high-level path of the program, validating inputs and calling the correct
     * drivers.
     * @param args Of the form recursive|iterative n, where n is an integer value
     */
    public static void main(String[] args)
    {
        try
        {
            validateInput(args);
            findFibonacciDriver(args);
        }
        catch (InvalidInputException e)
        {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    } // End of the main method

    /**
     * Validates the commandline inputs for sane inputs
     * @param args Of the form recursive|iterative n, where n is an integer value
     * @throws InvalidInputException when the commandline arguments are not valid
     */
    private static void validateInput(String[] args) throws InvalidInputException
    {
        if (args.length != 2)
            throw new InvalidInputException("Error: invalid command syntax, syntax is of form: " +
                    "recursive|iterative n, where n is a non-negative integer value");

        if ((!args[0].equals("iterative")) && (!args[0].equals("recursive")))
            throw new InvalidInputException("Error: input mode should be either `iterative` or `recursive`");

        try
        {
            long index = Integer.parseInt(args[1]);
            if (index < 0)
                throw new InvalidInputException("Error: input number should not be negative");
        }
        catch (NumberFormatException e)
        {throw new InvalidInputException("Error: input number should be an integer");}
    } // End of the validate input method

    private static void findFibonacciDriver(String[] args) throws InvalidInputException
    {
        long index = Long.parseLong(args[1]);
        System.out.printf("The %d%s fibonacci number is: ", index, getIndexSuffix(index));
        long value; // The nth fibonacci number calculated
        switch(args[0])
        {
            case "iterative":
                value = findFibonacciNumberIterative(index);
                break;
            case "recursive":
                value = findFibonacciNumberRecursiveDriver(index);
                break;
            default:
                // Will not occur due to check from validateInput(), but kept to keep compiler happy
                throw new InvalidInputException("Error: input mode should be either `iterative` or `recursive`");
        }
        System.out.println(Long.toString(value));
    } // End of the find fibonacci driver method

    /**
     * The actual implementation of the naive find fibonacci number in an iterative fashion method
     * @param index The index of the fibonacci sequence we are trying to find
     * @return Returns the value of the nth fibonacci number, where n is the index number given
     */
    private static long findFibonacciNumberIterative(long index)
    {
        if (index == 0)
            return 0;
        else if (index == 1)
            return 1;

        long firstValue = 0;
        long secondValue = 1;
        long currentValue = firstValue + secondValue;
        for (long i = 2; i < index; ++i)
        {
            firstValue = secondValue;
            secondValue = currentValue;
            currentValue = firstValue + secondValue;
        }
        return currentValue;
    } // End of the find fibonacci number iterative method

    /**
     * The driver for the naive find fibonacci number in a recursive fashion
     * @param index The index of the fibonacci sequence we are trying to find
     * @return Returns the value of the nth fibonacci number, where n is the index number given
     */
    private static long findFibonacciNumberRecursiveDriver(long index)
    {
        if (index == 0)
            return 0;
        else if (index == 1)
            return 1;
        return findFibonacciNumberRecursive(index, 2, 0 , 1);
    } // End of the find fibonacci number recursive driver method

    /**
     * The actual implementation of the naive find fibonacci number in a recursive fashion.
     * NOTE: In this implementation, we avoid the issue of the doubling issue:
     * @param index The index of the fibonacci sequence we are trying to find
     * @param current The current index that is being calculated
     * @param firstValue The first value (can be thought of as the value 2 indices lower from the current index)
     * @param secondValue The second value (can be thought of as the value an index lower from the current index)
     * @return Returns the value of the nth fibonacci number, where n is the index number given
     */
    private static long findFibonacciNumberRecursive(long index, long current, long firstValue, long secondValue)
    {
        if (index == current)
            return firstValue + secondValue;
        else
        {
            ++current;
            long newSecondValue = firstValue + secondValue;
            return findFibonacciNumberRecursive(index, current, secondValue, newSecondValue);
        }
    } // End of the find fibonacci number recursive method

    /**
     * [Helper Method] Gives the suffix of a number for prettier formatting
     * @param index The number that needs to be printed out
     * @return Returns the suffix of the number in a String
     */
    private static String getIndexSuffix(long index)
    {
        long lastDigit = index % 10;
        switch ((int) lastDigit)
        {
            case 0:
                return "th";
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    } // End of the get index suffix method

    // Private exception handling
    private static class InvalidInputException extends Throwable {public InvalidInputException(String s) {super(s);}}
} // End of the Fibonacci finder class
