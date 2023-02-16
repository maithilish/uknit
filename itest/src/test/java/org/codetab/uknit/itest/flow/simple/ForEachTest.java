package org.codetab.uknit.itest.flow.simple;

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

class ForEachTest {
    @InjectMocks
    private ForEach forEach;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBasicFor() {
        List<YmlNode> itemList = new ArrayList<>();
        int i = 0;
        YmlNode item = Mockito.mock(YmlNode.class);
        itemList.add(item);
        forEach.basicFor(itemList);

        verify(item).put("order", i);
    }

    @Test
    public void testBasicForAssign() {
        List<YmlNode> itemList = new ArrayList<>();
        int i = 0;
        YmlNode item = Mockito.mock(YmlNode.class);
        itemList.add(item);
        forEach.basicForAssign(itemList);

        verify(item).put("order", i);
    }

    @Test
    public void testEnhancedFor() {
        List<YmlNode> itemsList = new ArrayList<>();
        YmlNode items = Mockito.mock(YmlNode.class);
        List<YmlNode> itemList = new ArrayList<>();
        YmlNode item = Mockito.mock(YmlNode.class);
        itemsList.add(items);
        itemList.add(item);

        when(items.findValues("item")).thenReturn(itemList);
        forEach.enhancedFor(itemsList);

        verify(item).put("order", 1);
    }

    @Test
    public void testCombined() {
        List<YmlNode> itemsList = new ArrayList<>();
        YmlNode items = Mockito.mock(YmlNode.class);
        List<YmlNode> itemList = new ArrayList<>();
        int i = 0;
        YmlNode item = Mockito.mock(YmlNode.class);
        itemsList.add(items);
        itemList.add(item);

        when(items.findValues("item")).thenReturn(itemList);
        forEach.combined(itemsList);

        verify(item).put("order", i);
    }
}
