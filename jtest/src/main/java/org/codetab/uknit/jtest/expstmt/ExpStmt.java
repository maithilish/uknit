package org.codetab.uknit.jtest.expstmt;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Expression statement.
 * <p>
 * Used to convert an expression (<code>Expression</code>) into a statement
 * (<code>Statement</code>) by wrapping it.
 * </p>
 *
 * <pre>
 * ExpressionStatement:
 *    StatementExpression <b>;</b>
 * </pre>
 *
 * @author m
 *
 */
public class ExpStmt {

    public void foo(final AtomicInteger counter) {
        counter.decrementAndGet();
    }

}
