package org.codetab.uknit.itest.misuse;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class HashCodeTest {
    @InjectMocks
    private HashCode hashCode;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFoo() {
        File file1 = Mockito.mock(File.class);
        File file2 = Mockito.mock(File.class);

        boolean actual = hashCode.foo(file1, file2);

        assertFalse(actual);
    }
}
