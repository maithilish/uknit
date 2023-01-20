package org.codetab.uknit.itest.brace.im;

import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Locale;

import org.codetab.uknit.itest.brace.im.Model.Holder;
import org.codetab.uknit.itest.brace.im.Model.Provider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class VarConflictTest {
    @InjectMocks
    private VarConflict varConflict;

    @Mock
    private Provider provider;
    @Mock
    private Holder otherHolder;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCallSameTwice() {
        Holder holder = Mockito.mock(Holder.class);
        Instant instant = Mockito.mock(Instant.class);
        Instant other = Mockito.mock(Instant.class);
        int apple = 1;
        Instant instant2 = Mockito.mock(Instant.class);
        Instant other2 = Mockito.mock(Instant.class);
        int grape = 1;

        when((provider).getHolder()).thenReturn(holder);
        when((holder).getInstant()).thenReturn(instant).thenReturn(instant2);
        when((otherHolder).getInstant()).thenReturn(other).thenReturn(other2);
        when((instant).compareTo((other))).thenReturn(apple);
        when(instant2.compareTo(other2)).thenReturn(grape);
        varConflict.callSameTwice();
    }

    @Test
    public void testCallTwoMethodsOfSameType() {
        Holder holder = Mockito.mock(Holder.class);
        Instant instant = Mockito.mock(Instant.class);
        Instant other = Mockito.mock(Instant.class);
        int apple = 1;
        Instant instant2 = Mockito.mock(Instant.class);
        Instant other2 = Mockito.mock(Instant.class);
        int grape = 1;

        when((provider).getHolder()).thenReturn(holder);
        when((holder).getInstant()).thenReturn(instant).thenReturn(instant2);
        when((otherHolder).getInstant()).thenReturn(other);
        when((instant).compareTo((other))).thenReturn(apple);
        when(((otherHolder)).getInstant()).thenReturn(other2);
        when(instant2.compareTo(other2)).thenReturn(grape);
        varConflict.callTwoMethodsOfSameType();
    }

    @Test
    public void testCallTwoMethodsOfDiffTypes() {
        Holder holder = Mockito.mock(Holder.class);
        Instant instant = Mockito.mock(Instant.class);
        Instant other = Mockito.mock(Instant.class);
        int apple = 1;
        Locale locale = Mockito.mock(Locale.class);
        Locale other2 = Mockito.mock(Locale.class);
        String grape = "Foo";

        when((provider).getHolder()).thenReturn(holder);
        when((holder).getInstant()).thenReturn(instant);
        when((otherHolder).getInstant()).thenReturn(other);
        when((instant).compareTo((other))).thenReturn(apple);
        when((holder).getLocale()).thenReturn(locale);
        when((otherHolder).getLocale()).thenReturn(other2);
        when(((locale)).getDisplayCountry(other2)).thenReturn(grape);
        varConflict.callTwoMethodsOfDiffTypes();
    }
}
