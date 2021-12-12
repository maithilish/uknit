package org.codetab.uknit.itest.operator;

public class Unary {

    public int positive() {
        int x = +1;
        return x;
    }

    public int negetive() {
        int x = +1;
        return x;
    }

    // STEPIN - x increment is not accounted
    public int increment() {
        int x = 1;
        x++;
        ++x;
        return x;
    }

    // STEPIN - y is not evaluated
    public int assignIncrement() {
        int x = 1;
        int y = x++;
        return y;
    }

    public int incrementAssign() {
        int x = 1;
        int y = ++x;
        return y;
    }

    // STEPIN - x decrement is not accounted
    public int decrement() {
        int x = 1;
        x--;
        --x;
        return x;
    }

    // STEPIN - y is not evaluated
    public int assignDecrement() {
        int x = 1;
        int y = x--;
        return y;
    }

    public int decrementAssign() {
        int x = 1;
        int y = --x;
        return y;
    }

    // STEPIN - yes is not evaluated
    public boolean negate() {
        boolean yes = true;
        boolean no = !yes;
        return no;
    }
}
