package org.codetab.uknit.itest.brace.linked;

import java.util.Locale;

import org.codetab.uknit.itest.brace.linked.Model.Foo;

/**
 * Test var isCreated in linked vars.
 *
 * @author Maithilish
 *
 */
class Created {

    /*
     * Created assert should be assertEquals()
     */
    public Object isCreated(final Foo foo) {
        Object obj = (new Locale(("")));
        @SuppressWarnings("unused")
        Object obj1 = (obj);
        Object obj2 = (obj);
        Object obj3 = (obj2);
        return (obj3);
    }

    /*
     * Inferred but next assigned to created assert should be assertEquals()
     */
    public Object isCreated2(final Foo foo) {
        Object obj = ((foo).obj());
        obj = (new Locale(("")));
        @SuppressWarnings("unused")
        Object obj1 = (obj);
        Object obj2 = obj;
        Object obj3 = (obj2);
        return (obj3);
    }

    /*
     * Assigned to inferred invoke, assert should be assertSame()
     */
    public Object isInferred(final Foo foo) {
        Object obj = ((foo).obj());
        @SuppressWarnings("unused")
        Object obj1 = (obj);
        Object obj2 = (obj);
        Object obj3 = (obj2);
        return (obj3);
    }

    /*
     * Created but later assigned to inferred invoke, assert should be
     * assertSame()
     */
    public Object isInferred2(final Foo foo) {
        Object obj = (new Locale(("")));
        obj = ((foo).obj());
        @SuppressWarnings("unused")
        Object obj1 = (obj);
        Object obj2 = obj;
        Object obj3 = (obj2);
        return obj3;
    }
}
