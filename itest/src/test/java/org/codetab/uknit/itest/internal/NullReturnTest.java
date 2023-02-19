package org.codetab.uknit.itest.internal;

import static org.junit.jupiter.api.Assertions.assertNull;

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
    public void testFooIfFlag() {

        Connection actual = nullReturn.foo();

        assertNull(actual);
    }

    @Test
    public void testFooElseFlag() {

        Connection actual = nullReturn.foo();

        assertNull(actual);
    }
}
