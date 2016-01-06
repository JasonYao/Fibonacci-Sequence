# Fibonacci-Sequence
By Jason Yao

## A general note beforehand
I'd like to take this opportunity to clarify that all fibonacci number generators will be based on the system where:

The `0`th fibonacci number is: `0`

The `1`st fibonacci number is: `1`

The `2`nd fibonacci number is: `1`

The `3`rd fibonacci number is: `2`

etc. etc.

## Description
A repo showing different implementations of generating the `n`th fibonacci number. Overarching computing principles involved:

- Recursion vs Iterative approach (Recursion may have a more elegant solution sometimes, while iterative has faster runtime due to much fewer stack frame
calls)

- Dynamic Programming (memoization of prior calculations for future use, only useful for recursion, since iterative already uses an even faster method)

- Direct algorithmic Solving (A specific algorithm has been shown to be able to generate the nth fibonacci number, though accuracy decreases as n goes up)

- Integer overflow solving (naive does not take this into account, dynamic does take this into account)

## Description: The Naive Solution

### Naive: Recursive
The recursive model depends upon a driver in order to feed some relevant information. This allows us to use pre-computed information from previous
method calls, without having to resort to doing something like the following, for which each prior value has to be recalculated, increasing both
time and space complexity as `n` increases:

```java
long current = findFibonacciNumberRecursive(n - 2) + findFibonacciNumberRecursive(n - 1) // NOTE: Very BAD
```

### Naive: Iterative
The iterative model is a much more elegant solution, in which all work is done with 3 `long` variables, thus making the algorithm exceedingly efficient
both from the avoidance of making new stack frames from method calls, to efficient use of referencing and memory space usage. This space and time efficiency
does come at a cost: when a value is above `Long.MAX_VALUE`, an integer overflow will occur. The solution is below with the dynamic solutions, in which immutable
`java` `BigInteger`s and `BigDecimal`s are used to avoid any overflow issues.

## Description: The Dynamic Solution

### Dynamic: Recursive
This follows the more efficient recursive algorithm above, but utilises `java`'s `BigInteger` and `BigDecimal` to avoid integer overflows.
The recursive model depends upon a driver in order to feed some relevant information. This allows us to use pre-computed information from previous
method calls, without having to resort to doing something like the following, for which each prior value has to be recalculated, increasing both
time and space complexity as `n` increases:

```java
long current = findFibonacciNumberRecursive(n - 2) + findFibonacciNumberRecursive(n - 1) // NOTE: Very BAD
```

### Dynamic: Iterative
The iterative model loses much of the elegance from the naive approach, as `O(n)` space complexity must now be used, versus the `O(3)` space complexity
that we enjoyed with the naive approach. This is because `java`'s `BigInteger` and `BigDecimal` are immutable data structures, requiring the creation of
a new object every time we calculate a new value.

HOWEVER- all is not lost, and optimisations that can work, such as memoisation of prior calculated values can now be used, resulting in space complexity of
`O(n)`, and speed complexity of `O(n)`.

### The Direct Solution
This implementation contains a [lossy](#footnotes) direct approach to generating the `n`th fibonacci number utilising Binet's formula:

![Binet's Fibonacci Number Formula](img/fibonacci_formula.png)

This formula depends upon `Φ`, defined as the golden ratio:

![The Golden Ratio](img/phi.png)

## Compilation & Running

### The easy way
A compile and run script has been included for your benefit, the commands to run so is simply, where n is a non-negative integer:

```sh
./compileAndTest.sh <recursive | iterative | all> <n> <flags>
```

E.g. Run the program naively iteratively to find the 100th fibonacci number

```sh
./compileAndTest.sh naive iterative 100
```

E.g. Run the program dynamically recursively to find the 100th fibonacci number

```sh
./compileAndTest.sh dynamic recursive 100
```

E.g. Run the program to find the 100th fibonacci number with all generators

```sh
./compileAndTest.sh all 100
```

E.g. Run the program to find the 100th fibonacci number with all generators, and compare the result with a [testfile](test/fibonacci-numbers)

```sh
./compileAndTest.sh all 100 test/fibonacci-numbers
```

### The manual way

```sh
mkdir -p output
javac src/*.java -d output # Compiles the program
java -cp output FibonacciFinder <recursive | iterative | all> <n> <flags> # Runs the program
```

E.g. Run the program dynamically iteratively to find the 100th fibonacci number

```sh
mkdir -p output
javac src/*.java -d output # Compiles the program
java -cp output FibonacciFinder dynamic iterative 100 # Runs the program
```

E.g. Run the program naively recursively to find the 100th fibonacci number

```sh
mkdir -p output
javac src/*.java -d output # Compiles the program
java -cp output FibonacciFinder naive recursive 100 # Runs the program
```

E.g. Run the program to find the 100th fibonacci number with all generators

```sh
mkdir -p output
javac src/*.java -d output # Compiles the program
java -cp output FibonacciFinder all 100 # Runs the program
```

E.g. Run the program to find the 100th fibonacci number with all generators, and compare the result with a [testfile](test/fibonacci-numbers)

```sh
mkdir -p output
javac src/*.java -d output # Compiles the program
java -cp output FibonacciFinder all 100 test/fibonacci-numbers # Runs the program
```

## footnotes
It should be noted that this approach results in approximations of the actual number, and diverges quite
drastically with larger numbers of `n` (starts going nuts around n = 72), due to rounding errors with
floating point numbers when dealing with sqrt(5) in the denominator of the formula.

However, if looking for a quick and dirty way to generate this number, then it's probably the closest we have to an `O(1)` solution
(it's not truly `O(1)` since exponentiation doesn't exist as a logic gate the hardware can use, as it can with addition or multiplication).
It should be noted that from running the generator that when the dynamic version of the algorithm is used, even on very high values of `n`,
that the calculated percent error was still 0.00000 percent, showing that when floating points are dealt with accurately, that Binet's algorithm
is quite accurate.

## License
This repo is under the GNU GPL license as described [here](LICENSE), unless otherwise stated.
