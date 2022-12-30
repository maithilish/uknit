package org.codetab.uknit.itest.enums;

import org.codetab.uknit.itest.enums.Model.Account;
import org.codetab.uknit.itest.enums.Model.Account.Type;

public class Enums {

    public Type qualifiedName(final Account a) {
        return Type.SAVINGS;
    }

    public Type assignment(final Account a) {
        Type t = Type.OVERDRAFT;
        return t;
    }

    public Type mi(final Account a) {
        return a.getType();
    }

    public Type assignmentMi(final Account a) {
        Type t = a.getType();
        return t;
    }

    public Type parameter(final Type type) {
        return type;
    }
}
