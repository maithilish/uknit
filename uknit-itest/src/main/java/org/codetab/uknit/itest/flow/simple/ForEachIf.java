package org.codetab.uknit.itest.flow.simple;

import static java.util.Objects.isNull;

import java.util.List;

import org.codetab.uknit.itest.flow.simple.Model.YmlNode;

class ForEachIf {

    // for each, for and if statement
    public void addOrderIfAbsent(final List<YmlNode> itemsList) {
        for (YmlNode items : itemsList) {
            List<YmlNode> itemList = items.findValues("item");
            for (int i = 0; i < itemList.size(); i++) {
                YmlNode item = itemList.get(i);
                if (isNull(item.findValue("order"))) {
                    // STEPIN - is null and not called, add never() to verify
                    item.put("order", i);
                }
            }
        }
    }
}
