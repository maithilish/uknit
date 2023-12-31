package org.codetab.uknit.itest.superclass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class MultiGetRealTest {
    @InjectMocks
    private MultiGetReal multiGetReal;

    @Mock
    private PayloadReal payload;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMulti() {
        InfoReal infoReal = Mockito.mock(InfoReal.class);
        InfoReal infoReal2 = Mockito.mock(InfoReal.class);
        long apple = 1L;
        InfoReal infoReal3 = Mockito.mock(InfoReal.class);
        long grape = 1L;

        when(payload.getInfo()).thenReturn(infoReal).thenReturn(infoReal2)
                .thenReturn(infoReal3);
        when(infoReal2.getId()).thenReturn(apple);
        when(infoReal3.getId()).thenReturn(grape);

        long actual = multiGetReal.getMulti();

        assertEquals(grape, actual);
        verify(infoReal).setId(1L);
    }

    @Test
    public void testGetMultiWithSuper() {
        InfoReal infoReal = Mockito.mock(InfoReal.class);
        InfoReal infoReal2 = Mockito.mock(InfoReal.class);
        long apple = 1L;
        InfoReal infoReal3 = Mockito.mock(InfoReal.class);
        long grape = 1L;

        when(payload.getInfo()).thenReturn(infoReal).thenReturn(infoReal2)
                .thenReturn(infoReal3);
        when(infoReal2.getId()).thenReturn(apple);
        when(infoReal3.getId()).thenReturn(grape);

        long actual = multiGetReal.getMultiWithSuper();

        assertEquals(grape, actual);
        verify(infoReal).setId(1L);
    }

    @Test
    public void testGetMultiTask() {
        Task task = Mockito.mock(Task.class);
        PayloadReal payloadReal = Mockito.mock(PayloadReal.class);
        InfoReal infoReal = Mockito.mock(InfoReal.class);
        PayloadReal payloadReal2 = Mockito.mock(PayloadReal.class);
        InfoReal infoReal2 = Mockito.mock(InfoReal.class);
        long apple = 1L;
        PayloadReal payloadReal3 = Mockito.mock(PayloadReal.class);
        InfoReal infoReal3 = Mockito.mock(InfoReal.class);
        long grape = 1L;

        when(task.getPayload()).thenReturn(payloadReal).thenReturn(payloadReal2)
                .thenReturn(payloadReal3);
        when(payloadReal.getInfo()).thenReturn(infoReal);
        when(payloadReal2.getInfo()).thenReturn(infoReal2);
        when(infoReal2.getId()).thenReturn(apple);
        when(payloadReal3.getInfo()).thenReturn(infoReal3);
        when(infoReal3.getId()).thenReturn(grape);

        long actual = multiGetReal.getMulti(task);

        assertEquals(grape, actual);
        verify(infoReal).setId(1L);
    }

    @Test
    public void testGetMultiPayloadReal() {
        PayloadReal payloadReal = Mockito.mock(PayloadReal.class);
        InfoReal infoReal = Mockito.mock(InfoReal.class);
        InfoReal infoReal2 = Mockito.mock(InfoReal.class);
        long apple = 1L;
        InfoReal infoReal3 = Mockito.mock(InfoReal.class);
        long grape = 1L;

        when(payloadReal.getInfo()).thenReturn(infoReal).thenReturn(infoReal2)
                .thenReturn(infoReal3);
        when(infoReal2.getId()).thenReturn(apple);
        when(infoReal3.getId()).thenReturn(grape);

        long actual = multiGetReal.getMulti(payloadReal);

        assertEquals(grape, actual);
        verify(infoReal).setId(1L);
    }
}
