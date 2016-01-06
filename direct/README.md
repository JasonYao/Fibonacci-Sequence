# The Direct Solution
By Jason Yao

## Description
This repo contains a lossy* direct approach to identifying the `n`th fibonacci number utilising Binet's formula:

![Binet's Fibonacci Number Formula](img/fibonacci_formula.png)

This formula depends upon `Φ`, defined as the golden ratio:

![The Golden Ratio](img/phi.png)

* It should be noted that this approach results in approximations of the actual number, and diverges quite 
drastically with larger numbers of `n` (starts going nuts around n = 74), due to rounding errors with 
floating point numbers when dealing with sqrt(5) in the denominator of the formula.

However, if looking for a quick and dirty way to generate this number, then it's probably the closest we have to an `O(1)` solution
(it's not truly `O(1)` since exponentiation doesn't exist as a logic gate the hardware can use, as it can with addition or multiplication).

- [java implementation](java)

- [c implementation](c)

- [python implementation](python)

## Running & compiling
Follow the instructions in each individual code subdirectory.
