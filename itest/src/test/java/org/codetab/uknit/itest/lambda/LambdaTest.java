package org.codetab.uknit.itest.lambda;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileFilter;
import java.util.function.BiFunction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class LambdaTest {
    @InjectMocks
    private Lambda lambda;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReturnLambdaVar() {

        FileFilter actual = lambda.returnLambdaVar();

        File file = Mockito.mock(File.class);
        when(file.isHidden()).thenReturn(true).thenReturn(false);
        assertThat(actual.accept(file)).isTrue();
        assertThat(actual.accept(file)).isFalse();
    }

    @Test
    public void testReturnLambda() {

        FileFilter actual = lambda.returnLambda();

        File file = Mockito.mock(File.class);
        when(file.isHidden()).thenReturn(true).thenReturn(false);
        assertThat(actual.accept(file)).isTrue();
        assertThat(actual.accept(file)).isFalse();
    }

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void testReturnLambdaVarParentheses() {

        BiFunction actual = lambda.returnLambdaVarParentheses();

        assertThat(actual.apply("a", "b")).isEqualTo("ab");
    }

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void testReturnLambdaParentheses() {

        BiFunction actual = lambda.returnLambdaParentheses();

        assertThat(actual.apply("a", "b")).isEqualTo("ab");
    }

    @Test
    public void testReturnLambdaVarTyped() {

        BiFunction<Integer, Integer, Integer> actual =
                lambda.returnLambdaVarTyped();

        assertThat(actual.apply(3, 5)).isEqualTo(8);
    }

    @Test
    public void testReturnLambdaTyped() {

        BiFunction<Integer, Integer, Integer> actual =
                lambda.returnLambdaTyped();

        assertThat(actual.apply(10, 3)).isEqualTo(7);
    }
}
