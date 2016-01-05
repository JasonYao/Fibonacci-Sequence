#!/usr/bin/env bash

set -e

# Compiles
mkdir -p output
javac src/*.java -d output

# Runs the program
java -cp output FibonacciFinder "$@" # The `$@` is just a bash trick to replace the mark with all commandline arguments
