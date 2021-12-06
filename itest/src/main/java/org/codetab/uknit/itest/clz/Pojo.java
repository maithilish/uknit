package org.codetab.uknit.itest.clz;

import java.util.Date;

public class Pojo {

    private Date bar;
    private String foo;

    public Pojo(final String foo, final Date bar) {
        this.foo = foo;
        this.bar = bar;
    }

    public Date getBar() {
        return bar;
    }

    public void setBar(final Date bar) {
        this.bar = bar;
    }

    // STEPIN - String foo is not mock and not injected, but Date bar is mock
    public String getFoo() {
        return foo;
    }

    public void setFoo(final String foo) {
        this.foo = foo;
    }
}
