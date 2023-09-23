package org.codetab.uknit.itest.alpha;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import org.codetab.uknit.itest.alpha.Model.Bar;
import org.codetab.uknit.itest.alpha.Model.Foo;

public class Alpha {

    public LocalDateTime getStartOfDay(final LocalDate date) {
        return date.atStartOfDay();
    }

    public void cast(final Foo foo) {
        Object[] cities = {"foo", "bar", "baz"};
        foo.appendString((String) cities[0]);

        cities[1] = "barx";
        foo.format((String) cities[1]);
    }

    public void cast(final Foo foo, final Bar bar) {
        Object[] cities = {"foo", "bar", "baz"};
        foo.appendString((String) cities[0]);

        cities[1] = "barx";
        bar.format(foo);
    }

    public int accessArray() {
        int[] array = new int[1];
        array[0] = 10;

        int foo = array[0];
        return foo;
    }

    public Date accessMap(final Map<String, Date> names) {
        String key = "foo";
        Date date = names.get(key);
        return date;
    }

    public StringBuilder chainedMethodCall(final StringBuilder s1,
            final StringBuilder s2, final File file) {
        return s1.append(s2.append(file.getName().toLowerCase()));
    }

    public void conditional(final Foo foo, final File file) {
        String x = "x";
        String y = "y";
        foo.appendString(file.getAbsolutePath().equals(x) ? foo.format(x)
                : foo.format(y));
        foo.appendString(file.getAbsolutePath().equals(y) ? foo.format(x)
                : foo.format(y));
    }

    public void infix(final Foo foo, final File f1, final File f2) {
        boolean flg = true;
        File a = flg ? f1 : f2;
        File b = flg ? f1 : f2;
        File c = f2;
        foo.appendString(a.getAbsolutePath());
        foo.appendString(a.getAbsolutePath());
        foo.appendString(((a).getAbsolutePath()));
        foo.appendString(b.getAbsolutePath());
        foo.appendString(((b).getAbsolutePath()));
        foo.appendString(c.getAbsolutePath());
    }

    public String useArrayAccessInWhen(final Foo foo) {
        String[] cities = {"foo", "bar"};
        return foo.format(cities[1]);
    }

    public void useArrayAccessInVerify(final Foo foo) {
        String[] cities = {"foo", "bar"};
        foo.appendString(cities[0]);
    }
    // ------------- stash for later --------------

}
