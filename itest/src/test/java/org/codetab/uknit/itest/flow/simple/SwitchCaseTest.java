package org.codetab.uknit.itest.flow.simple;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class SwitchCaseTest {
    @InjectMocks
    private SwitchCase switchCase;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMonth() {
        int month = 1;
        String monthString13 = "January";

        String actual = switchCase.getMonth(month);

        assertEquals(monthString13, actual);
    }
}
