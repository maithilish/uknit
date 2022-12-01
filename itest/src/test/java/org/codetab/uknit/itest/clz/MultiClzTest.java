package org.codetab.uknit.itest.clz;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MultiClzTest {
    @InjectMocks
    private MultiClz multiClz;

    @Mock
    private Date fooBirthDay;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetFooBirthDay() {

        Date actual = multiClz.getFooBirthDay();

        assertSame(fooBirthDay, actual);
    }
}
