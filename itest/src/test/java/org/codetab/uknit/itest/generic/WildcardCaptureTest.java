package org.codetab.uknit.itest.generic;

import static org.mockito.Mockito.verify;

import java.io.PrintWriter;
import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class WildcardCaptureTest {
    @InjectMocks
    private WildcardCapture wildcardCapture;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testWrite() {
        PrintWriter out = Mockito.mock(PrintWriter.class);
        Object value = Mockito.mock(List.class);
        wildcardCapture.write(out, value);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<Consumer<? super Object>> captorA =
                ArgumentCaptor.forClass(Consumer.class);

        verify(((List<?>) value)).forEach(captorA.capture());
    }
}
