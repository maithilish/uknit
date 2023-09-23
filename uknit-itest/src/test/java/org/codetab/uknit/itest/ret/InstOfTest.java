package org.codetab.uknit.itest.ret;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class InstOfTest {
    @InjectMocks
    private InstOf instOf;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRet() {
        Object x = Mockito.mock(Object.class);

        boolean actual = instOf.ret(x);

        assertFalse(actual);
    }

    @Test
    public void testRet2() {

        boolean actual = instOf.ret();

        assertFalse(actual);
    }

    @Test
    public void testCall() {

        boolean actual = instOf.call();

        assertFalse(actual);
    }

    @Test
    public void testCallStatic() {

        boolean actual = instOf.callStatic();

        assertFalse(actual);
    }
}
