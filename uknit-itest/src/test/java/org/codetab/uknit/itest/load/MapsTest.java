package org.codetab.uknit.itest.load;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

class MapsTest {
    @InjectMocks
    private Maps maps;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAssign() {
        Map<String, Date> names = new HashMap<>();
        String key = "foo";
        Date date = Mockito.mock(Date.class);
        names.put(key, date);

        Date actual = maps.getAssign(names);

        assertSame(date, actual);
    }

    @Test
    public void testGetReturn() {
        Map<String, Date> names = new HashMap<>();
        String key = "foo";
        Date date = Mockito.mock(Date.class);
        names.put(key, date);

        Date actual = maps.getReturn(names);

        assertSame(date, actual);
    }

    @Test
    public void testRemoveAssign() {
        Map<String, Date> names = new HashMap<>();
        String key = "foo";
        Date date = Mockito.mock(Date.class);
        names.put(key, date);

        Date actual = maps.removeAssign(names);

        assertSame(date, actual);
    }

    @Test
    public void testRemoveReturn() {
        Map<String, Date> names = new HashMap<>();
        String key = "foo";
        Date date = Mockito.mock(Date.class);
        names.put(key, date);

        Date actual = maps.removeReturn(names);

        assertSame(date, actual);
    }

    @Test
    public void testGetAssignMapHolder() {
        MapHolder holder = Mockito.mock(MapHolder.class);
        String key = "foo";
        Map<String, Date> map = new HashMap<>();
        Date date = Mockito.mock(Date.class);
        map.put(key, date);

        when(holder.getMap()).thenReturn(map);

        Date actual = maps.getAssign(holder);

        assertSame(date, actual);
    }

    @Test
    public void testGetReturnMapHolder() {
        MapHolder holder = Mockito.mock(MapHolder.class);
        String key = "foo";
        Map<String, Date> map = new HashMap<>();
        Date date = Mockito.mock(Date.class);
        map.put(key, date);

        when(holder.getMap()).thenReturn(map);

        Date actual = maps.getReturn(holder);

        assertSame(date, actual);
    }

    @Test
    public void testForValuesAssign() {
        Map<String, Date> names = new HashMap<>();

        Date d = Mockito.mock(Date.class);
        Date date = d;
        String apple = "Foo";
        names.put(apple, d);

        Date actual = maps.forValuesAssign(names);

        assertEquals(date, actual);
    }

    @Test
    public void testForValuesAssignMapHolder() {
        MapHolder holder = Mockito.mock(MapHolder.class);

        Date d = Mockito.mock(Date.class);
        Date date = d;
        Map<String, Date> map = new HashMap<>();
        String apple = "Foo";
        map.put(apple, d);

        when(holder.getMap()).thenReturn(map);

        Date actual = maps.forValuesAssign(holder);

        assertEquals(date, actual);
    }

    @Test
    public void testForKeySetAssign() {
        Map<String, Date> names = new HashMap<>();
        String name = "Foo";
        String str = "Foo";
        Date apple = Mockito.mock(Date.class);
        names.put(str, apple);

        String actual = maps.forKeySetAssign(names);

        assertEquals(name, actual);
    }

    @Test
    public void testForKeySetAssignMapHolder() {
        MapHolder holder = Mockito.mock(MapHolder.class);
        String name = "Foo";
        String str = "Foo";
        Map<String, Date> map = new HashMap<>();
        Date apple = Mockito.mock(Date.class);
        map.put(str, apple);

        when(holder.getMap()).thenReturn(map);

        String actual = maps.forKeySetAssign(holder);

        assertEquals(name, actual);
    }
}
