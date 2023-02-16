package org.codetab.uknit.itest.flow.nosplit;

import org.codetab.uknit.itest.flow.nosplit.Model.Duck;

class IfElseIf {

    public String ifElseIf(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
        } else if (done) {
            duck.swim("else if canSwim");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifElseIfElse(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
        } else if (done) {
            duck.swim("else if done");
        } else {
            duck.swim("else canSwim done");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifTwiceElseIf(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
        } else if (canDive) {
            duck.swim("else if  canDive");
        } else if (done) {
            duck.swim("else if done");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifTwiceElseIfElse(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
        } else if (canDive) {
            duck.swim("else if canDive");
        } else if (done) {
            duck.swim("else if done");
        } else {
            duck.swim("else");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifElseIfTwice(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean canSwim2,
            final boolean canDive2) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
        } else if (canDive) {
            duck.swim("else if canDive");
        }

        if (canSwim2) {
            duck.swim("if canSwim2");
        } else if (canDive2) {
            duck.swim("else if canSwim2");
        }

        duck.swim("end");
        return duck.toString();
    }

    public String ifTwiceElseIfElseTwice(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done, final boolean canSwim2,
            final boolean canDive2, final boolean done2) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
        } else if (canDive) {
            duck.swim("else if canDive");
        } else if (done) {
            duck.swim("else if done");
        } else {
            duck.swim("else");
        }

        if (canSwim2) {
            duck.swim("if canSwim2");
        } else if (canDive2) {
            duck.swim("else if canDive2");
        } else if (done2) {
            duck.swim("else if done2");
        } else {
            duck.swim("else 2");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifElseIfPlusIf(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
        } else if (canDive) {
            duck.swim("else if canDive");
        }
        if (done) {
            duck.swim("plus if done");
        } else {
            duck.swim("plus else done");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifElseIfElsePlusIf(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
        } else if (canDive) {
            duck.swim("else if canDive");
        } else {
            duck.swim("else");
        }
        if (done) {
            duck.swim("plus if done");
        } else {
            duck.swim("plus else done");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifElseIfPlusIf(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean canFlip, final boolean canFly,
            final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
        } else if (canDive) {
            duck.swim("else if canDive");
            if (canFlip) {
                duck.swim("if canFlip nest");
            } else if (canFly) {
                duck.swim("else if canFly nest");
            }
        }
        if (done) {
            duck.swim("plus if done");
        } else {
            duck.swim("plus else done");
        }
        duck.swim("end");
        return duck.toString();
    }
}
