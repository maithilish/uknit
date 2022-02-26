package org.codetab.uknit.itest.superclass;

public class SuperVsInternalCall extends SuperFoo {

    public StringBuilder getInternalFooBar() {
        return internalFooBar(internalBar());
    }

    // STEPIN - super field is not auto set
    public StringBuilder getSuperFooBar() {
        return super.getFooBar(super.getBar());
    }

    public StringBuilder internalFooBar(final StringBuilder bar) {
        return bar;
    }

    public StringBuilder internalBar() {
        return bar;
    }
}

class SuperFoo {

    protected StringBuilder bar;

    // CHECKSTYLE:OFF
    public StringBuilder getFooBar(final StringBuilder bar) {
        return bar;
    }
    // CHECKSTYLE:ON

    public StringBuilder getBar() {
        return bar;
    }

    public void setBar(final StringBuilder bar) {
        this.bar = bar;
    }
}
