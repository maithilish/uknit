package org.codetab.uknit.itest.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class LostTrackATest {
    @InjectMocks
    private LostTrackA lostTrackA;

    @Mock
    private Synchronizer synchronizer;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPropertyTry() {
        String key = "Foo";
        Synchronizer sync = synchronizer;
        Synchronizer synchronizer3 = sync != null ? sync : sync;
        Synchronizer synchronizer4 = sync != null ? sync : sync;
        Object apple = "foo";

        Object actual = lostTrackA.getProperty(key);

        assertEquals(apple, actual);

        verify(synchronizer3).beginRead();
        verify(synchronizer4).endRead();
    }

    @Test
    public void testGetSynchronizer() {
        Synchronizer sync = synchronizer;
        Synchronizer synchronizer2 = sync != null ? sync : sync;

        Synchronizer actual = lostTrackA.getSynchronizer();

        assertSame(synchronizer2, actual);
    }

    @Test
    public void testBeginRead() {
        boolean optimize = true;
        Synchronizer sync = synchronizer;
        Synchronizer synchronizer3 = sync != null ? sync : sync;
        lostTrackA.beginRead(optimize);

        verify(synchronizer3).beginRead();
    }

    @Test
    public void testEndRead() {
        Synchronizer sync = synchronizer;
        Synchronizer synchronizer3 = sync != null ? sync : sync;
        lostTrackA.endRead();

        verify(synchronizer3).endRead();
    }
}
