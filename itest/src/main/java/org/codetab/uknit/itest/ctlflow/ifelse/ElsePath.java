package org.codetab.uknit.itest.ctlflow.ifelse;

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
        if (canSwim) {
            duck.swim("if");
        }
        duck.swim("end");
        return duck.toString();
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
        if (canSwim) {
            duck.swim("if canSwim");
        }
        if (done) {
            duck.swim("if done");
        }
        duck.swim("end");
        return duck.toString();
    }

    /**
     * Else path for bottom done if is required.
     * @param duck
     * @param canSwim
     * @return
     */
    public String tryPlusIfFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        try {
            duck.swim("try");
        } finally {
            duck.swim("finally");
        }
        if (canSwim) {
            duck.swim("if");
        }
        duck.swim("end");
        return duck.toString();
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
        if (canSwim) {
            duck.swim("if canSwim");
            if (done) {
                duck.swim("if done");
            }
        }
        if (eof) {
            duck.swim("if eof");
        }
        duck.swim("end");
        return duck.toString();
    }
}
