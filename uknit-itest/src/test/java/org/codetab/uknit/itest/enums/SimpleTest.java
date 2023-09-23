package org.codetab.uknit.itest.enums;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class SimpleTest {
    @InjectMocks
    private Simple simple;

    @Mock
    private Lookup lookup;

    @BeforeEach
    public void setUp() throws Exception {
        simple = Mockito.mock(Simple.class);
    }

    @Test
    public void testGetLookup() {

        Lookup actual = simple.getLookup();

        assertSame(lookup, actual);
    }
}
