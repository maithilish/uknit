package org.codetab.uknit.jtest.fieldaccess;

/**
 * Field access expression.
 *
 * <pre>
 * FieldAccess:
 *      Expression <b>.</b> Identifier
 * </pre>
 *
 *
 * TODO N - test is not proper.
 *
 * Also, multiple source error is thrown if F2 is renamed as T2 which already
 * exists in SuperFieldAccess when package is run as test.
 *
 * @author m
 *
 */
public class FieldAccess {

    int x = 3;

    Log log;
    F2 f2;

    void foo() {
        log.info("x " + f2.x);
    }

    interface Log {
        void info(String msg);
    }

    class F2 {
        int x = 2;
    }
}
