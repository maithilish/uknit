package org.codetab.uknit.itest.invoke;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class InferredVarTest {
    @InjectMocks
    private InferredVar inferredVar;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAppend() {
        StringBuilder s1 = Mockito.mock(StringBuilder.class);
        StringBuilder s2 = Mockito.mock(StringBuilder.class);
        File file = Mockito.mock(File.class);
        String apple = "Foo";
        StringBuilder grape = Mockito.mock(StringBuilder.class);
        StringBuilder orange = Mockito.mock(StringBuilder.class);

        when(file.getName()).thenReturn(apple);
        when(s2.append(apple.toLowerCase())).thenReturn(grape);
        when(s1.append(grape)).thenReturn(orange);

        StringBuilder actual = inferredVar.append(s1, s2, file);

        assertSame(orange, actual);
    }
}
