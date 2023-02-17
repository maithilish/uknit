package org.codetab.uknit.itest.nested.anon;

class AnonAbstract {

    public void add(final Calc calc) {
        calc.op(1, 2, new Addable<Integer>() {
            @Override
            Integer add(final Integer a, final Integer b) {
                return a + b;
            }
        });
    }
}

class Calc {
    public <T> T op(final T a, final T b, final Addable<T> addable) {
        return addable.add(a, b);
    }
}

abstract class Addable<T> {
    abstract T add(T a, T b);
}
