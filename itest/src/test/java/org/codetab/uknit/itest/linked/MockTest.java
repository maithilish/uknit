package org.codetab.uknit.itest.linked;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.codetab.uknit.itest.linked.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MockTest {
    @InjectMocks
    private Mock mock;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignCreationToMock() {
        Foo foo = Mockito.mock(Foo.class);
        Locale locale = new Locale("en");
        Locale locale2 = locale;

        Locale actual = mock.assignCreationToMock(foo);

        assertEquals(locale2, actual);
    }

    @Test
    public void testAssignCreationAndMockToMock() {
        Foo foo = Mockito.mock(Foo.class);
        Locale locale = Mockito.mock(Locale.class);
        Locale locale2 = locale;

        when(foo.locale()).thenReturn(locale);

        Locale actual = mock.assignCreationAndMockToMock(foo);

        assertSame(locale2, actual);
    }
}