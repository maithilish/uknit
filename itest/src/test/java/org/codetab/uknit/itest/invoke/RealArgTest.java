package org.codetab.uknit.itest.invoke;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class RealArgTest {
    @InjectMocks
    private RealArg realArg;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConsumer() {
        String id = "Foo";
        Map<String, byte[]> map = new HashMap<>();
        Some some = new Some("real");

        Some actual = realArg.consumer(id, map);

        assertEquals(some, actual);
    }
}
