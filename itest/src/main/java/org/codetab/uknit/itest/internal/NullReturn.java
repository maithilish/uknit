package org.codetab.uknit.itest.internal;

class NullReturn extends Delegating {

    private final boolean flag = false;

    public Connection foo() {
        if (flag) {
            return getDelegateInternal();
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
