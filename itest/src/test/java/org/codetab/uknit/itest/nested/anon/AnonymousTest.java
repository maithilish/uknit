package org.codetab.uknit.itest.nested.anon;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileFilter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class AnonymousTest {
    @InjectMocks
    private Anonymous anonymous;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignAnon() {
        File file = Mockito.mock(File.class);

        FileFilter actual = anonymous.assignAnon(file);

        // STEPIN
        when(file.isHidden()).thenReturn(true);
        assertTrue(actual.accept(file));
    }

    @Test
    public void testReturnAnon() {
        File file = Mockito.mock(File.class);

        FileFilter actual = anonymous.returnAnon(file);

        // STEPIN
        when(file.isHidden()).thenReturn(true);
        assertTrue(actual.accept(file));
    }

    @Test
    public void testAnonArg() {
        File file = Mockito.mock(File.class);
        File[] hiddenFiles = {new File("foo")};

        when(file.listFiles(any(FileFilter.class))).thenReturn(hiddenFiles);

        File[] actual = anonymous.anonArg(file);

        assertArrayEquals(hiddenFiles, actual);

        ArgumentCaptor<FileFilter> captorA =
                ArgumentCaptor.forClass(FileFilter.class);

        verify(file).listFiles(captorA.capture());

        when(file.isHidden()).thenReturn(true);
        assertTrue(captorA.getValue().accept(file));
    }

    @Test
    public void testAnonVarAsArg() {
        File file = Mockito.mock(File.class);
        File[] hiddenFiles = {new File("foo")};

        when(file.listFiles(any(FileFilter.class))).thenReturn(hiddenFiles);

        File[] actual = anonymous.anonVarAsArg(file);

        assertArrayEquals(hiddenFiles, actual);

        ArgumentCaptor<FileFilter> captorA =
                ArgumentCaptor.forClass(FileFilter.class);

        verify(file).listFiles(captorA.capture());
    }

    @Test
    public void testVerifyAnon() {
        File file = Mockito.mock(File.class);
        File[] apple = {new File("foo")};

        when(file.listFiles(any(FileFilter.class))).thenReturn(apple);
        anonymous.verifyAnon(file);

        ArgumentCaptor<FileFilter> captorA =
                ArgumentCaptor.forClass(FileFilter.class);

        verify(file).listFiles(captorA.capture());
    }

    @Test
    public void testVerifyAnonVar() {
        File file = Mockito.mock(File.class);
        File[] apple = {new File("foo")};

        when(file.listFiles(any(FileFilter.class))).thenReturn(apple);
        anonymous.verifyAnonVar(file);

        ArgumentCaptor<FileFilter> captorA =
                ArgumentCaptor.forClass(FileFilter.class);

        verify(file).listFiles(captorA.capture());
    }

    @Test
    public void testMockFunctionalInterface() {
        File file = Mockito.mock(File.class);
        FileFilter filter = Mockito.mock(FileFilter.class);
        File[] hiddenFiles = {new File("foo")};

        when(file.listFiles(filter)).thenReturn(hiddenFiles);

        File[] actual = anonymous.mockFunctionalInterface(file, filter);

        assertArrayEquals(hiddenFiles, actual);
    }
}
