package org.codetab.uknit.itest.linked;

import java.util.Date;
import java.util.Locale;

import org.codetab.uknit.itest.linked.Model.Foo;

class Enabled {

    /*
     * check var enable, created
     */
    @SuppressWarnings("unused")
    public Object disableUnusedVars() {
        Object obj = new Locale("");

        // obj1 and obj2 are not used
        Object obj1 = obj;
        Object obj2 = obj1;

        Object obj3 = obj;
        return obj3;
    }

    /*
     * check var enable, infer
     */
    @SuppressWarnings("unused")
    public Object disableUnusedVars(final Foo foo) {
        Object obj = foo.obj();

        // obj1 and obj2 are not used
        Object obj1 = obj;
        Object obj2 = obj1;

        Object obj3 = obj;
        return obj3;
    }

    /*
     * unused var but assigned Creation
     */
    @SuppressWarnings("unused")
    public Object disableUnusedVarsCreated(final Foo foo) {
        Object obj = foo.obj();

        // obj1 and obj2 are not used
        Object obj1 = obj;
        Object obj2 = obj1;
        obj2 = new Date();

        Object obj3 = obj;
        return obj3;
    }

    /*
     * unused var but assigned invoke which needs When() stmt
     */
    @SuppressWarnings("unused")
    public Object enableUnusedVarsButWhenExists(final Foo foo) {
        Object obj = new Locale("");

        // obj1 and obj2 are not used
        Object obj1 = obj;
        Object obj2 = obj1;
        obj2 = foo.obj();

        Object obj3 = obj;
        return obj3;
    }
}
