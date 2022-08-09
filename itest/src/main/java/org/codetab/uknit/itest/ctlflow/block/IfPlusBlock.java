package org.codetab.uknit.itest.ctlflow.block;

import java.util.List;

import org.codetab.uknit.itest.model.Duck;

/**
 * Test behaviour of tailing blocks other than MethodDeclaration, IfStatement,
 * TryStatement or CatchClause.
 * @author m
 *
 */
public class IfPlusBlock {

    public String ifPlusBlockFoo(final Duck duck, final boolean canSwim,
            final List<String> names) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if swim");
        }

        // for block
        for (String name : names) {
            duck.swim(name);
        }

        duck.swim("end");
        return duck.toString();
    }

    public String ifElsePlusBlockFoo(final Duck duck, final boolean canSwim,
            final List<String> names) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
        } else {
            duck.swim("else");
        }

        // for block
        for (String name : names) {
            duck.swim(name);
        }

        duck.swim("end");
        return duck.toString();
    }

    public String ifNestIfPlusBlockFoo(final Duck duck, final boolean canSwim,
            final boolean done, final List<String> names) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
            if (done) {
                duck.swim("if if");
            } else {
                duck.swim("if else");
            }
        } else {
            duck.swim("else");
        }

        // for block
        for (String name : names) {
            duck.swim(name);
        }

        duck.swim("end");
        return duck.toString();
    }

    public String ifIfPlusBlockFoo(final Duck duck, final boolean canSwim,
            final boolean canDive, final List<String> names) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if swim");
        }
        if (canDive) {
            duck.swim("if dive");
        }
        // for block
        for (String name : names) {
            duck.swim(name);
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifElseTwicePlusBlockFoo(final Duck duck,
            final boolean canSwim, final boolean done,
            final List<String> names) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
        } else {
            duck.swim("else");
        }
        if (done) {
            duck.swim("if if");
        } else {
            duck.swim("if else");
        }

        // for block
        for (String name : names) {
            duck.swim(name);
        }

        duck.swim("end");
        return duck.toString();
    }

    public String ifNestIfIfPlusBlockFoo(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done,
            final List<String> names) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
            if (canDive) {
                duck.swim("nest if");
            } else {
                duck.swim("nest else");
            }
            if (done) {
                duck.swim("nest if if");
            } else {
                duck.swim("nest if else");
            }
        } else {
            duck.swim("else");
        }

        // for block
        for (String name : names) {
            duck.swim(name);
        }

        duck.swim("end");
        return duck.toString();
    }

    public String ifNestIfNestBlockPlusBlockFoo(final Duck duck,
            final boolean canSwim, final boolean done, final List<String> lanes,
            final List<String> names) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
            if (done) {
                duck.swim("if if");
            } else {
                duck.swim("if else");
            }
            // nest for block
            for (String lane : lanes) {
                duck.swim(lane);
            }
        } else {
            duck.swim("else");
        }

        // for block
        for (String name : names) {
            duck.swim(name);
        }

        duck.swim("end");
        return duck.toString();
    }

    public String ifNestIfIfNestBlockPlusBlockFoo(final Duck duck,
            final boolean canSwim, final boolean canDive, final boolean done,
            final List<String> lanes, final List<String> names) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
            if (canDive) {
                duck.swim("nest if");
            } else {
                duck.swim("nest else");
            }
            if (done) {
                duck.swim("nest if if");
            } else {
                duck.swim("nest if else");
            }
            // nest for block
            for (String lane : lanes) {
                duck.swim(lane);
            }
        } else {
            duck.swim("else");
        }

        // for block
        for (String name : names) {
            duck.swim(name);
        }

        duck.swim("end");
        return duck.toString();
    }
}
