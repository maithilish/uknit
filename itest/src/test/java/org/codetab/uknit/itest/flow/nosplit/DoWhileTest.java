package org.codetab.uknit.itest.flow.nosplit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class DoWhileTest {
    @InjectMocks
    private DoWhile doWhile;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testWhileDo() {
        int count = 5;

        int actual = doWhile.whileDo();

        assertEquals(count, actual);
    }

    @Test
    public void testDoWhile() {
        int count = 5;

        int actual = doWhile.doWhile();

        assertEquals(count, actual);
    }
}
