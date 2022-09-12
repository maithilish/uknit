package org.codetab.uknit.itest.insert;

import java.util.List;

/**
 * To test object Insertion to lists.
 * @author m
 *
 */
public class Lists {

    public String getAssign(final List<String> names) {
        String name = names.get(0);
        return name;
    }

    public String getReturn(final List<String> names) {
        return names.get(0);
    }

    public String removeAssign(final List<String> names) {
        String name = names.remove(0);
        return name;
    }

    public String removeReturn(final List<String> names) {
        return names.remove(0);
    }

    public String getAssign(final ListHolder listHolder) {
        String name = listHolder.getList().get(0);
        return name;
    }

    public String getReturn(final ListHolder listHolder) {
        return listHolder.getList().get(0);
    }

    public String forAssign(final List<String> names) {
        String name = null;
        for (String str : names) {
            name = str;
        }
        return name;
    }

    public String forAssign(final ListHolder listHolder) {
        String name = null;
        for (String str : listHolder.getList()) {
            name = str;
        }
        return name;
    }
}

interface ListHolder {
    List<String> getList();
}
