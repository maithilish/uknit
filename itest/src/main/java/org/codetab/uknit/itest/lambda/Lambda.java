package org.codetab.uknit.itest.lambda;

import java.io.FileFilter;
import java.util.function.BiFunction;

public class Lambda {

    // STEPIN - in below tests, test return lambda
    public FileFilter returnLambdaVar() {
        FileFilter ff = f -> f.isHidden();
        return ff;
    }

    public FileFilter returnLambda() {
        return f -> f.isHidden();
    }

    @SuppressWarnings("rawtypes")
    public BiFunction returnLambdaVarParentheses() {
        BiFunction biFunc = (a, b) -> a.toString() + b.toString();
        return biFunc;
    }

    @SuppressWarnings("rawtypes")
    public BiFunction returnLambdaParentheses() {
        return (a, b) -> a.toString() + b.toString();
    }

    public BiFunction<Integer, Integer, Integer> returnLambdaVarTyped() {
        BiFunction<Integer, Integer, Integer> biFunc = (a, b) -> a + b;
        return biFunc;
    }

    public BiFunction<Integer, Integer, Integer> returnLambdaTyped() {
        return (a, b) -> a - b;
    }
}
