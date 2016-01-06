import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * A program to naively identify the nth Fibonacci number, with the option for finding it via iterative approaches, or
 * recursive approaches
 */
public class FibonacciFinder
{
    // Global flags
    private static boolean isTestable = false;
    private static String MAX_TEST_FILE_INDEX = "1001"; // Replace with the maximum value in your test file

    /**
     * The main driver class showing the high-level path of the program, validating inputs and calling the correct
     * drivers.
     * @param args Of the form recursive|iterative n, where n is an integer value
     */
    public static void main(String[] args)
    {
        try
        {
            int nIndex = validateInput(args);
            findFibonacciDriver(args, nIndex);
        }
        catch (InvalidInputException e)
        {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    } // End of the main method

    /**
     * Validates the commandline inputs for sane inputs
     * @param args Of the form `all n` OR `naive|dynamic recursive|iterative|direct n`, where n is a non-negative integer
     * @return Returns the position of where in the args array the `n` value is
     * @throws InvalidInputException When the commandline arguments are not valid
     */
    private static int validateInput(String[] args) throws InvalidInputException
    {
        if ((args.length != 3) && (args.length != 2))
            throw new InvalidInputException("Error: invalid command syntax, syntax is of form: " +
                    "`naive|dynamic recursive|iterative|direct n` OR `all n` OR `all n file_path_to_test_numbers`," +
                    " where n is a non-negative integer value");

        if ((!args[0].equals("naive")) && (!args[0].equals("dynamic")) && (!args[0].equals("all")))
            throw new InvalidInputException("Error: input mode should be either `naive` or `dynamic` or `all`");

        try
        {
            if ((args[0].equals("all")) && (args.length == 3))
            {
                isTestable = true;
                File testFile = new File(args[2]);
                if (!testFile.isFile())
                    throw new InvalidInputException("Error: Test file_path given is not a valid test file," +
                            " or does not have read permissions");
            }

            // Figures out which index in args is the `n` number
            int nIndex = 1;
            if ((args[0].equals("naive")) || (args[0].equals("dynamic")))
                nIndex = 2;

            BigInteger index = new BigInteger(args[nIndex]);
            if (index.compareTo(BigInteger.ZERO) == -1)
                throw new InvalidInputException("Error: input number should not be negative");
            return nIndex;
        }
        catch (NumberFormatException e)
        {throw new InvalidInputException("Error: input number should be an integer");}
    } // End of the validate input method

    /**
     * The driver to run the correct version of the generate fibonacci number
     * @param args Of the form `all n` OR `naive|dynamic recursive|iterative|direct n`, where n is a non-negative integer
     * @param nIndex The position of where in the args array the `n` value is
     * @throws InvalidInputException When the commandline arguments are not valid
     */
    private static void findFibonacciDriver(String[] args, int nIndex) throws InvalidInputException
    {
        long naiveIndex = Long.parseLong(args[nIndex]);
        long naiveValue; // The nth fibonacci number calculated (naively)

        BigInteger index = new BigInteger(args[nIndex]);
        BigInteger value; // The nth fibonacci number calculated (dynamically)

        switch(args[0])
        {
            case "naive":
            {
                System.out.printf("The %d%s fibonacci number is: ", index, getIndexSuffix(index));
                switch(args[1])
                {
                    case "iterative":
                        naiveValue = findFibonacciNumberNaiveIterative(naiveIndex);
                        break;
                    case "recursive":
                        naiveValue = findFibonacciNumberNaiveRecursiveDriver(naiveIndex);
                        break;
                    case "direct":
                        naiveValue = findFibonacciNumberNaiveDirect(naiveIndex);
                        break;
                    default:
                        // Will not occur due to check from validateInput(), but kept to keep compiler happy
                        throw new InvalidInputException("Error: input mode should be either `iterative` or `recursive`");
                }
                System.out.println(naiveValue);
            } // End of the naive case
            case "dynamic":
            {
                System.out.printf("The %d%s fibonacci number is: ", index, getIndexSuffix(index));
                switch(args[1])
                {
                    case "iterative":
                        value = findFibonacciNumberDynamicIterative(index);
                        break;
                    case "recursive":
                        value = findFibonacciNumberDynamicRecursiveDriver(index);
                        break;
                    case "direct":
                        value = findFibonacciNumberDynamicDirect(index);
                        break;
                    default:
                        // Will not occur due to check from validateInput(), but kept to keep compiler happy
                        throw new InvalidInputException("Error: input mode should be either `iterative` or `recursive`");
                }
                System.out.println(value);
            } // End of the dynamic case
            case "all":
            {
                String filePath = "";

                if (args.length == 3)
                    filePath = args[2];
                benchmarkAllGeneratorsDriver(filePath, index);
                break;
            } // End of the direct case
            default:
            {
                // Will not occur due to check from validateInput(), but kept to keep compiler happy
                throw new InvalidInputException("Error: input mode should be either `iterative` or `recursive`");
            } // End of the default case
        } // End of the main switch driver
    } // End of the main driver method

    /************************* START OF NAIVE FIBONACCI NUMBER GENERATORS *************************/

    /**
     * The naive approach to generate the `n`th fibonacci number in an iterative fashion
     * @param index The index of the fibonacci sequence we are trying to find
     * @return Returns the value of the `n`th fibonacci number, where `n` is the index number given
     */
    private static long findFibonacciNumberNaiveIterative(long index)
    {
        if (index == 0L)
            return 0L;
        else if (index == 1L)
            return 1L;

        long firstValue = 0L;
        long secondValue = 1L;
        long currentValue = firstValue + secondValue;
        for (long i = 2L; i < index; ++i)
        {
            firstValue = secondValue;
            secondValue = currentValue;
            currentValue = firstValue + secondValue;
        }
        return currentValue;
    } // End of the findFibonacciNumberNaiveIterative method

    /**
     * The naive approach to generate the `n`th fibonacci number in a recursive fashion. Driver method for the actual
     * generator method
     * @param index The index of the fibonacci sequence we are trying to find
     * @return Returns the value of the `n`th fibonacci number, where `n` is the index number given
     */
    private static long findFibonacciNumberNaiveRecursiveDriver(long index)
    {
        if (index == 0L)
            return 0L;
        else if (index == 1L)
            return 1L;
        return findFibonacciNumberNaiveRecursive(index, 2L, 0L, 1L);
    } // End of the findFibonacciNumberNaiveRecursiveDriver method

    /**
     * The naive approach to generate the `n`th fibonacci number in a recursive fashion.
     * NOTE: In this implementation, we avoid the issue of the doubling issue due to argument passing
     * @param index The index of the fibonacci sequence we are trying to find
     * @param current The current index that is being calculated
     * @param firstValue The first value (can be thought of as the value 2 indices lower from the current index)
     * @param secondValue The second value (can be thought of as the value an index lower from the current index)
     * @return Returns the value of the `n`th fibonacci number, where `n` is the index number given
     */
    private static long findFibonacciNumberNaiveRecursive(long index, long current, long firstValue, long secondValue)
    {
        if (index == current)
            return firstValue + secondValue;
        else
        {
            ++current;
            long newSecondValue = firstValue + secondValue;
            return findFibonacciNumberNaiveRecursive(index, current, secondValue, newSecondValue);
        }
    } // End of the findFibonacciNumberNaiveRecursive method

    /**
     * The naive approach to generate the `n`th fibonacci number in a direct fashion with
     * Binet's Fibonacci Number Formula
     * @param index The index of the fibonacci sequence we are trying to find
     * @return Returns the value of the `n`th fibonacci number, where `n` is the index number given
     */
    private static long findFibonacciNumberNaiveDirect(long index)
    {
        double phi = (1d + Math.sqrt(5d))/(2d);
        double value = (Math.pow(phi, index) - (Math.pow(-phi, -index)))/(Math.sqrt(5d));
        return (long) value;
    } // End of the findFibonacciNumberNaiveDirect method

    /************************* END OF NAIVE FIBONACCI NUMBER GENERATORS *************************/



    /************************* START OF DYNAMIC FIBONACCI NUMBER GENERATORS *************************/

    /**
     * An implementation of a dynamic programming approach to solving the fibonacci number generation problem,
     * utilises BigIntegers in order to go above the limit of (2^63) - 1 given by long. Implemented with memoization
     * for speed increases lost due to BigInteger's immutability property.
     * @param index The index of the fibonacci sequence we are trying to find
     * @return Returns the value of the `n`th fibonacci number, where `n` is the index number given
     */
    private static BigInteger findFibonacciNumberDynamicIterative(BigInteger index)
    {
        BigInteger[] memoisedArray = new BigInteger[index.intValue() + 1];

        if (index.equals(BigInteger.ZERO))
            return BigInteger.ZERO;
        else if (index.equals(BigInteger.ONE))
            return BigInteger.ONE;

        memoisedArray[0] = BigInteger.ZERO;
        memoisedArray[1] = BigInteger.ONE;

        for (BigInteger i = new BigInteger("2"); (i.compareTo(index) == -1) || (i.compareTo(index) == 0);
             i = i.add(BigInteger.ONE))
        {
            BigInteger currentValue = memoisedArray[i.add(new BigInteger("-1")).intValue()]
                    .add(memoisedArray[i.add(new BigInteger("-2")).intValue()]);
            memoisedArray[i.intValue()] = currentValue;
        }
        return memoisedArray[index.intValue()];

    } // End of the findFibonacciNumberDynamicIterative method

    /**
     * The dynamic approach to generating the `n`th fibonacci number in a recursive fashion. Driver method for
     * the actual generator method. This method utilises BigIntegers in order to be able to go higher than the
     * limitations placed on `long`: (2^63) - 1
     * @param index The index of the fibonacci sequence we are trying to find
     * @return Returns the value of the `n`th fibonacci number, where `n` is the index number given
     */
    private static BigInteger findFibonacciNumberDynamicRecursiveDriver(BigInteger index)
    {
        if (index.compareTo(BigInteger.ZERO) == 0)
            return BigInteger.ZERO;
        else if (index.compareTo(BigInteger.ONE) == 0)
            return BigInteger.ONE;

        return findFibonacciNumberDynamicRecursive(index, new BigInteger("2"), BigInteger.ZERO, BigInteger.ONE);

    } // End of the findFibonacciNumberDynamicRecursiveDriver method

    /**
     * The dynamic approach to generate the `n`th fibonacci number in a recursive fashion. This method utilises
     * BigIntegers in order to be able to go higher than the limitations placed on `long`: (2^63) - 1
     * @param index The index of the fibonacci sequence we are trying to find
     * @param current The current index that is being calculated
     * @param firstValue The first value (can be thought of as the value 2 indices lower from the current index)
     * @param secondValue The second value (can be thought of as the value an index lower from the current index)
     * @return Returns the value of the `n`th fibonacci number, where `n` is the index number given
     */
    private static BigInteger findFibonacciNumberDynamicRecursive(
            BigInteger index, BigInteger current, BigInteger firstValue, BigInteger secondValue)
    {
        BigInteger newSecondValue = firstValue.add(secondValue);
        if (index.compareTo(current) == 0)
            return newSecondValue;
        else
        {
            current = current.add(BigInteger.ONE);
            return findFibonacciNumberDynamicRecursive(index, current, secondValue, newSecondValue);
        }
    } // End of the findFibonacciNumberDynamicRecursive method

    /**
     * The dynamic approach to generate the `n`th fibonacci number in a direct fashion with Binet's Fibonacci
     * Number Formula
     * @param index The index of the fibonacci sequence we are trying to find
     * @return Returns the value of the `n`th fibonacci number, where `n` is the index number given
     */
    private static BigInteger findFibonacciNumberDynamicDirect(BigInteger index)
    {
        BigDecimal phi = new BigDecimal(Math.sqrt(5d)).add(BigDecimal.ONE).divide(new BigDecimal("2"), 10, BigDecimal.ROUND_HALF_EVEN);
        BigDecimal numerator = phi.pow(index.intValue()).subtract(phi.negate().pow(index.negate().intValue(), MathContext.DECIMAL128));
        BigDecimal value = numerator.divide(new BigDecimal(Math.sqrt(5d)), 100, BigDecimal.ROUND_HALF_EVEN);

        return value.toBigInteger();
    } // End of the findFibonacciNumberDynamicDirect method

    /************************* END OF DYNAMIC FIBONACCI NUMBER GENERATORS *************************/



    /************************* START OF BENCHMARKING FIBONACCI NUMBER GENERATORS *************************/

    /**
     * Compares all values generated by each algorithm
     * @param index The index of the fibonacci sequence we are trying to find
     */
    private static void benchmarkAllGeneratorsDriver(String filePath, BigInteger index)
    {
        long[] naiveSolutions = benchmarkNaiveGenerators(index.longValue());
        BigInteger[] dynamicSolutions = benchmarkDynamicGenerators(index);

        if (isTestable)
        {
            try
            {printBenchmarkWithTest(naiveSolutions, dynamicSolutions, getTestValue(filePath, index));}
            catch (IOException e)
            {
                System.err.println(e.getMessage());
                System.out.println("Benchmarking without test file");
                printBenchmarkWithoutTest(naiveSolutions, dynamicSolutions);
            }
        }
        else
            printBenchmarkWithoutTest(naiveSolutions, dynamicSolutions);
    } // End of the benchmarkAllGeneratorsDriver method

    /**
     * Runs all naive generators, returning an array of all generated values
     * @param index The index of the fibonacci sequence we are trying to find
     * @return An array of naively generated solutions
     */
    private static long[] benchmarkNaiveGenerators(long index)
    {
        long[] returnValues = new long[3];
        returnValues[0] = findFibonacciNumberNaiveIterative(index);
        returnValues[1] = findFibonacciNumberNaiveRecursiveDriver(index);
        returnValues[2] = findFibonacciNumberNaiveDirect(index);
        return returnValues;
    } // End of the benchmarkNaiveGenerators method

    /**
     * Runs all dynamic generators, returning an array of all generated values
     * @param index The index of the fibonacci sequence we are trying to find
     * @return An array of dynamically generated solutions
     */
    private static BigInteger[] benchmarkDynamicGenerators(BigInteger index)
    {
        BigInteger[] returnValues = new BigInteger[3];
        returnValues[0] = findFibonacciNumberDynamicIterative(index);
        returnValues[1] = findFibonacciNumberDynamicRecursiveDriver(index);
        returnValues[2] = findFibonacciNumberDynamicDirect(index);
        return returnValues;
    } // End of the benchmarkDynamicGenerators

    /**
     * Outputs the results of the benchmark gathered from all generators
     * @param naiveSolutions An array of naively generated solutions
     * @param dynamicSolutions An array of dynamically generated solutions
     */
    private static void printBenchmarkWithTest(
            long[] naiveSolutions, BigInteger[] dynamicSolutions, BigInteger testSolution)
    {
        // Naive difference calculations
        BigInteger naiveDifferenceIterative = testSolution.subtract(new BigInteger(Long.toString(naiveSolutions[0])));
        BigInteger naiveDifferenceRecursive = testSolution.subtract(new BigInteger(Long.toString(naiveSolutions[1])));
        BigInteger naiveDifferenceDirect = testSolution.subtract(new BigInteger(Long.toString(naiveSolutions[2])));

        // Naive percent error calculations
        BigDecimal naivePercentErrorIterative = new BigDecimal(
                naiveDifferenceIterative).divide(new BigDecimal(testSolution), 10, BigDecimal.ROUND_HALF_EVEN).abs().multiply(new BigDecimal("100"));

        BigDecimal naivePercentErrorRecursive = new BigDecimal(
                naiveDifferenceRecursive).divide(new BigDecimal(testSolution), 10, BigDecimal.ROUND_HALF_EVEN).abs().multiply(new BigDecimal("100"));

        BigDecimal naivePercentErrorDirect = new BigDecimal(
                naiveDifferenceDirect).divide(new BigDecimal(testSolution), 10, BigDecimal.ROUND_HALF_EVEN).abs().multiply(new BigDecimal("100"));

        // Dynamic difference calculations
        BigInteger dynamicDifferenceIterative = testSolution.subtract(dynamicSolutions[0]);
        BigInteger dynamicDifferenceRecursive = testSolution.subtract(dynamicSolutions[1]);
        BigInteger dynamicDifferenceDirect = testSolution.subtract(dynamicSolutions[2]);

        // Dynamic percent error calculations
        BigDecimal dynamicPercentErrorIterative = new BigDecimal(
                dynamicDifferenceIterative).divide(new BigDecimal(testSolution), 10, BigDecimal.ROUND_HALF_EVEN).abs().multiply(new BigDecimal("100"));

        BigDecimal dynamicPercentErrorRecursive = new BigDecimal(
                dynamicDifferenceRecursive).divide(new BigDecimal(testSolution), 10, BigDecimal.ROUND_HALF_EVEN).abs().multiply(new BigDecimal("100"));

        BigDecimal dynamicPercentErrorDirect = new BigDecimal(
                dynamicDifferenceDirect).divide(new BigDecimal(testSolution), 10, BigDecimal.ROUND_HALF_EVEN).abs().multiply(new BigDecimal("100"));

        // Naive output
        System.out.println("Naive Solutions:");
        System.out.printf("\tIterative Solution:\t%d\tDifference:\t\t%d\n", naiveSolutions[0], naiveDifferenceIterative);
        System.out.printf("\t\tTest Solution:\t%d\tPercent Error:\t\t%.5f\n",
                testSolution, naivePercentErrorIterative);

        System.out.printf("\tRecursive Solution:\t%d\tDifference:\t\t%d\n", naiveSolutions[1], naiveDifferenceRecursive);
        System.out.printf("\t\tTest Solution:\t%d\tPercent Error:\t\t%.5f\n",
                testSolution, naivePercentErrorRecursive);

        System.out.printf("\tDirect Solution:\t%d\tDifference:\t\t%d\n", naiveSolutions[2], naiveDifferenceDirect);
        System.out.printf("\t\tTest Solution:\t%d\tPercent Error:\t\t%.5f\n",
                testSolution, naivePercentErrorDirect);

        // Dynamic output
        System.out.println("Dynamic Solutions");

        System.out.printf("\tIterative Solution:\t%d\tDifference:\t\t%d\n", dynamicSolutions[0], dynamicDifferenceIterative);
        System.out.printf("\t\tTest Solution:\t%d\tPercent Error:\t\t%.5f\n",
                testSolution, dynamicPercentErrorIterative);

        System.out.printf("\tRecursive Solution:\t%d\tDifference:\t\t%d\n", dynamicSolutions[1], dynamicDifferenceRecursive);
        System.out.printf("\t\tTest Solution:\t%d\tPercent Error:\t\t%.5f\n",
                testSolution, dynamicPercentErrorRecursive);

        System.out.printf("\tDirect Solution:\t%d\tDifference:\t\t%d\n", dynamicSolutions[2], dynamicDifferenceDirect);
        System.out.printf("\t\tTest Solution:\t%d\tPercent Error:\t\t%.5f\n",
                testSolution, dynamicPercentErrorDirect);
    } // End of the printBenchmarkWithoutTest method

    /**
     * Outputs the results of the benchmark gathered from all generators
     * @param naiveSolutions An array of naively generated solutions
     * @param dynamicSolutions An array of dynamically generated solutions
     */
    private static void printBenchmarkWithoutTest(long[] naiveSolutions, BigInteger[] dynamicSolutions)
    {
        System.out.println("Naive Solutions:");
        System.out.printf("\tIterative Solution:\t%d\n", naiveSolutions[0]);
        System.out.printf("\tRecursive Solution:\t%d\n", naiveSolutions[1]);
        System.out.printf("\tDirect Solution:\t%d\n", naiveSolutions[2]);

        System.out.println("Dynamic Solutions");
        System.out.printf("\tIterative Solution:\t%d\n", dynamicSolutions[0]);
        System.out.printf("\tRecursive Solution:\t%d\n", dynamicSolutions[1]);
        System.out.printf("\tDirect Solution:\t%d\n", dynamicSolutions[2]);
    } // End of the printBenchmarkWithoutTest method

    /************************* END OF BENCHMARKING FIBONACCI NUMBER GENERATORS *************************/



    /************************* START OF HELPER METHODS *************************/

    /**
     * [Helper Method] Gives the suffix of a number for prettier formatting
     * @param index The number that needs to be printed out
     * @return Returns the suffix of the number in a String
     */
    private static String getIndexSuffix(BigInteger index)
    {
        int lastDigit = index.mod(new BigInteger("10")).intValue();
        switch (lastDigit)
        {
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

    /**
     * Gets the value from the given test file
     * @param filePath The file path to the test file
     * @param index The number that needs to be printed out
     * @return Returns the "true" value that the test file gives (assumes test file is accurate)
     * @throws IOException Throws an exception if the test file is malformed or is too short to skip through
     */
    private static BigInteger getTestValue(String filePath, BigInteger index) throws IOException
    {
        if (index.compareTo(new BigInteger(MAX_TEST_FILE_INDEX)) == 1)
            throw new IOException("Error: test file does not have prerequisite number of entries");

        Stream<String> lines = Files.lines(Paths.get(filePath)); // Reads from the file
        String testLine = lines.skip(index.longValue()).findFirst().get(); // Skips past most lines, reads in a line
        String[] lineTokens = testLine.split(" ");

        if (lineTokens.length != 2)
            throw new IOException("Error: test file line was not of the correct form, please check your test file entries");
        String realValueString = lineTokens[1];
        return new BigInteger(realValueString);
    } // End of the getTestValue method

    /************************* END OF HELPER METHODS *************************/

    // Private exception handling
    private static class InvalidInputException extends Throwable {public InvalidInputException(String s) {super(s);}}
} // End of the Fibonacci finder class
