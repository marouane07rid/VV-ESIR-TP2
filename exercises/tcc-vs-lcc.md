# TCC *vs* LCC

Explain under which circumstances *Tight Class Cohesion* (TCC) and *Loose Class Cohesion* (LCC) metrics produce the same value for a given Java class. Build an example of such as class and include the code below or find one example in an open-source project from Github and include the link to the class below. Could LCC be lower than TCC for any given class? Explain.

## Answer

TCC=LCC=1 is a maximally cohesive class: all methods are connected. It means that the number of indirect connections is null
The higher TCC and LCC, the more cohesive the class is.
