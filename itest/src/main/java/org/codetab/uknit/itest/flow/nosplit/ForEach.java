package org.codetab.uknit.itest.flow.nosplit;

import java.util.List;

public class ForEach {

    public void basicFor(final List<YmlNode> itemList) {
        // STEPIN - add item to list
        for (int i = 0; i < itemList.size(); i++) {
            YmlNode item = itemList.get(i);
            item.put("order", i);
        }
    }

    public void basicForAssign(final List<YmlNode> itemList) {
        int i;
        for (i = 0; i < itemList.size(); i++) {
            YmlNode item = itemList.get(i);
            item.put("order", i);
        }
    }

    public void enhancedFor(final List<YmlNode> itemsList) {
        // STEPIN - add item and items to lists
        for (YmlNode items : itemsList) {
            List<YmlNode> itemList = items.findValues("item");
            YmlNode item = itemList.get(0);
            item.put("order", 1);
        }
    }

    public void combined(final List<YmlNode> itemsList) {
        for (YmlNode items : itemsList) {
            List<YmlNode> itemList = items.findValues("item");
            for (int i = 0; i < itemList.size(); i++) {
                YmlNode item = itemList.get(i);
                item.put("order", i);
            }
        }
    }
}
