package org.codetab.uknit.itest.anon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class AnonNoCaptureTest {
    @InjectMocks
    private AnonNoCapture anonNoCapture;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReturnAnon() {

        @SuppressWarnings("unused")
        Thread actual = anonNoCapture.returnAnon();

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testUseAnon() {
        anonNoCapture.useAnon();

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testAssignAnon() {

        @SuppressWarnings("unused")
        Predicate<String> actual = anonNoCapture.assignAnon();

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testAnonFromInterface() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        String name = "Foo";
        anonNoCapture.anonFromInterface(sb, name);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testReturnAnonFromInterface() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        String name = "Foo";

        @SuppressWarnings("unused")
        Runnable actual = anonNoCapture.returnAnonFromInterface(sb, name);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testUseAssignedAnonInArg() {
        Button button = Mockito.mock(Button.class);
        anonNoCapture.useAssignedAnonInArg(button);

        verify(button).addActionListener(any(ActionListener.class));
    }

    @Test
    public void testAnonInArg() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        StringBuilder stringBuilder = Mockito.mock(StringBuilder.class);

        when(sb.append("finish")).thenReturn(stringBuilder);
        anonNoCapture.anonInArg(sb);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testAnonInArgButton() {
        Button button = Mockito.mock(Button.class);
        anonNoCapture.anonInArg(button);

        verify(button).addActionListener(any(ActionListener.class));
    }
}
