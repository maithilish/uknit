package org.codetab.uknit.itest.internal;

class NullReturn extends Delegating {

    public String internalCall(final boolean flag) {
        if (flag) {
            return imc();
        }
        return null;
    }

    private String imc() {
        return "a";
    }

    public Connection superCall(final boolean flag) {
        if (flag) {
            return getDelegateInternal();
        }
        return null;
    }

    public Connection superCallWithSuper(final boolean flag) {
        if (flag) {
            return super.getDelegateInternal();
        }
        return null;
    }
}

class Delegating implements Connection {

    private Connection connection;

    Connection getDelegateInternal() {
        return connection;
    }
}

interface Connection {

}
