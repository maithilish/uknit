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
        JobInfo jobInfo = Mockito.mock(JobInfo.class);
        JobInfo jobInfo2 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo3 = Mockito.mock(JobInfo.class);
        long apple = 1L;

        when(payload.getJobInfo()).thenReturn(jobInfo).thenReturn(jobInfo2)
                .thenReturn(jobInfo3);
        when(jobInfo3.getId()).thenReturn(apple);

        long actual = multiGetMock.getMulti();

        assertEquals(apple, actual);
        verify(jobInfo).setId(1L);
        verify(jobInfo2).getId();
    }

    @Test
    public void testGetMultiWithSuper() {
        JobInfo jobInfo = Mockito.mock(JobInfo.class);
        JobInfo jobInfo2 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo3 = Mockito.mock(JobInfo.class);
        long apple = 1L;

        when(payload.getJobInfo()).thenReturn(jobInfo).thenReturn(jobInfo2)
                .thenReturn(jobInfo3);
        when(jobInfo3.getId()).thenReturn(apple);

        long actual = multiGetMock.getMultiWithSuper();

        assertEquals(apple, actual);
        verify(jobInfo).setId(1L);
        verify(jobInfo2).getId();
    }

    @Test
    public void testGetMultiStep() {
        Step step = Mockito.mock(Step.class);
        Payload payload2 = Mockito.mock(Payload.class);
        JobInfo jobInfo = Mockito.mock(JobInfo.class);
        Payload payload3 = Mockito.mock(Payload.class);
        JobInfo jobInfo2 = Mockito.mock(JobInfo.class);
        Payload payload4 = Mockito.mock(Payload.class);
        JobInfo jobInfo3 = Mockito.mock(JobInfo.class);
        long apple = 1L;

        when(step.getPayload()).thenReturn(payload2).thenReturn(payload3)
                .thenReturn(payload4);
        when(payload2.getJobInfo()).thenReturn(jobInfo);
        when(payload3.getJobInfo()).thenReturn(jobInfo2);
        when(payload4.getJobInfo()).thenReturn(jobInfo3);
        when(jobInfo3.getId()).thenReturn(apple);

        long actual = multiGetMock.getMulti(step);

        assertEquals(apple, actual);
        verify(jobInfo).setId(1L);
        verify(jobInfo2).getId();
    }

    @Test
    public void testGetMultiPayload() {
        JobInfo jobInfo = Mockito.mock(JobInfo.class);
        JobInfo jobInfo2 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo3 = Mockito.mock(JobInfo.class);
        long apple = 1L;

        when(payload.getJobInfo()).thenReturn(jobInfo).thenReturn(jobInfo2)
                .thenReturn(jobInfo3);
        when(jobInfo3.getId()).thenReturn(apple);

        long actual = multiGetMock.getMulti(payload);

        assertEquals(apple, actual);
        verify(jobInfo).setId(1L);
        verify(jobInfo2).getId();
    }
}
