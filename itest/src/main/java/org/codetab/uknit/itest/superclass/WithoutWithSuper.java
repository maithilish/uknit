package org.codetab.uknit.itest.superclass;

public class WithoutWithSuper extends SuperBaz {

    public StringBuilder assignPrefix() {
        StringBuilder b = getBaz();
        return b;
    }

    public StringBuilder assignWithSuperPrefix() {
        StringBuilder b = super.getBaz();
        return b;
    }

    public StringBuilder assignWithSuperPrefixMultiCall() {
        StringBuilder a = super.getBaz();
        StringBuilder b = super.getBaz();
        StringBuilder c = getBaz();
        return a.append(b).append(c);
    }

    public StringBuilder assignPrefixMultiCall() {
        StringBuilder a = getBaz();
        StringBuilder b = getBaz();
        StringBuilder c = getBaz();
        return a.append(b).append(c);
    }

    public StringBuilder assignPrefixArg() {
        StringBuilder a = getBaz();
        StringBuilder b = getBaz(a);
        return b;
    }

    public StringBuilder assignWithSuperPrefixArg() {
        StringBuilder a = super.getBaz();
        StringBuilder b = super.getBaz(a);
        return b;
    }

    public StringBuilder assignPrefixArgCall() {
        StringBuilder b = getBaz(getBaz());
        return b;
    }

    public StringBuilder assignWithSuperPrefixArgCall() {
        StringBuilder b = super.getBaz(super.getBaz());
        return b;
    }

    public StringBuilder assignPrefixArgCallEx1() {
        StringBuilder a = getBaz();
        StringBuilder b = getBazz(a);
        return b;
    }

    public StringBuilder assignWithSuperPrefixArgCallEx1() {
        StringBuilder a = super.getBaz();
        StringBuilder b = super.getBazz(a);
        return b;
    }

    public StringBuilder assignPrefixArgCallEx2() {
        StringBuilder b = getBazz(getBaz());
        return b;
    }

    public StringBuilder assignWithSuperPrefixArgCallEx2() {
        StringBuilder b = super.getBazz(super.getBaz());
        return b;
    }
}

class SuperBaz {

    private StringBuilder baz;

    public StringBuilder getBaz() {
        return baz;
    }

    public StringBuilder getBaz(final StringBuilder foo) {
        return baz;
    }

    public StringBuilder getBazz(final StringBuilder foo) {
        return baz.append(foo);
    }
}
