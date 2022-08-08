package org.codetab.uknit.itest.ctlflow.ifelse.when;

import org.codetab.uknit.itest.model.Duck;

public class ElsePath {

    /**
     * Else path required for top if
     * @param duck
     * @param canSwim
     * @return
     */
    public String ifFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if");
            state = duck.fly("if");
        }
        duck.swim("end");
        return state;
    }

    /**
     * Two tests are enough, but creates three. This is because separate empty
     * else is created for each if statement.
     * @param duck
     * @param canSwim
     * @param done
     * @return
     */
    public String ifPlusIfFoo(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
        }
        if (done) {
            duck.swim("if done");
            state = duck.fly("if done");
        }
        duck.swim("end");
        return state;
    }

    /**
     * Else path for bottom done if is required.
     * @param duck
     * @param canSwim
     * @return
     */
    public String tryPlusIfFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        String state = null;
        try {
            duck.swim("try");
            state = duck.fly("try");
        } finally {
            duck.swim("finally");
            state = duck.fly("finally");
        }
        if (canSwim) {
            duck.swim("if");
            state = duck.fly("if");
        }
        duck.swim("end");
        return state;
    }

    /**
     * Else required for canSwim and done branches, not for eof
     * @param duck
     * @param canSwim
     * @param done
     * @param eof
     * @return
     */
    public String ifIfPlusIfFoo(final Duck duck, final boolean canSwim,
            final boolean done, final boolean eof) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            if (done) {
                duck.swim("if done");
                state = duck.fly("if done");
            }
        }
        if (eof) {
            duck.swim("if eof");
            state = duck.fly("if eof");
        }
        duck.swim("end");
        return state;
    }
}
