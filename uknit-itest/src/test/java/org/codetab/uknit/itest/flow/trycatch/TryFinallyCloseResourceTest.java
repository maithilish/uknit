package org.codetab.uknit.itest.flow.trycatch;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.codetab.uknit.itest.flow.trycatch.Model.Io;
import org.codetab.uknit.itest.flow.trycatch.Model.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class TryFinallyCloseResourceTest {
    @InjectMocks
    private TryFinallyCloseResource tryFinallyCloseResource;

    @Mock
    private Logger log;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTryFinallyCloseResourceTryTryIfBr() throws Exception {
        Io io = Mockito.mock(Io.class);
        BufferedReader br = Mockito.mock(BufferedReader.class);
        String line = "Foo";
        String apple = "Bar";
        String grape = "Baz";

        when(io.bufferedFileReader("foo.txt")).thenReturn(br);
        when(br.readLine()).thenReturn(line).thenReturn(null);
        tryFinallyCloseResource.tryFinallyCloseResource(io);

        verify(log).debug("Entering try block");
        verify(log).debug("Line =>" + line);
        verify(log, never()).debug("IOException in try block =>" + apple);
        verify(log).debug("Entering finally block");
        verify(br).close();
        verify(log, never()).debug("IOException in finally block =>" + grape);
    }

    @Test
    public void testTryFinallyCloseResourceTryTryElseBr() throws Exception {
        Io io = Mockito.mock(Io.class);
        BufferedReader br = Mockito.mock(BufferedReader.class);
        String line = "Foo";
        String apple = "Bar";
        String grape = "Baz";

        when(io.bufferedFileReader("foo.txt"))
                .thenThrow(FileNotFoundException.class);

        tryFinallyCloseResource.tryFinallyCloseResource(io);

        verify(log).debug("Entering try block");
        verify(log, never()).debug("Line =>" + line);
        verify(log, never()).debug("IOException in try block =>" + apple);
        verify(log).debug("Entering finally block");
        verify(br, never()).close();
        verify(log, never()).debug("IOException in finally block =>" + grape);
    }

    @Test
    public void testTryFinallyCloseResourceTryTryCatchIOException()
            throws Exception {
        Io io = Mockito.mock(Io.class);
        BufferedReader br = Mockito.mock(BufferedReader.class);
        String line = "Foo";
        String apple = "Bar";
        String grape = "Baz";
        IOException ex = Mockito.mock(IOException.class);

        when(io.bufferedFileReader("foo.txt")).thenReturn(br);
        when(br.readLine()).thenThrow(IOException.class);
        when(ex.getMessage()).thenReturn(grape);
        doThrow(ex).when(br).close();
        tryFinallyCloseResource.tryFinallyCloseResource(io);

        verify(log).debug("Entering try block");
        verify(log, never()).debug("Line =>" + line);
        verify(log, never()).debug("IOException in try block =>" + apple);
        verify(log).debug("Entering finally block");
        verify(log).debug("IOException in finally block =>" + grape);
    }

    @Test
    public void testTryFinallyCloseResourceTryCatchIOExceptionTry()
            throws Exception {
        Io io = Mockito.mock(Io.class);
        BufferedReader br = Mockito.mock(BufferedReader.class);
        String line = "Foo";
        String apple = null;
        String grape = "Baz";

        when(io.bufferedFileReader("foo.txt")).thenReturn(br);
        when(br.readLine()).thenThrow(IOException.class);
        tryFinallyCloseResource.tryFinallyCloseResource(io);

        verify(log).debug("Entering try block");
        verify(log, never()).debug("Line =>" + line);
        verify(log).debug("IOException in try block =>" + apple);
        verify(log).debug("Entering finally block");
        verify(br).close();
        verify(log, never()).debug("IOException in finally block =>" + grape);
    }
}
