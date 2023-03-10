package org.codetab.uknit.itest.internal;

import javax.management.ObjectName;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class VoidsTest {
    @InjectMocks
    private Voids voids;

    @Mock
    private ObjectName objectName;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterMBeanIfServerTry() throws Exception {
        Object object = Mockito.mock(Object.class);
        voids.registerMBean(object);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testRegisterMBeanIfServerTryCatchLinkageError()
            throws Exception {
        Object object = Mockito.mock(Object.class);
        voids.registerMBean(object);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testRegisterMBeanIfServerTryCatchException() throws Exception {
        Object object = Mockito.mock(Object.class);
        voids.registerMBean(object);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testRegisterMBeanElseServerTry() throws Exception {
        Object object = Mockito.mock(Object.class);
        voids.registerMBean(object);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testCallSimilarlyNamedMethods() {
        voids.callSimilarlyNamedMethods();

        // fail("unable to assert, STEPIN");
    }
}
