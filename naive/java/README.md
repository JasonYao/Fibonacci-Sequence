# Naive Java Fibonacci Number Finder
By Jason Yao

## A general note beforehand
I'd like to take this opportunity to clarify that this fibonacci number generator is based on the system where:

The `0`th fibonacci number is: `0`

The `1`st fibonacci number is: `1`

The `2`nd fibonacci number is: `1`

The `3`rd fibonacci number is: `2`

etc. etc.

## Description: Recursive
The recursive model depends upon a driver in order to feed some relevant information. This allows us to use pre-computed information from previous
method calls, without having to resort to doing something like the following, for which each prior value has to be recalculated, increasing both
time and space complexity as `n` increases

```java
long current = findFibonacciNumberRecursive(n - 2) + findFibonacciNumberRecursive(n - 1) // NOTE: Very BAD
```

## Description: Iterative
The iterative model is a much more elegant solution, in which all work is done with 3 `long` variables, thus making the algorithm exceedingly efficient
both from the avoidance of making new stack frames from method calls, to efficient use of referencing and memory space usage.

## Compilation & Running

### The easy way
A compile and run script has been included for your benefit, the commands to run so is simply, where n is a non-negative integer:

```sh
./compileAndTest.sh <recursive | iterative> <n>
```

E.g. Run the program recursively to find the 100th fibonacci number

```sh
./compileAndTest.sh recursive 100
```

E.g. Run the program iteratively to find the 100th fibonacci number

```sh
./compileAndTest.sh iterative 100
```

### The manual way

```sh
mkdir -p output
javac src/*.java -d output # Compiles the program
java -cp output FibonacciFinder <iterative | recursive> <n> # Runs the program
```

E.g. Run the program recursively to find the 100th fibonacci number

```sh
mkdir -p output
javac src/*.java -d output # Compiles the program
java -cp output FibonacciFinder recursive 100 # Runs the program
```

E.g. Run the program iteratively to find the 100th fibonacci number

```sh
mkdir -p output
javac src/*.java -d output # Compiles the program
java -cp output FibonacciFinder iterative 100 # Runs the program
```
