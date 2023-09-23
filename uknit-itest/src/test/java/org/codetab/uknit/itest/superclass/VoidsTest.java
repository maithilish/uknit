package org.codetab.uknit.itest.superclass;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.management.MBeanServer;
import javax.management.ObjectInstance;
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

    // CHECKSTYLE:OFF
    @Mock
    private MBeanServer SERVER;
    // CHECKSTYLE:ON

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

        verify(SERVER, never()).registerMBean(object, objectName);
    }

    @Test
    public void testRegisterMBeanIfServerTryCatchLinkageError()
            throws Exception {
        Object object = Mockito.mock(Object.class);
        voids.registerMBean(object);

        verify(SERVER, never()).registerMBean(object, objectName);
    }

    @Test
    public void testRegisterMBeanIfServerTryCatchException() throws Exception {
        Object object = Mockito.mock(Object.class);
        voids.registerMBean(object);

        verify(SERVER, never()).registerMBean(object, objectName);
    }

    @Test
    public void testRegisterMBeanElseServerTry() throws Exception {
        Object object = Mockito.mock(Object.class);
        ObjectInstance objectInstance = Mockito.mock(ObjectInstance.class);

        when(SERVER.registerMBean(object, objectName))
                .thenReturn(objectInstance);
        voids.registerMBean(object);

        // fail("unable to assert, STEPIN");
    }
}
