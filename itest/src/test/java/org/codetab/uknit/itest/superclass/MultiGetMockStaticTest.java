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

public class MultiGetMockStaticTest {
    @InjectMocks
    private MultiGetMockStatic multiGetMockStatic;

    @Mock
    private Payload payload;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetStaticMulti() {
        long grape = 1L;

        long actual = multiGetMockStatic.getStaticMulti();

        assertEquals(grape, actual);
    }

    @Test
    public void testGetStaticMultiWithSuper() {
        JobInfo jobInfo = Mockito.mock(JobInfo.class);
        JobInfo jobInfo2 = Mockito.mock(JobInfo.class);
        long apple = 1L;
        JobInfo jobInfo3 = Mockito.mock(JobInfo.class);
        long grape = 1L;

        when(jobInfo2.getId()).thenReturn(apple);
        when(jobInfo3.getId()).thenReturn(grape);

        long actual = multiGetMockStatic.getStaticMultiWithSuper();

        assertEquals(grape, actual);
        verify(jobInfo).setId(1L);
    }

    @Test
    public void testGetStaticMultiStep() {
        Step step = Mockito.mock(Step.class);
        long grape = 1L;

        long actual = multiGetMockStatic.getStaticMulti(step);

        assertEquals(grape, actual);
    }
}
