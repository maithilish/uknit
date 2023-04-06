package org.codetab.uknit.itest.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class NullReturnTest {
    @InjectMocks
    private NullReturn nullReturn;

    @Mock
    private Connection connection;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInternalCallIfFlag() {
        boolean flag = true;
        String grape = "a";
        String apple = grape;

        String actual = nullReturn.internalCall(flag);

        assertEquals(apple, actual);
    }

    @Test
    public void testInternalCallElseFlag() {
        boolean flag = false;
        String grape = null;

        String actual = nullReturn.internalCall(flag);

        assertEquals(grape, actual);
    }

    @Test
    public void testSuperCallIfFlag() {
        boolean flag = true;
        Connection connection2 = connection;

        Connection actual = nullReturn.superCall(flag);

        assertSame(connection2, actual);
    }

    @Test
    public void testSuperCallElseFlag() {
        boolean flag = false;
        Connection connection3 = null;

        Connection actual = nullReturn.superCall(flag);

        assertEquals(connection3, actual);
    }

    @Test
    public void testSuperCallWithSuperIfFlag() {
        boolean flag = true;
        Connection connection2 = connection;

        Connection actual = nullReturn.superCallWithSuper(flag);

        assertSame(connection2, actual);
    }

    @Test
    public void testSuperCallWithSuperElseFlag() {
        boolean flag = false;
        Connection connection3 = null;

        Connection actual = nullReturn.superCallWithSuper(flag);

        assertEquals(connection3, actual);
    }
}
