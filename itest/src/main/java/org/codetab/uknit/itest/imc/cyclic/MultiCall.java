package org.codetab.uknit.itest.imc.cyclic;

import org.codetab.uknit.itest.imc.cyclic.Model.Foo;

public class MultiCall {

    boolean end = false;

    public void multiCall51(final Foo foo) {
        end = false;
        i52(foo);
        end = false;
        i53(foo);
        end = false;
        i52(foo);
        end = false;
        i53(foo);
        end = false;
        i53(foo);
        end = false;
        i54(foo);
    }

    private void i52(final Foo foo) {
        foo.append("i52 before");
        i53(foo);
        foo.append("i52 after");
        foo.format("i52 after");
    }

    private void i53(final Foo foo) {
        foo.append("i53 before");
        if (!end) {
            i54(foo);
        }
        foo.append("i53 after");
        foo.format("i53 after");
    }

    private void i54(final Foo foo) {
        foo.append("i54 before");
        end = true;
        i53(foo);
        foo.append("i54 after");
        foo.format("i54 after");
    }

    public void callBase61(final Foo foo, final boolean finish) {
        foo.append("before i62");
        i62(foo, finish);
        foo.append("before i63");
        i63(foo, finish);
    }

    private void i62(final Foo foo, final boolean finish) {
        foo.append("i62 before");
        if (!finish) {
            callBase61(foo, finish);
        }
        foo.append("i62 after");
        foo.format("i62 after");
    }

    private void i63(final Foo foo, final boolean finish) {
        foo.append("i63 before");
        i62(foo, finish);
        foo.append("i63 after");
        foo.format("i63 after");
    }
}
