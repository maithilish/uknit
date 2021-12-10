package org.codetab.uknit.itest.flow;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class SwitchCaseTest {
    @InjectMocks
    private SwitchCase switchCase;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMonth() {
        String monthString = "January";
        int month = 1;

        String actual = switchCase.getMonth(month);

        assertEquals(monthString, actual);
    }
}
