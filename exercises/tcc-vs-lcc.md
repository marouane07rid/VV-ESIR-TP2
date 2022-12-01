# TCC *vs* LCC

Explain under which circumstances *Tight Class Cohesion* (TCC) and *Loose Class Cohesion* (LCC) metrics produce the same value for a given Java class. Build an example of such as class and include the code below or find one example in an open-source project from Github and include the link to the class below. Could LCC be lower than TCC for any given class? Explain.

## Answer

If TCC=LCC=1, it means that this class is a maximally cohesive class where all methods are directly connected to each other.

```public class Lcc_tcc {
    
    public boolean fct1() {
        return fct2();
    }

    public boolean fct2() {
        return fct3() && true;
    }

    public boolean fct3() {
        return false;
    }
}
```