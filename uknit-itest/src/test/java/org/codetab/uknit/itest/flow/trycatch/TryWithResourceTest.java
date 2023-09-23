package org.codetab.uknit.itest.flow.trycatch;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;

import org.codetab.uknit.itest.flow.trycatch.Model.Io;
import org.codetab.uknit.itest.flow.trycatch.Model.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class TryWithResourceTest {
    @InjectMocks
    private TryWithResource tryWithResource;

    @Mock
    private Logger log;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTryWithResourceTry() throws Exception {
        Io io = Mockito.mock(Io.class);
        BufferedReader br = Mockito.mock(BufferedReader.class);
        String line = "Foo";
        String apple = "Bar";

        when(io.bufferedFileReader("foo.txt")).thenReturn(br);
        when(br.readLine()).thenReturn(line).thenReturn(null);
        tryWithResource.tryWithResource(io);

        verify(log).debug("Line =>" + line);
        verify(log, never()).debug("catch block" + apple);
    }

    @Test
    public void testTryWithResourceTryCatchIOException() throws Exception {
        Io io = Mockito.mock(Io.class);
        BufferedReader br = Mockito.mock(BufferedReader.class);
        String line = "Foo";
        String apple = null;

        when(io.bufferedFileReader("foo.txt")).thenReturn(br);
        when(br.readLine()).thenThrow(IOException.class);

        tryWithResource.tryWithResource(io);

        verify(log, never()).debug("Line =>" + line);
        verify(log).debug("catch block" + apple);
    }
}
