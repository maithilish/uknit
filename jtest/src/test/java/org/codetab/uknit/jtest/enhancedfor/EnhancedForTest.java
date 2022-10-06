package org.codetab.uknit.jtest.enhancedfor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class EnhancedForTest {
    @InjectMocks
    private EnhancedFor enhancedFor;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testForeach() {
        List<String> names = new ArrayList<>();
        String n = "Foo";
        String name = n;
        names.add(n);

        String actual = enhancedFor.foreach(names);

        assertEquals(name, actual);
    }
}
