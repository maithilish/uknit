package org.codetab.uknit.itest.internal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class VarConflictTest {
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
    public void testCompare() {
        Holder holder = Mockito.mock(Holder.class);
        Date date = Mockito.mock(Date.class);
        Date other = Mockito.mock(Date.class);
        Locale locale = Mockito.mock(Locale.class);
        Locale otherApple = Mockito.mock(Locale.class);

        when(provider.getHolder()).thenReturn(holder);
        when(holder.getDate()).thenReturn(date);
        when(otherHolder.getDate()).thenReturn(other);
        when(holder.getLocale()).thenReturn(locale);
        when(otherHolder.getLocale()).thenReturn(otherApple);

        varConflict.compare();

        verify(date).compareTo(other);
        verify(locale).getDisplayCountry(otherApple);
    }
}
