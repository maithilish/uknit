package org.codetab.uknit.itest.brace.linked;

import java.util.Locale;

import org.codetab.uknit.itest.brace.linked.Model.Foo;

class Unused {

    /*
     * Even though unusedObj when is created for it.
     */
    public void unusedButInovked(final Foo foo) {
        @SuppressWarnings("unused")
        Object unObj = ((foo).obj());
    }

    /*
     * The 1st stmt is unused as 2nd stmt overrides it so the 1st stmt is
     * removed in Assignor.process() and when is not created for it. The mock
     * call foo.obj() returns null as there is no when but it doesn't results in
     * error as obj is not used.
     */
    public void unusedButInovkedAndCreated(final Foo foo) {
        @SuppressWarnings("unused")
        Object unObj = ((foo).obj());
        unObj = (new Locale("en"));
    }
}
