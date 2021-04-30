# 361-Project-3
* Author: Bethany Hull and Marie Phelan
* Class: CS361 Section 1
* Semester: Spring 2021

## Overview

This program creates a deterministic finite automata that is equivalent to a given nondeterministic finite automata. The program takes in the final states, states that are not final or start states, and the start state on three separate lines. It then takes in the transitions. Finally, it takes strings to check if they are a valid part of that NFA's language. After converting the NFA to a DFA, the program prints the 5-tuple and a "no" or "yes" depending on if each string is a valid string in the language or not.

This program creates a nondeterministic finite automata from a given regular expression. The given driver converts the NFA to a DFA and prints a "yes" or "no" depending on if each string is a valid string in the language or not. The program takes in a file that contains the regular expression on the first lines and strings to check on subsequent lines. 

## Compiling and Using

This program requires a .txt file formatted in the following way:
> Regular expression (which contains only a, b, e, (, ), |, and *)
> all additional lines are strings to be tested

Compile and run the program with the following commands from the main project folder:
```bash
javac -cp ".:./CS361FA.jar" re/REDriver.java
java -cp ".:./CS361FA.jar" re.REDriver ./tests/p3tc1.txt
```

## Discussion

This project was simpler than the last one, partially because the previous projects deepened our understanding of how DFAs 
and NFAs are implemented. The given resources were very helpful, and Bethany had experience with parsing from the Programming
Languages course. Since we were given the Matt Might website, we studied it and applied it to fit our project. 
We did not have to spend much time debugging, but we did find at one point that we had an extra empty state, but we were 
able to fix this by adding some checks and remembering to remove unwanted final states. We had also forgotten to add to our
alphebet at a couple of points which was really easy to fix. As we ran our extra tests, we found that we were getting 
Null Pointer Exceptions, which was off-putting, but it was clarified in Piazza that this was what the output was supposed
to be. 

## Testing

To test our project, we compared the test file outputs to those found in the project 
outline. We also used test cases given in Piazza and our own cases for additional testing. 
We also added print statements to help us trace through where we were having errors.

## Sources used

Matt Might Website http://matt.might.net/articles/parsing-regex-with-recursive-descent/
Regular Expressions and Converting to a NFA http://www2.cs.duke.edu/csed/jflap/tutorial/regular/index.html
