package org.codetab.uknit.itest.lambda;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileFilter;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class LambdaTest {
    @InjectMocks
    private Lambda lambda;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReturnLambdaVar() {

        @SuppressWarnings("unused")
        FileFilter actual = lambda.returnLambdaVar();

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testReturnLambda() {

        @SuppressWarnings("unused")
        FileFilter actual = lambda.returnLambda();

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testReturnLambdaVarParentheses() {

        @SuppressWarnings({"unused", "rawtypes"})
        BiFunction actual = lambda.returnLambdaVarParentheses();

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testReturnLambdaParentheses() {

        @SuppressWarnings({"unused", "rawtypes"})
        BiFunction actual = lambda.returnLambdaParentheses();

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testReturnLambdaVarTyped() {
        @SuppressWarnings("unused")
        BiFunction<Integer, Integer, Integer> actual =
                lambda.returnLambdaVarTyped();

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testReturnLambdaTyped() {
        @SuppressWarnings("unused")
        BiFunction<Integer, Integer, Integer> actual =
                lambda.returnLambdaTyped();

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testMultiStatementLambda() {

        @SuppressWarnings("unused")
        Runnable actual = lambda.multiStatementLambda();

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testExplicitParameterLambda() {

        @SuppressWarnings("unused")
        BinaryOperator<Long> actual = lambda.explicitParameterLambda();

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testCallLamdba() {

        boolean actual = lambda.callLamdba();

        assertTrue(actual);
    }
}
