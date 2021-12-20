package org.codetab.uknit.itest.model;

public class Account {

    public enum Type {
        SAVINGS, OVERDRAFT
    };

    private Type type;

    public Type getType() {
        return type;
    }
}
