package org.codetab.uknit.itest.brace.patch;

import org.codetab.uknit.itest.brace.patch.Model.Factory;
import org.codetab.uknit.itest.brace.patch.Model.Foo;

class NameChange {

    private Factory factory;

    /**
     * Resolve var name conflict - foo in caller and IM. Changes foo to foo2 in
     * IM.
     *
     * @return
     */
    public Foo varNameChangeInReturn() {
        @SuppressWarnings("unused")
        Foo foo = ((factory).createFoo());
        Foo otherFoo = (imVarNameChangeInReturn());
        return otherFoo;
    }

    private Foo imVarNameChangeInReturn() {
        Foo foo = ((factory).createFoo());
        return foo;
    }

    /**
     * Resolve var name conflict - foo in caller and IM. Changes foo to foo2 in
     * all occurrences IM.
     *
     * @return
     */
    public Foo varNameChangeInSimpleNameInvoke() {
        @SuppressWarnings("unused")
        Foo foo = (factory.createFoo());
        Foo otherFoo = (imVarNameChangeInSimpleNameInvoke());
        return otherFoo;
    }

    private Foo imVarNameChangeInSimpleNameInvoke() {
        Foo foo = ((factory).createFoo());

        // invoke on simple name
        foo.bar();

        return foo;
    }

    /**
     * Resolve var name conflict - foo in caller and IM. Changes foo to foo2 in
     * all occurrences IM.
     *
     * @return
     */
    public Foo varNameChangeInExpInvoke() {
        @SuppressWarnings("unused")
        Foo foo = ((factory).createFoo());
        Foo otherFoo = (imVarNameChangeInExpInvoke());
        return otherFoo;
    }

    private Foo imVarNameChangeInExpInvoke() {
        Foo foo = ((factory).createFoo());
        // invoke on exp foo.bar()
        ((foo).bar()).baz();

        return foo;
    }
}
