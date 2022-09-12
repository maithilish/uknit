package org.codetab.uknit.itest.insert;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MapsWithLiteralTest {
    @InjectMocks
    private MapsWithLiteral mapsWithLiteral;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUsingString() {
        Map<String, Date> names = new HashMap<>();
        Date date = Mockito.mock(Date.class);
        String apple = "foo";
        names.put(apple, date);

        Date actual = mapsWithLiteral.getUsingString(names);

        assertSame(date, actual);
    }

    @Test
    public void testGetUsingCharacter() {
        Map<Character, Date> names = new HashMap<>();
        Date date = Mockito.mock(Date.class);
        Character apple = 'c';
        names.put(apple, date);

        Date actual = mapsWithLiteral.getUsingCharacter(names);

        assertSame(date, actual);
    }

    @Test
    public void testGetUsingInteger() {
        Map<Integer, Date> names = new HashMap<>();
        Date date = Mockito.mock(Date.class);
        Integer apple = 5;
        names.put(apple, date);

        Date actual = mapsWithLiteral.getUsingInteger(names);

        assertSame(date, actual);
    }

    @Test
    public void testRemoveUsingLong() {
        Map<Long, Date> names = new HashMap<>();
        Date date = Mockito.mock(Date.class);
        Long apple = 7L;
        names.put(apple, date);

        Date actual = mapsWithLiteral.removeUsingLong(names);

        assertSame(date, actual);
    }

    @Test
    public void testGetUsingNumber() {
        Map<Number, Date> names = new HashMap<>();
        Date date = Mockito.mock(Date.class);
        Number apple = 6;
        names.put(apple, date);

        Date actual = mapsWithLiteral.getUsingNumber(names);

        assertSame(date, actual);
    }

    @Test
    public void testGetAssign() {
        MapHolder holder = Mockito.mock(MapHolder.class);
        Map<String, Date> map = new HashMap<>();
        Date date = Mockito.mock(Date.class);
        String apple = "foo";
        map.put(apple, date);

        when(holder.getMap()).thenReturn(map);

        Date actual = mapsWithLiteral.getAssign(holder);

        assertSame(date, actual);
    }

    @Test
    public void testGetReturn() {
        MapHolder holder = Mockito.mock(MapHolder.class);
        Map<String, Date> map = new HashMap<>();
        Date date = Mockito.mock(Date.class);
        String apple = "bar";
        map.put(apple, date);

        when(holder.getMap()).thenReturn(map);

        Date actual = mapsWithLiteral.getReturn(holder);

        assertSame(date, actual);
    }
}
