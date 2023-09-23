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

class MultiGetMockTest {
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
        JobInfo jobInfo = Mockito.mock(JobInfo.class);
        JobInfo jobInfo2 = Mockito.mock(JobInfo.class);
        long apple = 1L;
        JobInfo jobInfo3 = Mockito.mock(JobInfo.class);
        long grape = 1L;

        when(payload.getJobInfo()).thenReturn(jobInfo).thenReturn(jobInfo2)
                .thenReturn(jobInfo3);
        when(jobInfo2.getId()).thenReturn(apple);
        when(jobInfo3.getId()).thenReturn(grape);

        long actual = multiGetMock.getMulti();

        assertEquals(grape, actual);
        verify(jobInfo).setId(1L);
    }

    @Test
    public void testGetMultiWithSuper() {
        JobInfo jobInfo = Mockito.mock(JobInfo.class);
        JobInfo jobInfo2 = Mockito.mock(JobInfo.class);
        long apple = 1L;
        JobInfo jobInfo3 = Mockito.mock(JobInfo.class);
        long grape = 1L;

        when(payload.getJobInfo()).thenReturn(jobInfo).thenReturn(jobInfo2)
                .thenReturn(jobInfo3);
        when(jobInfo2.getId()).thenReturn(apple);
        when(jobInfo3.getId()).thenReturn(grape);

        long actual = multiGetMock.getMultiWithSuper();

        assertEquals(grape, actual);
        verify(jobInfo).setId(1L);
    }

    @Test
    public void testGetMultiStep() {
        Step step = Mockito.mock(Step.class);
        Payload payload2 = Mockito.mock(Payload.class);
        JobInfo jobInfo = Mockito.mock(JobInfo.class);
        Payload payload3 = Mockito.mock(Payload.class);
        JobInfo jobInfo2 = Mockito.mock(JobInfo.class);
        long apple = 1L;
        Payload payload4 = Mockito.mock(Payload.class);
        JobInfo jobInfo3 = Mockito.mock(JobInfo.class);
        long grape = 1L;

        when(step.getPayload()).thenReturn(payload2).thenReturn(payload3)
                .thenReturn(payload4);
        when(payload2.getJobInfo()).thenReturn(jobInfo);
        when(payload3.getJobInfo()).thenReturn(jobInfo2);
        when(jobInfo2.getId()).thenReturn(apple);
        when(payload4.getJobInfo()).thenReturn(jobInfo3);
        when(jobInfo3.getId()).thenReturn(grape);

        long actual = multiGetMock.getMulti(step);

        assertEquals(grape, actual);
        verify(jobInfo).setId(1L);
    }

    @Test
    public void testGetMultiPayload() {
        Payload payload = Mockito.mock(Payload.class);
        JobInfo jobInfo = Mockito.mock(JobInfo.class);
        JobInfo jobInfo2 = Mockito.mock(JobInfo.class);
        long apple = 1L;
        JobInfo jobInfo3 = Mockito.mock(JobInfo.class);
        long grape = 1L;

        when(payload.getJobInfo()).thenReturn(jobInfo).thenReturn(jobInfo2)
                .thenReturn(jobInfo3);
        when(jobInfo2.getId()).thenReturn(apple);
        when(jobInfo3.getId()).thenReturn(grape);

        long actual = multiGetMock.getMulti(payload);

        assertEquals(grape, actual);
        verify(jobInfo).setId(1L);
    }
}
