package org.codetab.uknit.itest.linked;

import java.util.Locale;

import org.codetab.uknit.itest.linked.Model.Foo;

/**
 * Mock var assigned with creation.
 *
 * @author Maithilish
 *
 */
public class Mock {

    /*
     * Var locale is mock but later new Locale() is assigned to it. The 1st stmt
     * is unused as 2nd stmt overrides it so the 1st stmt is removed in
     * Assignor.process() and when is not created for it.
     */
    public Locale assignCreationToMock(final Foo foo) {
        Locale locale = foo.locale();
        locale = new Locale("en");
        Locale locale2 = locale;
        return locale2;
    }

    /*
     * Var locale is mock but new Locale() is assigned to it and next override
     * with infer
     */
    public Locale assignCreationAndMockToMock(final Foo foo) {
        Locale locale = new Locale("en");
        locale = foo.locale();
        Locale locale2 = locale;
        return locale2;
    }
}
