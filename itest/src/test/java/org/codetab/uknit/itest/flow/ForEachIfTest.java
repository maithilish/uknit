package org.codetab.uknit.itest.flow;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ForEachIfTest {
    @InjectMocks
    private ForEachIf forEachIf;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddOrderIfAbsent() {
        List<YmlNode> itemsList = new ArrayList<>();
        YmlNode items = Mockito.mock(YmlNode.class);
        itemsList.add(items);
        List<YmlNode> itemList = new ArrayList<>();
        YmlNode item = Mockito.mock(YmlNode.class);
        itemList.add(item);
        Object kiwi = Mockito.mock(Object.class);
        int i = 0;

        when(items.findValues("item")).thenReturn(itemList);
        when(item.findValue("order")).thenReturn(kiwi);
        forEachIf.addOrderIfAbsent(itemsList);

        verify(item, never()).put("order", i);
    }
}
