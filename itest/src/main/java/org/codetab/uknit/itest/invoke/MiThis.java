package org.codetab.uknit.itest.invoke;

/**
 * The this keyword arg should be replaced with CUT. The this in MI results in
 * improper test.
 *
 * TODO High - rectify the error.
 *
 * @author m
 *
 */
public class MiThis {

    private Helper helper;

    public Document thisArg() {
        return helper.getDocument(this, "foo");
    }

    public Document thisInvoke() {
        return this.helper.getDocument(this, "foo");
    }

    interface Helper {
        Document getDocument(MiThis miThis, String name);
    }

    interface Document {
    }
}
