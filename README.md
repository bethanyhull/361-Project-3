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

This project was definitely more complex than the last one. We were able to create the NFA relatively easily.
Creating the eClosure method was more complex but not too difficult. We ran into the most trouble when writing
the getDFA() method and fixing bugs. We realized that parts of the implementation for the NFA would have to be 
changed to properly get the DFA. One small issue that caused a lot of problems was that the HashMap of transitions
had the values as NFAState rather than a set of NFAStates. Since maps cannot have one key for multiple values, this
caused problems. This was an easy fix though, and it made the getTo() method much simpler. The getDFA() method was
difficult because it involved a while loop, multiple for loops, and several if statements. This made it difficult to 
keep organized. While writing the getDFA() method, we knew we would need to check if multiple sets of states
were equivalent but in a different order. We eventually converted our Linked Hash Sets to Tree Sets to keep them ordered. 
We figured out the build path, so we were able to run the program in Eclipse (since in the last project we were only
able to run on the command line). Overall, this project went well. We realized that we often made things more complicated
than they needed to be. We also found that it helped a lot to take breaks after a certain amount of time working on the 
same errors. There were several times that we made big breakthroughs when we spent a bit of time away. 

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
