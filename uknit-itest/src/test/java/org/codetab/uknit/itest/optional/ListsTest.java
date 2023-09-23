package org.codetab.uknit.itest.optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ListsTest {
    @InjectMocks
    private Lists lists;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCollectIfDatesIsPresentIfDateEquals() {
        OptionalListHolder holder = Mockito.mock(OptionalListHolder.class);
        Date inDate = Mockito.mock(Date.class);
        Optional<List<Date>> dates = Optional.empty();
        @SuppressWarnings("unused")
        Date date = Mockito.mock(Date.class);

        when(holder.getDates()).thenReturn(dates);

        boolean actual = lists.collect(holder, inDate);

        assertFalse(actual);
    }

    @Test
    public void testCollectIfDatesIsPresentElseDateEquals() {
        OptionalListHolder holder = Mockito.mock(OptionalListHolder.class);
        Date inDate = Mockito.mock(Date.class);
        Optional<List<Date>> dates = Optional.empty();
        @SuppressWarnings("unused")
        Date date = Mockito.mock(Date.class);

        when(holder.getDates()).thenReturn(dates);

        boolean actual = lists.collect(holder, inDate);

        assertFalse(actual);
    }

    @Test
    public void testCollectElseDatesIsPresent() {
        OptionalListHolder holder = Mockito.mock(OptionalListHolder.class);
        Date inDate = Mockito.mock(Date.class);
        Optional<List<Date>> dates = Optional.empty();
        @SuppressWarnings("unused")
        Date date = Mockito.mock(Date.class);

        when(holder.getDates()).thenReturn(dates);

        boolean actual = lists.collect(holder, inDate);

        assertFalse(actual);
    }
}
