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

public class MultiGetMockTest {
    @InjectMocks
    private MultiGetMock multiGetMock;

    @Mock
    private Payload payload;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMulti() {
        JobInfo apple = Mockito.mock(JobInfo.class);
        JobInfo grape = Mockito.mock(JobInfo.class);
        JobInfo orange = Mockito.mock(JobInfo.class);
        long kiwi = 1L;

        when(payload.getJobInfo()).thenReturn(apple).thenReturn(grape)
                .thenReturn(orange);
        when(orange.getId()).thenReturn(kiwi);

        long actual = multiGetMock.getMulti();

        assertEquals(kiwi, actual);
        verify(apple).setId(1L);
        verify(grape).getId();
    }

    @Test
    public void testGetMultiWithSuper() {
        JobInfo apple = Mockito.mock(JobInfo.class);
        JobInfo grape = Mockito.mock(JobInfo.class);
        JobInfo orange = Mockito.mock(JobInfo.class);
        long kiwi = 1L;

        when(payload.getJobInfo()).thenReturn(apple).thenReturn(grape)
                .thenReturn(orange);
        when(orange.getId()).thenReturn(kiwi);

        long actual = multiGetMock.getMultiWithSuper();

        assertEquals(kiwi, actual);
        verify(apple).setId(1L);
        verify(grape).getId();
    }

    @Test
    public void testGetMultiStep() {
        Step step = Mockito.mock(Step.class);
        Payload apple = Mockito.mock(Payload.class);
        JobInfo grape = Mockito.mock(JobInfo.class);
        Payload orange = Mockito.mock(Payload.class);
        JobInfo kiwi = Mockito.mock(JobInfo.class);
        Payload mango = Mockito.mock(Payload.class);
        JobInfo banana = Mockito.mock(JobInfo.class);
        long cherry = 1L;

        when(step.getPayload()).thenReturn(apple).thenReturn(orange)
                .thenReturn(mango);
        when(apple.getJobInfo()).thenReturn(grape);
        when(orange.getJobInfo()).thenReturn(kiwi);
        when(mango.getJobInfo()).thenReturn(banana);
        when(banana.getId()).thenReturn(cherry);

        long actual = multiGetMock.getMulti(step);

        assertEquals(cherry, actual);
        verify(grape).setId(1L);
        verify(kiwi).getId();
    }

    @Test
    public void testGetMultiPayload() {
        Payload payload1 = Mockito.mock(Payload.class);
        JobInfo apple = Mockito.mock(JobInfo.class);
        JobInfo grape = Mockito.mock(JobInfo.class);
        JobInfo orange = Mockito.mock(JobInfo.class);
        long kiwi = 1L;

        when(payload1.getJobInfo()).thenReturn(apple).thenReturn(grape)
                .thenReturn(orange);
        when(orange.getId()).thenReturn(kiwi);

        long actual = multiGetMock.getMulti(payload1);

        assertEquals(kiwi, actual);
        verify(apple).setId(1L);
        verify(grape).getId();
    }
}