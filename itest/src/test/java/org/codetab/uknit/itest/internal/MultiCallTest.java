package org.codetab.uknit.itest.internal;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.internal.Model.JobInfo;
import org.codetab.uknit.itest.internal.Model.Payload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MultiCallTest {
    @InjectMocks
    private MultiCall multiCall;

    @Mock
    private Payload payload;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCall() {
        JobInfo jobInfo4 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo5 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo6 = Mockito.mock(JobInfo.class);

        when(payload.getJobInfo()).thenReturn(jobInfo4).thenReturn(jobInfo5)
                .thenReturn(jobInfo6);
        multiCall.call();
    }

    @Test
    public void testReturnCall() {
        JobInfo jobInfo4 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo5 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo6 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo3 = jobInfo6;

        when(payload.getJobInfo()).thenReturn(jobInfo4).thenReturn(jobInfo5)
                .thenReturn(jobInfo6);

        JobInfo actual = multiCall.returnCall();

        assertSame(jobInfo3, actual);
    }

    @Test
    public void testCallAndUse() {
        JobInfo jobInfo4 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo5 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo6 = Mockito.mock(JobInfo.class);

        when(payload.getJobInfo()).thenReturn(jobInfo4).thenReturn(jobInfo5)
                .thenReturn(jobInfo6);
        multiCall.callAndUse();

        verify(jobInfo4).check();
        verify(jobInfo5).check();
        verify(jobInfo6).check();
    }

    @Test
    public void testReturnCallAndUse() {
        JobInfo jobInfo4 = Mockito.mock(JobInfo.class);
        boolean apple = true;
        JobInfo jobInfo5 = Mockito.mock(JobInfo.class);
        boolean grape = true;
        JobInfo jobInfo6 = Mockito.mock(JobInfo.class);
        boolean orange = true;

        when(payload.getJobInfo()).thenReturn(jobInfo4).thenReturn(jobInfo5)
                .thenReturn(jobInfo6);
        when(jobInfo4.isValid()).thenReturn(apple);
        when(jobInfo5.isValid()).thenReturn(grape);
        when(jobInfo6.isValid()).thenReturn(orange);

        boolean actual = multiCall.returnCallAndUse();

        assertTrue(actual);
    }

    @Test
    public void testCallNestedArg() {
        JobInfo jobInfo7 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo8 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo9 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo10 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo11 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo12 = Mockito.mock(JobInfo.class);

        when(payload.getJobInfo()).thenReturn(jobInfo7).thenReturn(jobInfo10);
        when(payload.getJobInfo(jobInfo7)).thenReturn(jobInfo8);
        when(payload.getJobInfo(jobInfo8)).thenReturn(jobInfo9);
        when(payload.getJobInfo(jobInfo10)).thenReturn(jobInfo11);
        when(payload.getJobInfo(jobInfo11)).thenReturn(jobInfo12);
        multiCall.callNestedArg();
    }

    @Test
    public void testReturnCallNestedArg() {
        JobInfo jobInfo7 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo8 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo9 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo10 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo11 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo12 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo6 = jobInfo12;

        when(payload.getJobInfo()).thenReturn(jobInfo7).thenReturn(jobInfo10);
        when(payload.getJobInfo(jobInfo7)).thenReturn(jobInfo8);
        when(payload.getJobInfo(jobInfo8)).thenReturn(jobInfo9);
        when(payload.getJobInfo(jobInfo10)).thenReturn(jobInfo11);
        when(payload.getJobInfo(jobInfo11)).thenReturn(jobInfo12);

        JobInfo actual = multiCall.returnCallNestedArg();

        assertSame(jobInfo6, actual);
    }
}
