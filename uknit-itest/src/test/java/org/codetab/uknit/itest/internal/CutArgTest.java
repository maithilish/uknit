package org.codetab.uknit.itest.internal;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class CutArgTest {
    @InjectMocks
    private CutArg cutArg;

    @Mock
    private Date date;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCopy() {
        CutArg target = Mockito.spy(CutArg.class);
        cutArg.copy(target);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testCopyCutArgDate() {
        CutArg target = Mockito.spy(CutArg.class);
        Date date = Mockito.mock(Date.class);
        cutArg.copy(target, date);

        // fail("unable to assert, STEPIN");
    }
}
