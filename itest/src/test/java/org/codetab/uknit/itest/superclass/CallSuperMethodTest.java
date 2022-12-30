package org.codetab.uknit.itest.superclass;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class CallSuperMethodTest {
    @InjectMocks
    private CallSuperMethod callSuperMethod;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSetLicensePlate() {
        String license = "Foo";
        callSuperMethod.setLicensePlate(license);
    }
}
