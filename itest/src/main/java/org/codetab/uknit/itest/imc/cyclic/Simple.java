package org.codetab.uknit.itest.imc.cyclic;

import org.codetab.uknit.itest.imc.cyclic.Model.Foo;

public class Simple {

    boolean end;

    public void callEachOther11(final Foo foo) {
        end = false;
        imc12(foo);
    }

    private void imc12(final Foo foo) {
        if (!end) {
            foo.append("imc12");
            imc13(foo);
        }
    }

    private void imc13(final Foo foo) {
        end = true;
        foo.append("imc13");
        imc12(foo);
    }

    public void callEachOther21(final Foo foo) {
        end = false;
        imc22(foo);
    }

    private void imc22(final Foo foo) {
        if (!end) {
            foo.append("imc22 before");
            imc23(foo);
            foo.append("imc22 after");
        }
    }

    private void imc23(final Foo foo) {
        end = true;
        foo.append("imc23 before");
        imc22(foo);
        foo.append("imc23 after");
    }

    public void callAncestor31(final Foo foo) {
        end = false;
        imc32(foo);
    }

    private void imc32(final Foo foo) {
        if (!end) {
            foo.append("imc32 before");
            imc33(foo);
            foo.append("imc32 after");
        }
    }

    private void imc33(final Foo foo) {
        end = true;
        foo.append("imc33 before");
        imc34(foo);
        foo.append("imc33 after");
    }

    private void imc34(final Foo foo) {
        end = true;
        foo.append("imc34 before");
        imc32(foo);
        foo.append("imc34 after");
    }

    // TODO L - if finish is true then in if test don't process IM
    public void callBase41(final Foo foo, final boolean finish) {
        if (!finish) {
            imc42(foo);
        }
    }

    private void imc42(final Foo foo) {
        foo.append("imc42 before");
        imc43(foo);
        foo.append("imc42 after");
    }

    private void imc43(final Foo foo) {
        foo.append("imc43 before");
        callBase41(foo, true);
        foo.append("imc43 after");
    }
}
