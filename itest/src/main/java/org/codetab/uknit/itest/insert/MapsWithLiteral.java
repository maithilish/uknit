package org.codetab.uknit.itest.insert;

import java.util.Date;
import java.util.Map;

public class MapsWithLiteral {

    public Date getUsingString(final Map<String, Date> names) {
        Date date = names.get("foo");
        return date;
    }

    public Date getUsingCharacter(final Map<Character, Date> names) {
        return names.get('c');
    }

    public Date getUsingInteger(final Map<Integer, Date> names) {
        return names.get(5);
    }

    public Date removeUsingLong(final Map<Long, Date> names) {
        Date date = names.remove(7L);
        return date;
    }

    public Date getUsingNumber(final Map<Number, Date> names) {
        return names.get(6);
    }

    public Date getAssign(final MapHolder holder) {
        Date date = holder.getMap().get("foo");
        return date;
    }

    public Date getReturn(final MapHolder holder) {
        return holder.getMap().get("bar");
    }
}
