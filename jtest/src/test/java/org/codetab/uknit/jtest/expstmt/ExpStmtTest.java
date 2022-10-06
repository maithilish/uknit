package org.codetab.uknit.jtest.expstmt;

import static org.mockito.Mockito.verify;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ExpStmtTest {
    @InjectMocks
    private ExpStmt expStmt;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFoo() {
        AtomicInteger counter = Mockito.mock(AtomicInteger.class);
        expStmt.foo(counter);

        verify(counter).decrementAndGet();
    }
}
