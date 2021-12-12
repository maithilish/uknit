package org.codetab.uknit.itest.nest;

public class AnonymousClassParameterized {

    public void add(final Calc2 calc) {
        calc.op(1, 2, new IAddable<Integer>() {
            @Override
            public Integer add(final Integer a, final Integer b) {
                return a + b;
            }
        });
    }
}

class Calc2 {
    public <T> T op(final T a, final T b, final IAddable<T> addable) {
        return addable.add(a, b);
    }
}

interface IAddable<T> {
    T add(T a, T b);
}
