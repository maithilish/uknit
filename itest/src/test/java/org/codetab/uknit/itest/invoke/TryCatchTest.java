package org.codetab.uknit.itest.invoke;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.invoke.TryCatch.DataDao;
import org.codetab.uknit.itest.invoke.TryCatch.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class TryCatchTest {
    @InjectMocks
    private TryCatch tryCatch;

    @Mock
    private DataDao dataDao;
    @Mock
    private Document document;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCallSameMethodInTryAndCatchTry() {
        long apple = 1L;
        Object grape = Mockito.mock(Object.class);
        long orange = 1L;

        when(document.getLocatorId()).thenReturn(apple);
        when(dataDao.get(apple)).thenReturn(grape);
        tryCatch.callSameMethodInTryAndCatch();

        verify(document, times(1)).getLocatorId();
        verify(dataDao, never()).delete(orange);
    }

    @Test
    public void testCallSameMethodInTryAndCatchTryCatchIllegalStateException() {
        long apple = 1L;
        // Object grape = Mockito.mock(Object.class);
        long orange = 1L;

        when(document.getLocatorId()).thenReturn(apple).thenReturn(orange);
        when(dataDao.get(apple)).thenThrow(IllegalStateException.class);
        tryCatch.callSameMethodInTryAndCatch();

        verify(dataDao).delete(orange);
    }
}
