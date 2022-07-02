package org.codetab.uknit.itest.superclass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MultiGetRealTest {
    @InjectMocks
    private MultiGetReal multiGetReal;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMulti() {
        long apple = 1L;

        long actual = multiGetReal.getMulti();

        assertEquals(apple, actual);
    }

    @Test
    public void testGetMultiWithSuper() {
        long apple = 1L;

        long actual = multiGetReal.getMultiWithSuper();

        assertEquals(apple, actual);
    }

    @Test
    public void testGetMultiTask() {
        Task task = Mockito.mock(Task.class);
        PayloadReal payloadReal = new PayloadReal();
        PayloadReal payloadReal2 = new PayloadReal();
        PayloadReal payloadReal3 = new PayloadReal();
        long apple = 1L;

        payloadReal3.getInfo().setId(apple);

        when(task.getPayload()).thenReturn(payloadReal).thenReturn(payloadReal2)
                .thenReturn(payloadReal3);

        long actual = multiGetReal.getMulti(task);

        assertEquals(apple, actual);
    }

    @Test
    public void testGetMultiPayloadReal() {
        PayloadReal payloadReal = new PayloadReal();
        long apple = 1L;

        long actual = multiGetReal.getMulti(payloadReal);

        assertEquals(apple, actual);
    }
}
