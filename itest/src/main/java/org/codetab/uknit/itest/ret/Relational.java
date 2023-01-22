package org.codetab.uknit.itest.ret;

/**
 * Relational operators in return statement
 * @author Maithilish
 *
 */
class Relational {

    public boolean checkRelational() {
        int a = 1;
        int b = 1;
        return a == b;
    }

    public boolean checkRelationalGt() {
        int a = 1;
        int b = 1;
        return a > b;
    }

    public boolean checkConditional() {

        int a = 1;
        int b = 1;
        int x = 1;
        int y = 1;

        return (a == b) && (x == y);
    }

}
