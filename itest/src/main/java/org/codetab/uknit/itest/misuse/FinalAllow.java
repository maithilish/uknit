package org.codetab.uknit.itest.misuse;

import org.codetab.uknit.itest.misuse.Model.Foo;

/**
 * Stubbing of final methods are allowed in mockito 2.x using mockito-inline
 * extension (see uknit/pom.xml for mockito-inline dependency)
 *
 * @author Maithilish
 *
 */
class FinalAllow {

    public int foo(final Foo foo) {
        int flag = foo.finalMethod("foo", "bar");
        return flag;
    }

}
