package org.codetab.uknit.itest.lambda;

import java.io.FileFilter;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

class Lambda {

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

    public Runnable multiStatementLambda() {
        Runnable multiStatement = () -> {
            System.out.print("Hello");
            System.out.println(" World");
        };
        return multiStatement;
    }

    public BinaryOperator<Long> explicitParameterLambda() {
        BinaryOperator<Long> addExplicit =
                (final Long x, final Long y) -> x + y;
        return addExplicit;
    }

    public boolean callLamdba() {
        Predicate<Integer> atLeast5 = x -> x > 5;
        return atLeast5.test(6);
    }
}
