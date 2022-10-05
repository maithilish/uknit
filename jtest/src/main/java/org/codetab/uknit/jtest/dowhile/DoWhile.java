package org.codetab.uknit.jtest.dowhile;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Do statement.
 *
 * <pre>
 * DoStatement:
 *    <b>do</b> Statement <b>while</b> <b>(</b> Expression <b>)</b> <b>;</b>
 * </pre>
 *
 * While statement.
 *
 * <pre>
 * WhileStatement:
 *    <b>while</b> <b>(</b> Expression <b>)</b> Statement
 * </pre>
 *
 * The Expression must have type boolean.
 *
 * TODO H - add when stmt for exp in while()
 *
 * @author m
 *
 */
public class DoWhile {

    public AtomicInteger whileDo(final AtomicInteger counter) {
        while (counter.get() < 5) {
            counter.getAndIncrement();
        }
        return counter;
    }

    public AtomicInteger doWhile(final AtomicInteger counter) {
        do {
            counter.getAndIncrement();
        } while (counter.get() < 5);
        return counter;
    }

    public AtomicInteger whileDoBreak(final AtomicInteger counter) {
        while (counter.get() < 5) {
            if (counter.get() < 0) {
                break;
            }
            counter.getAndIncrement();
        }
        return counter;
    }

    public AtomicInteger doWhileBreak(final AtomicInteger counter) {
        do {
            if (counter.get() < 0) {
                break;
            }
            counter.getAndIncrement();
        } while (counter.get() < 5);
        return counter;
    }

}
