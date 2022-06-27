package org.codetab.uknit.itest.superclass;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class WithoutWithSuperTest {
    @InjectMocks
    private WithoutWithSuper withoutWithSuper;

    @Mock
    private StringBuilder baz;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignPrefix() {
        StringBuilder b = baz;

        StringBuilder actual = withoutWithSuper.assignPrefix();

        assertSame(b, actual);
    }

    @Test
    public void testAssignWithSuperPrefix() {
        StringBuilder b = baz;

        StringBuilder actual = withoutWithSuper.assignWithSuperPrefix();

        assertSame(b, actual);
    }

    @Test
    public void testAssignWithSuperPrefixMultiCall() {
        StringBuilder a = baz;
        StringBuilder b = baz;
        StringBuilder c = baz;
        StringBuilder apple = Mockito.mock(StringBuilder.class);
        StringBuilder grape = Mockito.mock(StringBuilder.class);

        when(a.append(b)).thenReturn(apple);
        when(apple.append(c)).thenReturn(grape);

        StringBuilder actual =
                withoutWithSuper.assignWithSuperPrefixMultiCall();

        assertSame(grape, actual);
    }

    @Test
    public void testAssignPrefixMultiCall() {
        StringBuilder a = baz;
        StringBuilder b = baz;
        StringBuilder c = baz;
        StringBuilder apple = Mockito.mock(StringBuilder.class);
        StringBuilder grape = Mockito.mock(StringBuilder.class);

        when(a.append(b)).thenReturn(apple);
        when(apple.append(c)).thenReturn(grape);

        StringBuilder actual = withoutWithSuper.assignPrefixMultiCall();

        assertSame(grape, actual);
    }

    @Test
    public void testAssignPrefixArg() {
        StringBuilder b = baz;

        StringBuilder actual = withoutWithSuper.assignPrefixArg();

        assertSame(b, actual);
    }

    @Test
    public void testAssignWithSuperPrefixArg() {
        StringBuilder b = baz;

        StringBuilder actual = withoutWithSuper.assignWithSuperPrefixArg();

        assertSame(b, actual);
    }

    @Test
    public void testAssignPrefixArgCall() {
        StringBuilder b = baz;

        StringBuilder actual = withoutWithSuper.assignPrefixArgCall();

        assertSame(b, actual);
    }

    @Test
    public void testAssignWithSuperPrefixArgCall() {
        StringBuilder b = baz;

        StringBuilder actual = withoutWithSuper.assignWithSuperPrefixArgCall();

        assertSame(b, actual);
    }

    @Test
    public void testAssignPrefixArgCallEx1() {
        StringBuilder a = baz;
        StringBuilder apple = Mockito.mock(StringBuilder.class);
        StringBuilder b = apple;

        when(baz.append(a)).thenReturn(apple);

        StringBuilder actual = withoutWithSuper.assignPrefixArgCallEx1();

        assertSame(b, actual);
    }

    @Test
    public void testAssignWithSuperPrefixArgCallEx1() {
        StringBuilder a = baz;
        StringBuilder apple = Mockito.mock(StringBuilder.class);
        StringBuilder b = apple;

        when(baz.append(a)).thenReturn(apple);

        StringBuilder actual =
                withoutWithSuper.assignWithSuperPrefixArgCallEx1();

        assertSame(b, actual);
    }

    @Test
    public void testAssignPrefixArgCallEx2() {
        StringBuilder apple = Mockito.mock(StringBuilder.class);
        StringBuilder b = apple;

        when(baz.append(baz)).thenReturn(apple);

        StringBuilder actual = withoutWithSuper.assignPrefixArgCallEx2();

        assertSame(b, actual);
    }

    @Test
    public void testAssignWithSuperPrefixArgCallEx2() {
        StringBuilder apple = Mockito.mock(StringBuilder.class);
        StringBuilder b = apple;

        when(baz.append(baz)).thenReturn(apple);

        StringBuilder actual =
                withoutWithSuper.assignWithSuperPrefixArgCallEx2();

        assertSame(b, actual);
    }
}
