package org.codetab.uknit.itest.misuse;

import org.codetab.uknit.itest.misuse.Model.Foo;

/**
 * Stubbing of final method is not allowed if uknit.mockito.stub.final=false
 *
 * @author Maithilish
 *
 */
class FinalDisallow {

    public int foo(final Foo foo) {
        int flag = foo.finalMethod("foo", "bar");
        return flag;
    }

}
