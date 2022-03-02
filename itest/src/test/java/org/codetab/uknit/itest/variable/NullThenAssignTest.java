package org.codetab.uknit.itest.variable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.concurrent.BlockingQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class NullThenAssignTest {
    @InjectMocks
    private NullThenAssign nullThenAssign;

    @Mock
    private BlockingQueue<PrintPayload> q;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRun() throws Exception {
        PrintPayload printPayload = Mockito.mock(PrintPayload.class);
        String path = "Foo";
        String grape = path;

        when(q.take()).thenReturn(printPayload);
        when(printPayload.getId()).thenReturn(path);

        String actual = nullThenAssign.run();

        assertEquals(grape, actual);
    }
}
