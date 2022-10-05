package org.codetab.uknit.itest.trycatch;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;

import org.codetab.uknit.itest.trycatch.TryWithResource.Io;
import org.codetab.uknit.itest.trycatch.TryWithResource.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TryWithResourceTest {
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
        IOException e = Mockito.mock(IOException.class);
        String kiwi = "Bar";

        when(io.bufferedFileReader("foo.txt")).thenReturn(br);
        when(br.readLine()).thenReturn(line).thenReturn(null);
        tryWithResource.tryWithResource(io);

        verify(log).debug("Line =>" + line);
        verify(e, never()).getMessage();
        verify(log, never()).debug("catch block" + kiwi);
    }

    @Test
    public void testTryWithResourceTryCatchIOException() throws Exception {
        Io io = Mockito.mock(Io.class);
        BufferedReader br = Mockito.mock(BufferedReader.class);
        String line = "Foo";
        IOException e = Mockito.mock(IOException.class);
        String kiwi = "Bar";

        when(io.bufferedFileReader("foo.txt")).thenReturn(br);
        when(br.readLine()).thenThrow(e);
        when(e.getMessage()).thenReturn(kiwi);
        tryWithResource.tryWithResource(io);

        verify(log, never()).debug("Line =>" + line);
        verify(log).debug("catch block" + kiwi);
    }
}
