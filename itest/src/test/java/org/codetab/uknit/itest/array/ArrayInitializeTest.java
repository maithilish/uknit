package org.codetab.uknit.itest.array;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class ArrayInitializeTest {
    @InjectMocks
    private ArrayInitialize arrayInitialize;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInitializeStrings() {
        String[] array = {"hello", "world"};

        String[] actual = arrayInitialize.initializeStrings();

        assertArrayEquals(array, actual);
    }
}
