package org.codetab.uknit.itest.superclass;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MultiGetMockStaticTest {
    @InjectMocks
    private MultiGetMockStatic multiGetMockStatic;

    @Mock
    private Payload payload;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetStaticMulti() {
        long grape = 1L;

        long actual = multiGetMockStatic.getStaticMulti();

        assertEquals(grape, actual);
    }

    @Test
    public void testGetStaticMultiWithSuper() {
        long grape = 1L;

        long actual = multiGetMockStatic.getStaticMultiWithSuper();

        assertEquals(grape, actual);
    }

    @Test
    public void testGetStaticMultiStep() {
        Step step = Mockito.mock(Step.class);
        long grape = 1L;

        long actual = multiGetMockStatic.getStaticMulti(step);

        assertEquals(grape, actual);
    }
}
