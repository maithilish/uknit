package org.codetab.uknit.itest.anon;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class AnonTest {
    @InjectMocks
    private Anon anon;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReturnAnon() {

        @SuppressWarnings("unused")
        Thread actual = anon.returnAnon();

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testUseAnon() {
        anon.useAnon();

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testAssignAnon() {

        @SuppressWarnings("unused")
        Predicate<String> actual = anon.assignAnon();

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testAnonFromInterface() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        String name = "Foo";
        anon.anonFromInterface(sb, name);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testReturnAnonFromInterface() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        String name = "Foo";

        @SuppressWarnings("unused")
        Runnable actual = anon.returnAnonFromInterface(sb, name);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testUseAssignedAnonInArg() {
        Button button = Mockito.mock(Button.class);
        anon.useAssignedAnonInArg(button);

        ArgumentCaptor<ActionListener> captorA =
                ArgumentCaptor.forClass(ActionListener.class);

        verify(button).addActionListener(captorA.capture());
    }

    @Test
    public void testAnonInArg() {
        StringBuilder sb = Mockito.mock(StringBuilder.class);
        StringBuilder stringBuilder = Mockito.mock(StringBuilder.class);

        when(sb.append("finish")).thenReturn(stringBuilder);
        anon.anonInArg(sb);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testAnonInArgButton() {
        Button button = Mockito.mock(Button.class);
        anon.anonInArg(button);

        ArgumentCaptor<ActionListener> captorA =
                ArgumentCaptor.forClass(ActionListener.class);

        verify(button).addActionListener(captorA.capture());
    }
}
