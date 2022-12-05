package org.codetab.uknit.itest.load;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ListsTest {
    @InjectMocks
    private Lists lists;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAssign() {
        List<String> names = new ArrayList<>();
        String name = "Foo";
        names.add(name);

        String actual = lists.getAssign(names);

        assertEquals(name, actual);
    }

    @Test
    public void testGetReturn() {
        List<String> names = new ArrayList<>();
        String apple = "Foo";
        names.add(apple);

        String actual = lists.getReturn(names);

        assertEquals(apple, actual);
    }

    @Test
    public void testRemoveAssign() {
        List<String> names = new ArrayList<>();
        String name = "Foo";
        names.add(name);

        String actual = lists.removeAssign(names);

        assertEquals(name, actual);
    }

    @Test
    public void testRemoveReturn() {
        List<String> names = new ArrayList<>();
        String apple = "Foo";
        names.add(apple);

        String actual = lists.removeReturn(names);

        assertEquals(apple, actual);
    }

    @Test
    public void testGetAssignListHolder() {
        ListHolder listHolder = Mockito.mock(ListHolder.class);
        List<String> list = new ArrayList<>();
        String name = "Foo";
        list.add(name);

        when(listHolder.getList()).thenReturn(list);

        String actual = lists.getAssign(listHolder);

        assertEquals(name, actual);
    }

    @Test
    public void testGetReturnListHolder() {
        ListHolder listHolder = Mockito.mock(ListHolder.class);
        List<String> list = new ArrayList<>();
        String apple = "Foo";
        list.add(apple);

        when(listHolder.getList()).thenReturn(list);

        String actual = lists.getReturn(listHolder);

        assertEquals(apple, actual);
    }

    @Test
    public void testForAssign() {
        List<String> names = new ArrayList<>();
        String str = "Foo";
        String name = str;
        names.add(str);

        String actual = lists.forAssign(names);

        assertEquals(name, actual);
    }

    @Test
    public void testForAssignListHolder() {
        ListHolder listHolder = Mockito.mock(ListHolder.class);
        String str = "Foo";
        String name = str;
        List<String> list = new ArrayList<>();
        list.add(str);

        when(listHolder.getList()).thenReturn(list);

        String actual = lists.forAssign(listHolder);

        assertEquals(name, actual);
    }
}
