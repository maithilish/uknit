package org.codetab.uknit.itest.brace.im;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.brace.im.Model.JobInfo;
import org.codetab.uknit.itest.brace.im.Model.Payload;
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

        when((payload).getJobInfo()).thenReturn(jobInfo4).thenReturn(jobInfo5)
                .thenReturn(jobInfo6);
        multiCall.call();
    }

    @Test
    public void testReturnCall() {
        JobInfo jobInfo4 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo5 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo6 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo3 = jobInfo6;

        when((payload).getJobInfo()).thenReturn(jobInfo4).thenReturn(jobInfo5)
                .thenReturn(jobInfo6);

        JobInfo actual = multiCall.returnCall();

        assertSame(jobInfo3, actual);
    }

    @Test
    public void testCallAndUse() {
        JobInfo jobInfo4 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo = jobInfo4;
        JobInfo jobInfo5 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo2 = jobInfo5;
        JobInfo jobInfo6 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo3 = jobInfo6;

        when((payload).getJobInfo()).thenReturn(jobInfo4).thenReturn(jobInfo5)
                .thenReturn(jobInfo6);
        multiCall.callAndUse();

        verify(jobInfo).check();
        verify(jobInfo2).check();
        verify(jobInfo3).check();
    }

    @Test
    public void testReturnCallAndUse() {
        JobInfo jobInfo4 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo = jobInfo4;
        boolean apple = true;
        JobInfo jobInfo5 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo2 = jobInfo5;
        boolean grape = true;
        JobInfo jobInfo6 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo3 = jobInfo6;
        boolean orange = true;

        when((payload).getJobInfo()).thenReturn(jobInfo4).thenReturn(jobInfo5)
                .thenReturn(jobInfo6);
        when(jobInfo.isValid()).thenReturn(apple);
        when(jobInfo2.isValid()).thenReturn(grape);
        when(jobInfo3.isValid()).thenReturn(orange);

        boolean actual = multiCall.returnCallAndUse();

        assertTrue(actual);
    }

    @Test
    public void testCallNestedArg() {
        JobInfo jobInfo7 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo = jobInfo7;
        JobInfo jobInfo8 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo2 = jobInfo8;
        JobInfo jobInfo9 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo10 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo4 = jobInfo10;
        JobInfo jobInfo11 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo5 = jobInfo11;
        JobInfo jobInfo12 = Mockito.mock(JobInfo.class);

        when((payload).getJobInfo()).thenReturn(jobInfo7).thenReturn(jobInfo10);
        when((payload).getJobInfo(((jobInfo)))).thenReturn(jobInfo8);
        when((payload).getJobInfo(jobInfo2)).thenReturn(jobInfo9);
        when((payload).getJobInfo(jobInfo4)).thenReturn(jobInfo11);
        when((payload).getJobInfo(jobInfo5)).thenReturn(jobInfo12);
        multiCall.callNestedArg();
    }

    @Test
    public void testReturnCallNestedArg() {
        JobInfo jobInfo7 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo = jobInfo7;
        JobInfo jobInfo8 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo2 = jobInfo8;
        JobInfo jobInfo9 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo10 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo4 = jobInfo10;
        JobInfo jobInfo11 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo5 = jobInfo11;
        JobInfo jobInfo12 = Mockito.mock(JobInfo.class);
        JobInfo jobInfo6 = jobInfo12;

        when((payload).getJobInfo()).thenReturn(jobInfo7).thenReturn(jobInfo10);
        when((payload).getJobInfo(((jobInfo)))).thenReturn(jobInfo8);
        when((payload).getJobInfo(jobInfo2)).thenReturn(jobInfo9);
        when((payload).getJobInfo(jobInfo4)).thenReturn(jobInfo11);
        when((payload).getJobInfo(jobInfo5)).thenReturn(jobInfo12);

        JobInfo actual = multiCall.returnCallNestedArg();

        assertSame(jobInfo6, actual);
    }
}
