package org.codetab.uknit.itest.cast;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class CastTypeChangeTest {
    @InjectMocks
    private CastTypeChange castTypeChange;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCount() {
        long count = 1L;
        castTypeChange.setCount(count);

        long actual = castTypeChange.getCount();

        assertEquals(count, actual);
    }

    @Test
    public void testSetCount() {
        long count = 1L;
        castTypeChange.setCount(count);
    }

    @Test
    public void testAggregate() {
        CastTypeChange other = Mockito.mock(CastTypeChange.class);
        long count = 1L;

        when(other.getCount()).thenReturn(count);
        castTypeChange.aggregate(other);
    }
}
