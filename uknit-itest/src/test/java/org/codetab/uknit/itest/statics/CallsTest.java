package org.codetab.uknit.itest.statics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.codetab.uknit.itest.statics.Model.Driver;
import org.codetab.uknit.itest.statics.Model.DriverWait;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class CallsTest {
    @InjectMocks
    private Calls calls;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testStaticCallInInit() throws Exception {
        Driver driver = Mockito.mock(Driver.class);
        String timeout = "10";
        DriverWait driverWait =
                new DriverWait(driver, Integer.parseInt(timeout));

        DriverWait actual = calls.staticCallInInit(driver, timeout);

        assertEquals(driverWait, actual);
    }
}
