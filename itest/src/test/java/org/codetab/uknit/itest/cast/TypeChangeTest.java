package org.codetab.uknit.itest.cast;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TypeChangeTest {
    @InjectMocks
    private TypeChange typeChange;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCount() {
        long count = 0L;

        long actual = typeChange.getCount();

        assertEquals(count, actual);
    }

    @Test
    public void testAggregate() {
        TypeChange other = Mockito.mock(TypeChange.class);
        long count = 1L;

        when(other.getCount()).thenReturn(count);
        typeChange.aggregate(other);
    }
}
