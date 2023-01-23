package org.codetab.uknit.itest.statics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class FieldMethodCallTest {
    @InjectMocks
    private FieldMethodCall fieldMethodCall;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTimer() {
        Object clz = Mockito.mock(Object.class);
        String[] names = {};
        Timer timer = null;

        Timer actual = fieldMethodCall.getTimer(clz, names);

        assertEquals(timer, actual);
    }

    @Test
    public void testGetMeter() {
        Object clz = Mockito.mock(Object.class);
        String[] names = {""};
        Meter meter = null;

        Meter actual = fieldMethodCall.getMeter(clz, names);

        assertEquals(meter, actual);
    }

    @Test
    public void testRegisterGuage() throws Exception {
        File value = Mockito.mock(File.class);
        Object clz = Mockito.mock(Object.class);
        String[] names = {};
        fieldMethodCall.registerGuage(value, clz, names);

        // fail("unable to assert, STEPIN");
    }
}
