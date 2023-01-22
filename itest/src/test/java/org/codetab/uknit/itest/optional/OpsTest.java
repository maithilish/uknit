package org.codetab.uknit.itest.optional;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.codetab.uknit.itest.optional.Ops.Dates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class OpsTest {
    @InjectMocks
    private Ops ops;

    @Mock
    private Dates dates;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFooIfDateOIsPresent() {
        Date date = Mockito.mock(Date.class);
        Optional<List<Date>> dateO = Optional.of(List.of(date));
        String format = "foo";
        List<Date> list = dateO.get();

        when(dates.getDates()).thenReturn(dateO);
        ops.foo(date);

        verify(dates).doSomething(list, format);
    }

    @Test
    public void testFooElseDateOIsPresent() {
        Date date = Mockito.mock(Date.class);
        Optional<List<Date>> dateO = Optional.empty();
        String format = "foo";
        List<Date> list = null;

        when(dates.getDates()).thenReturn(dateO);
        ops.foo(date);

        verify(dates, never()).doSomething(list, format);
    }
}
