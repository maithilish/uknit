package org.codetab.uknit.itest.superclass;

import java.text.ParseException;

class SuperCalls extends SuperBar {

    public Bar assignFromSuperCreate() {
        Bar bar = super.createBar();
        return bar;
    }

    public Bar returnFromSuperCreate() {
        return super.createBar();
    }

    public Bar assignFromSuperCreateAndMock(final String name)
            throws ParseException {
        Bar bar = super.getBar(name);
        return bar;
    }

    public Bar returnFromSuperCreateAndMock(final String name)
            throws ParseException {
        return super.getBar(name);
    }

    public Bar assignFromMock(final Factory factory, final String name)
            throws ParseException {
        Bar bar = super.getBar(factory, name);
        return bar;
    }

    public Bar returnFromMock(final Factory factory, final String name)
            throws ParseException {
        return super.getBar(factory, name);
    }

}

class SuperBar {

    Bar createBar() {
        return new Bar();
    }

    public Bar getBar(final Factory factory, final String name) {
        return factory.instance(name);
    }

    public Bar getBar(final String name) {
        Factory factory = new Factory();
        return factory.instance(name);
    }
}
