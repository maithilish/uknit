package org.codetab.uknit.itest.misuse;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class EqualsTest {
    @InjectMocks
    private Equals equals;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFooIfFile1Equals() {
        File file1 = Mockito.mock(File.class);
        File file2 = Mockito.mock(File.class);

        boolean actual = equals.foo(file1, file2);

        assertFalse(actual);
    }

    @Test
    public void testFooElseFile1Equals() {
        File file1 = Mockito.mock(File.class);
        File file2 = Mockito.mock(File.class);

        boolean actual = equals.foo(file1, file2);

        assertFalse(actual);
    }
}
