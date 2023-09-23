package org.codetab.uknit.itest.enums;

class Model {

    static class Account {

        enum Type {
            SAVINGS, OVERDRAFT
        };

        private Type type;

        Type getType() {
            return type;
        }
    }

}
