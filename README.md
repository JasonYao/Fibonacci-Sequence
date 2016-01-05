# Fibonacci-Sequence
By Jason Yao

## Description
A repo showing different implementations of generating the `n`th fibonacci number. Overarching computing principles involved:

- [Recursion vs Iterative approach](naive) (Recursion may have a more elegant solution sometimes, while iterative has faster runtime due to much fewer stack frame 
calls)

- [Dynamic Programming](dynamic) (memoization of prior calculations for future use, only useful for recursion, since iterative already uses an even faster method)

- [Direct algorithmic Solving](direct) (A specific algorithm has been shown to be able to generate the nth fibonacci number, though accuracy decreases as n goes up)

## A general note beforehand
I'd like to take this opportunity to clarify that all fibonacci number generators will be based on the system where:

The `0`th fibonacci number is: `0`

The `1`st fibonacci number is: `1`

The `2`nd fibonacci number is: `1`

The `3`rd fibonacci number is: `2`

etc. etc.

## License
This repo is under the GNU GPL license as described [here](LICENSE), unless otherwise stated.
