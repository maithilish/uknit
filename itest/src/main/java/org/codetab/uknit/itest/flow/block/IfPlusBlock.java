package org.codetab.uknit.itest.flow.block;

import java.util.List;

import org.codetab.uknit.itest.flow.block.Model.Duck;

/**
 * Test behaviour of tailing blocks other than MethodDeclaration, IfStatement,
 * TryStatement or CatchClause.
 * @author m
 *
 */
class IfPlusBlock {

    public String ifPlusBlockFoo(final Duck duck, final boolean canSwim,
            final List<String> names) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
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
            duck.swim("if canSwim");
        } else {
            duck.swim("else canSwim");
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
            duck.swim("if canSwim");
            if (done) {
                duck.swim("if done nest");
            } else {
                duck.swim("else done nest");
            }
        } else {
            duck.swim("else canSwim");
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
            duck.swim("if canSwim");
        }
        if (canDive) {
            duck.swim("if canDive");
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
            duck.swim("if canSwim");
        } else {
            duck.swim("else canSwim");
        }
        if (done) {
            duck.swim("if done");
        } else {
            duck.swim("else done");
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
            duck.swim("if canSwim");
            if (canDive) {
                duck.swim("if canDive nest");
            } else {
                duck.swim("else canDive nest");
            }
            if (done) {
                duck.swim("if done");
            } else {
                duck.swim("else done");
            }
        } else {
            duck.swim("else canSwim");
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
            duck.swim("if canSwim");
            if (done) {
                duck.swim("if done nest");
            } else {
                duck.swim("else done nest");
            }
            // nest for block
            for (String lane : lanes) {
                duck.swim(lane);
            }
        } else {
            duck.swim("else canSwim");
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
            duck.swim("if canSwim");
            if (canDive) {
                duck.swim("if canDive");
            } else {
                duck.swim("else canDive");
            }
            if (done) {
                duck.swim("if done");
            } else {
                duck.swim("else done");
            }
            // nest for block
            for (String lane : lanes) {
                duck.swim(lane);
            }
        } else {
            duck.swim("else canSwim");
        }

        // for block
        for (String name : names) {
            duck.swim(name);
        }

        duck.swim("end");
        return duck.toString();
    }
}
