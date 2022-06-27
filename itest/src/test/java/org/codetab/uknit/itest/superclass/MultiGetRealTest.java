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
        long kiwi = 1L;

        long actual = multiGetReal.getMulti();

        assertEquals(kiwi, actual);
    }

    @Test
    public void testGetMultiWithSuper() {
        long kiwi = 1L;

        long actual = multiGetReal.getMultiWithSuper();

        assertEquals(kiwi, actual);
    }

    @Test
    public void testGetMultiTask() {
        Task task = Mockito.mock(Task.class);
        PayloadReal apple = new PayloadReal();
        PayloadReal orange = new PayloadReal();
        PayloadReal mango = new PayloadReal();
        long cherry = 1L;

        mango.getInfo().setId(cherry);

        when(task.getPayload()).thenReturn(apple).thenReturn(orange)
                .thenReturn(mango);

        long actual = multiGetReal.getMulti(task);

        assertEquals(cherry, actual);
    }

    @Test
    public void testGetMultiPayloadReal() {
        PayloadReal payloadReal = new PayloadReal();
        long kiwi = 1L;

        long actual = multiGetReal.getMulti(payloadReal);

        assertEquals(kiwi, actual);
    }
}
