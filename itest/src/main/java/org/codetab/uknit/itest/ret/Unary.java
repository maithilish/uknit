package org.codetab.uknit.itest.ret;

class Unary {

    public int positive() {
        return +1;
    }

    public int increment() {
        int x = 1;
        return ++x;
    }

    public int returnIncrement() {
        int x = 1;
        return x++;
    }

    public int decrement() {
        int x = 1;
        return --x;
    }

    public int returnDecrement() {
        int x = 1;
        return x--;
    }

    public boolean negate() {
        boolean yes = true;
        return !yes;
    }
}
