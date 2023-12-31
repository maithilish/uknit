package org.codetab.uknit.itest.variable;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.AbstractMap.SimpleEntry;

import org.codetab.uknit.itest.variable.Model.Point;
import org.codetab.uknit.itest.variable.Model.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class QNameTest {
    @InjectMocks
    private QName qName;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReturnQName() {
        Point point = Mockito.mock(Point.class);
        int apple = point.x;

        int actual = qName.returnQName(point);

        assertEquals(apple, actual);
    }

    @Test
    public void testQNameInArg() {
        Point point = Mockito.mock(Point.class);
        int apple = point.y;

        int actual = qName.qNameInArg(point);

        assertEquals(apple, actual);

        verify(point).setY(point.x);
    }

    @Test
    public void testQNameInAssign() {
        Point point = Mockito.spy(Point.class);
        point.y = 2;
        int[] coordinates = {1, 2};
        int apple = point.y;

        int actual = qName.qNameInAssign(point, coordinates);

        assertEquals(apple, actual);
    }

    @Test
    public void testAssignQNameToArray() {
        Point point = Mockito.mock(Point.class);
        int[] coordinates = new int[2];

        int[] actual = qName.assignQNameToArray(point);

        assertArrayEquals(coordinates, actual);
    }

    @Test
    public void testAssignQNameFromGetter() {
        Point point = Mockito.mock(Point.class);
        int apple = 1;
        int grape = point.y;

        when(point.getY()).thenReturn(apple);

        int actual = qName.assignQNameFromGetter(point);

        assertEquals(grape, actual);
    }

    @Test
    public void testQNameInfix() {
        Point point = Mockito.mock(Point.class);

        boolean actual = qName.qNameInfix(point);

        assertTrue(actual);
    }

    @Test
    public void testReturnQNameInfix() {
        Point point = Mockito.mock(Point.class);

        boolean actual = qName.returnQNameInfix(point);

        assertTrue(actual);
    }

    @Test
    public void testQNameAsCallRealObj() {
        Point point = Mockito.spy(Point.class);
        point.desc = "abcdeA";
        char apple = 'A';

        char actual = qName.qNameAsCallRealObj(point);

        assertEquals(apple, actual);
    }

    @Test
    public void testQNameAsCallMockObj() {
        Screen screen = Mockito.spy(Screen.class);
        Point point = Mockito.spy(Point.class);
        point.x = 1;
        int apple = 1;

        int actual = qName.qNameAsCallMockObj(screen, point);

        assertEquals(apple, actual);

        verify(screen).setPoint(point);
    }

    @Test
    public void testQNameInCast() {
        Point point = Mockito.mock(Point.class);
        String apple = (String) point.attchment;

        String actual = qName.qNameInCast(point);

        assertEquals(apple, actual);
    }

    @Test
    public void testQNameInCreate() {
        Point point = Mockito.mock(Point.class);
        SimpleEntry<Integer, Integer> entry =
                new SimpleEntry<Integer, Integer>(point.x, point.y);

        SimpleEntry<Integer, Integer> actual = qName.qNameInCreate(point);

        assertEquals(entry, actual);
    }
}
