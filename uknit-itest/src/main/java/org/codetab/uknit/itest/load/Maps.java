package org.codetab.uknit.itest.load;

import java.util.Date;
import java.util.Map;

/**
 * To test object Insertion to lists.
 * @author Maithilish
 *
 */
class Maps {

    public Date getAssign(final Map<String, Date> names) {
        String key = "foo";
        Date date = names.get(key);
        return date;
    }

    public Date getReturn(final Map<String, Date> names) {
        String key = "foo";
        return names.get(key);
    }

    public Date removeAssign(final Map<String, Date> names) {
        String key = "foo";
        Date date = names.remove(key);
        return date;
    }

    public Date removeReturn(final Map<String, Date> names) {
        String key = "foo";
        return names.remove(key);
    }

    public Date getAssign(final MapHolder holder) {
        String key = "foo";
        Date date = holder.getMap().get(key);
        return date;
    }

    public Date getReturn(final MapHolder holder) {
        String key = "foo";
        return holder.getMap().get(key);
    }

    /**
     * TODO L - var date is set to null instead of var d.
     * @param names
     * @return
     */
    public Date forValuesAssign(final Map<String, Date> names) {
        Date date = null;
        for (Date d : names.values()) {
            date = d;
        }
        return date;
    }

    public Date forValuesAssign(final MapHolder holder) {
        Date date = null;
        for (Date d : holder.getMap().values()) {
            date = d;
        }
        return date;
    }

    public String forKeySetAssign(final Map<String, Date> names) {
        String name = null;
        for (String str : names.keySet()) {
            name = str;
        }
        return name;
    }

    public String forKeySetAssign(final MapHolder holder) {
        String name = null;
        for (String str : holder.getMap().keySet()) {
            name = str;
        }
        return name;
    }
}

interface MapHolder {
    Map<String, Date> getMap();
}
