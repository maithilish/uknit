package org.codetab.uknit.itest.nested.inner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.codetab.uknit.itest.nested.inner.Model.Duck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AccessEnclosingTest {
    @InjectMocks
    private AccessEnclosing accessEnclosing;

    @Mock
    private Duck duck;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOuterAndInner() {
        String apple = "Speed 10";

        String actual = accessEnclosing.createOuterAndInner();

        assertEquals(apple, actual);
    }

    @Test
    public void testCreateInner() {
        String apple = null;

        String actual = accessEnclosing.createInner();

        assertEquals(apple, actual);
    }

    @Test
    public void testCreateInner2() {
        String apple = null;

        String actual = accessEnclosing.createInner2();

        assertEquals(apple, actual);
    }
}
