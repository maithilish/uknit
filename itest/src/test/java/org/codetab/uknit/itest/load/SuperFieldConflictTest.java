package org.codetab.uknit.itest.load;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SuperFieldConflictTest {
    @InjectMocks
    private SuperFieldConflict superFieldConflict;

    @Mock
    private Date date;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFoo() {
        List<Date> dates = new ArrayList<>();
        Date selectedDate = date;
        dates.add(date);

        Date actual = superFieldConflict.foo(dates);

        assertEquals(selectedDate, actual);
    }
}
