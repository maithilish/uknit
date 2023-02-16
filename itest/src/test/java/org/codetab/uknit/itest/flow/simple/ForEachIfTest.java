package org.codetab.uknit.itest.flow.simple;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.codetab.uknit.itest.flow.simple.Model.YmlNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ForEachIfTest {
    @InjectMocks
    private ForEachIf forEachIf;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddOrderIfAbsentIfIsNull() {
        List<YmlNode> itemsList = new ArrayList<>();
        YmlNode items = Mockito.mock(YmlNode.class);
        List<YmlNode> itemList = new ArrayList<>();
        int i = 0;
        YmlNode item = Mockito.mock(YmlNode.class);
        Object grape = null;
        itemsList.add(items);
        itemList.add(item);

        when(items.findValues("item")).thenReturn(itemList);
        when(item.findValue("order")).thenReturn(grape);
        forEachIf.addOrderIfAbsent(itemsList);

        verify(item).put("order", i);
    }

    @Test
    public void testAddOrderIfAbsentElseIsNull() {
        List<YmlNode> itemsList = new ArrayList<>();
        YmlNode items = Mockito.mock(YmlNode.class);
        List<YmlNode> itemList = new ArrayList<>();
        int i = 0;
        YmlNode item = Mockito.mock(YmlNode.class);
        Object grape = "obj";
        itemsList.add(items);
        itemList.add(item);

        when(items.findValues("item")).thenReturn(itemList);
        when(item.findValue("order")).thenReturn(grape);
        forEachIf.addOrderIfAbsent(itemsList);

        verify(item, never()).put("order", i);
    }
}
