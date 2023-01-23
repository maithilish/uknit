package org.codetab.uknit.itest.lambda;

import java.util.Properties;

public class LambdaInArgGeneric {

    public Integer calculate(final CalcB calcB) {
        calcB.op(1, 2, (a, b) -> a + b);
        @SuppressWarnings("unused")
        int divValue = calcB.op(8, 4, (a, b) -> a / b);
        return calcB.op(6, 3, (a, b) -> a * b);
    }

    public void castInlambda(final Properties properties,
            final Config<String, String> config) {
        properties
                .forEach((k, v) -> config.setProperty((String) k, (String) v));
    }
}

class CalcB {
    public <T> T op(final T a, final T b, final OperationB<T> operationB) {
        return operationB.op(a, b);
    }
}

interface OperationB<T> {
    T op(T a, T b);
}

class Config<k, v> {

    public void setProperty(final k key, final v value) {
    }
}
