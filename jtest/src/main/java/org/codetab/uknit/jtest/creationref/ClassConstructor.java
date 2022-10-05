package org.codetab.uknit.jtest.creationref;

import java.util.List;

/**
 * Creation reference expression.
 *
 * <pre>
 * CreationReference:
 *     Type <b>::</b>
 *         [ <b>&lt;</b> Type { <b>,</b> Type } <b>&gt;</b> ]
 *         <b>new</b>
 * </pre>
 *
 * TODO L - fix the test.
 *
 * @author m
 *
 */
class ClassConstructor {

    private Log log;

    public ClassConstructor(final String s) {
        log.debug("Hello " + s);
    }

    public void foo(final List<String> list) {
        list.add("uknit");
        list.add("mockito");
        list.add("junit");

        // call the class constructor using double colon operator
        list.forEach(ClassConstructor::new);
    }

    interface Log {
        void debug(String msg);
    }
}
