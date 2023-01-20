package org.codetab.uknit.itest.initializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class MockTest {
    @InjectMocks
    private Mock mock;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignMockReturnsMock() throws Exception {
        File file = Mockito.mock(File.class);
        File cFile = Mockito.mock(File.class);

        when(file.getCanonicalFile()).thenReturn(cFile);

        File actual = mock.assignMockReturnsMock(file);

        assertSame(cFile, actual);
    }

    @Test
    public void testReturnMockReturnsMock() throws Exception {
        File file = Mockito.mock(File.class);
        File file2 = Mockito.mock(File.class);

        when(file.getCanonicalFile()).thenReturn(file2);

        File actual = mock.returnMockReturnsMock(file);

        assertSame(file2, actual);
    }

    @Test
    public void testAssignMockReturnsMock2() throws Exception {
        File file = Mockito.mock(File.class);
        File file2 = Mockito.mock(File.class);
        File cFile = Mockito.mock(File.class);

        when(file.getCanonicalFile()).thenReturn(file2);
        when(file2.getCanonicalFile()).thenReturn(cFile);

        File actual = mock.assignMockReturnsMock2(file);

        assertSame(cFile, actual);
    }

    @Test
    public void testReturnMockReturnsMock2() throws Exception {
        File file = Mockito.mock(File.class);
        File file2 = Mockito.mock(File.class);
        File file3 = Mockito.mock(File.class);

        when(file.getCanonicalFile()).thenReturn(file2);
        when(file2.getCanonicalFile()).thenReturn(file3);

        File actual = mock.returnMockReturnsMock2(file);

        assertSame(file3, actual);
    }

    @Test
    public void testAssignMockReturnsMockInArgs() throws Exception {
        File file = Mockito.mock(File.class);
        File file2 = Mockito.mock(File.class);
        File file3 = Mockito.mock(File.class);
        int value = 1;

        when(file.getCanonicalFile()).thenReturn(file2);
        when(file2.getAbsoluteFile()).thenReturn(file3);
        when(file.compareTo(file3)).thenReturn(value);

        int actual = mock.assignMockReturnsMockInArgs(file);

        assertEquals(value, actual);
    }

    @Test
    public void testReturnMockReturnsMockInArgs() throws Exception {
        File file = Mockito.mock(File.class);
        File file2 = Mockito.mock(File.class);
        File file3 = Mockito.mock(File.class);
        int apple = 1;

        when(file.getCanonicalFile()).thenReturn(file2);
        when(file2.getAbsoluteFile()).thenReturn(file3);
        when(file.compareTo(file3)).thenReturn(apple);

        int actual = mock.returnMockReturnsMockInArgs(file);

        assertEquals(apple, actual);
    }

    @Test
    public void testAssignMockReturnsReal() throws Exception {
        File file = Mockito.mock(File.class);
        File file2 = Mockito.mock(File.class);
        String absPath = "Foo";

        when(file.getCanonicalFile()).thenReturn(file2);
        when(file2.getAbsolutePath()).thenReturn(absPath);

        String actual = mock.assignMockReturnsReal(file);

        assertEquals(absPath, actual);
    }

    @Test
    public void testReturnMockReturnsReal() throws Exception {
        File file = Mockito.mock(File.class);
        File file2 = Mockito.mock(File.class);
        String apple = "Foo";

        when(file.getCanonicalFile()).thenReturn(file2);
        when(file2.getAbsolutePath()).thenReturn(apple);

        String actual = mock.returnMockReturnsReal(file);

        assertEquals(apple, actual);
    }

    @Test
    public void testAssignMockReturnsRealInArgs() throws Exception {
        File file = Mockito.mock(File.class);
        String apple = "Foo";
        File file2 = Mockito.mock(File.class);
        String grape = "Bar";
        int value = apple.compareTo(grape);

        when(file.getAbsolutePath()).thenReturn(apple);
        when(file.getCanonicalFile()).thenReturn(file2);
        when(file2.getAbsolutePath()).thenReturn(grape);

        int actual = mock.assignMockReturnsRealInArgs(file);

        assertEquals(value, actual);
    }

    @Test
    public void testReturnMockReturnsRealInArgs() throws Exception {
        File file = Mockito.mock(File.class);
        String apple = "Foo";
        File file2 = Mockito.mock(File.class);
        String grape = "Bar";
        int orange = apple.compareTo(grape);

        when(file.getAbsolutePath()).thenReturn(apple);
        when(file.getCanonicalFile()).thenReturn(file2);
        when(file2.getAbsolutePath()).thenReturn(grape);

        int actual = mock.returnMockReturnsRealInArgs(file);

        assertEquals(orange, actual);
    }
}
