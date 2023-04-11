package org.codetab.uknit.itest.array;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.codetab.uknit.itest.array.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
class AccessCastTest {
    @InjectMocks
    private AccessCast accessCast;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignAccessPrimitive() {
        String apple = "foo";
        String foo = apple;

        String actual = accessCast.assignAccessPrimitive();

        assertEquals(foo, actual);
    }

    @Test
    public void testReturnAccess() {
        int apple = 10;

        int actual = accessCast.returnAccess();

        assertEquals(apple, actual);
    }

    @Test
    public void testDeclareTwoArray() {
        Foo foo = Mockito.mock(Foo.class);
        String apple = "foo";
        String grape = "bar";
        String orange = "foox";
        String kiwi = new String("barx");
        // String mango = "foo";
        accessCast.declareTwoArray(foo);

        verify(foo, times(2)).append(apple);
        verify(foo).append(grape);
        verify(foo).append(orange);
        verify(foo).append(kiwi);
    }

    @Test
    public void testArrayParameter() {
        Foo foo = Mockito.mock(Foo.class);
        String[] array = {"x", "y"};
        String apple = "foox";
        String grape = new String("barx");
        accessCast.arrayParameter(foo, array);

        verify(foo, times(2)).append(array[0]);
        verify(foo).append(array[1]);
        verify(foo).append(apple);
        verify(foo).append(grape);
    }

    @Test
    public void testAccessInfix() {
        boolean flag = true;
        int apple = 20;
        int grape = 10;
        Object foo = flag ? apple : grape;

        Object actual = accessCast.accessInfix(flag);

        assertEquals(foo, actual);
    }

    @Test
    public void testReturnAccessInfix() {
        boolean flag = true;
        int apple = 20;
        int grape = 10;
        Object orange = flag ? apple : grape;

        Object actual = accessCast.returnAccessInfix(flag);

        assertEquals(orange, actual);
    }

    @Test
    public void testReturnInitializedAccess() {
        Object apple = 10;

        Object actual = accessCast.returnInitializedAccess();

        assertEquals(apple, actual);
    }
}
