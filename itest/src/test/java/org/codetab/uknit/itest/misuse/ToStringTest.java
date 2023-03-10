package org.codetab.uknit.itest.misuse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.misuse.Model.Duck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ToStringTest {
    @InjectMocks
    private ToString toString;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToStringNotAllowedInVerifyIfCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = true;
        String grape = "Foo";

        when(duck.toString()).thenReturn(grape);

        String actual = toString.toStringNotAllowedInVerify(duck, canSwim);

        assertEquals(grape, actual);
    }

    @Test
    public void testToStringNotAllowedInVerifyElseCanSwim() {
        Duck duck = Mockito.mock(Duck.class);
        boolean canSwim = false;
        String grape = "Bar";

        when(duck.toString()).thenReturn(grape);

        String actual = toString.toStringNotAllowedInVerify(duck, canSwim);

        assertEquals(grape, actual);
    }
}
