package org.codetab.uknit.itest.statics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class StaticFieldMethodCallTest {
    @InjectMocks
    private StaticFieldMethodCall staticFieldMethodCall;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTimer() {
        Object clz = Mockito.mock(Object.class);
        String names = "Foo";

        @SuppressWarnings("unused")
        Timer actual = staticFieldMethodCall.getTimer(clz, names);
    }

    @Test
    public void testGetMeter() {
        Object clz = Mockito.mock(Object.class);
        String names = "Foo";

        @SuppressWarnings("unused")
        Meter actual = staticFieldMethodCall.getMeter(clz, names);
    }

    @Test
    public void testRegisterGuage() {
        Integer value = 5;
        Object clz = Mockito.mock(Object.class);
        String names = "Foo";
        staticFieldMethodCall.registerGuage(value, clz, names);
    }
}
