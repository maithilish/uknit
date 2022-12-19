package org.codetab.uknit.itest.superclass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SuperCallsTest {
    @InjectMocks
    private SuperCalls superCalls;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignFromSuperCreate() {
        Bar bar2 = new Bar();
        Bar bar = bar2;

        Bar actual = superCalls.assignFromSuperCreate();

        assertEquals(bar, actual);
    }

    @Test
    public void testReturnFromSuperCreate() {
        Bar bar = new Bar();

        Bar actual = superCalls.returnFromSuperCreate();

        assertEquals(bar, actual);
    }

    @Test
    public void testAssignFromSuperCreateAndMock() throws Exception {
        String name = "Foo";
        Bar bar2 = new Bar(name);
        Bar bar = bar2;

        Bar actual = superCalls.assignFromSuperCreateAndMock(name);

        assertEquals(bar, actual);
    }

    @Test
    public void testReturnFromSuperCreateAndMock() throws Exception {
        String name = "Foo";
        Bar bar = new Bar(name);

        Bar actual = superCalls.returnFromSuperCreateAndMock(name);

        assertEquals(bar, actual);
    }

    @Test
    public void testAssignFromMock() throws Exception {
        Factory factory = Mockito.mock(Factory.class);
        String name = "Foo";
        Bar bar2 = Mockito.mock(Bar.class);
        Bar bar = bar2;

        when(factory.instance(name)).thenReturn(bar2);

        Bar actual = superCalls.assignFromMock(factory, name);

        assertSame(bar, actual);
    }

    @Test
    public void testReturnFromMock() throws Exception {
        Factory factory = Mockito.mock(Factory.class);
        String name = "Foo";
        Bar bar = Mockito.mock(Bar.class);

        when(factory.instance(name)).thenReturn(bar);

        Bar actual = superCalls.returnFromMock(factory, name);

        assertSame(bar, actual);
    }
}
