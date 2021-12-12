package org.codetab.uknit.itest.nest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

public class AnonymousClassTest {
    @InjectMocks
    private AnonymousClass anonymousClass;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAnonymousReturn() {
        // FileFilter fileFilter = STEPIN;
        File file = Mockito.mock(File.class);

        FileFilter actual = anonymousClass.anonymousReturn(file);

        when(file.isHidden()).thenReturn(true).thenReturn(false);
        assertTrue(actual.accept(file));
        assertFalse(actual.accept(file));
    }

    @Test
    public void testAnonymousArg() {
        File[] hiddenFiles = {};
        File file = Mockito.mock(File.class);

        when(file.listFiles(any(FileFilter.class))).thenReturn(hiddenFiles);

        File[] actual = anonymousClass.anonymousArg(file);

        assertArrayEquals(hiddenFiles, actual);

        ArgumentCaptor<FileFilter> argcA =
                ArgumentCaptor.forClass(FileFilter.class);
        verify(file).listFiles(argcA.capture());

        FileFilter arg = argcA.getValue();
        when(file.isHidden()).thenReturn(false).thenReturn(true);
        assertFalse(arg.accept(file));
        assertTrue(arg.accept(file));
    }

    @Test
    public void testMockFilter() {
        File[] hiddenFiles = {};
        File file = Mockito.mock(File.class);
        FileFilter filter = Mockito.mock(FileFilter.class);

        when(file.listFiles(filter)).thenReturn(hiddenFiles);

        File[] actual = anonymousClass.mockFilter(file, filter);

        assertArrayEquals(hiddenFiles, actual);
    }

    @Test
    public void testAnonymousInVerify() {
        File file = Mockito.mock(File.class);
        anonymousClass.anonymousInVerify(file);
        ArgumentCaptor<FileFilter> argcA =
                ArgumentCaptor.forClass(FileFilter.class);

        verify(file).listFiles(argcA.capture());

        FileFilter arg = argcA.getValue();
        when(file.isHidden()).thenReturn(false).thenReturn(true);
        assertFalse(arg.accept(file));
        assertTrue(arg.accept(file));
    }
}
