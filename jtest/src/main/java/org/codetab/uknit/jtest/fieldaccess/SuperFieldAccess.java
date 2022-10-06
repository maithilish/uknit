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
 * @author m
 *
 */
public class SuperFieldAccess extends T2 {

    int x = 3;

    Log log;

    void foo() {
        log.info("x " + x);
        log.info("super.x " + super.x);
        log.info("(T2)this).x " + ((T2) this).x);
        log.info("(T1)this).x " + ((T1) this).x);
    }

    interface Log {
        void info(String msg);
    }

}

interface I {
    int x = 0;
}

class T1 implements I {
    int x = 1;
}

class T2 extends T1 {
    int x = 2;
}
