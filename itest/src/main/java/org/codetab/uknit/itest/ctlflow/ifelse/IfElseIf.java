package org.codetab.uknit.itest.ctlflow.ifelse;

import org.codetab.uknit.itest.model.Duck;

public class IfElseIf {

    public String ifElseIf(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
        } else if (done) {
            duck.swim("else if");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifElseIfElse(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
        } else if (done) {
            duck.swim("else if");
        } else {
            duck.swim("else");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifTwiceElseIf(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
        } else if (canDive) {
            duck.swim("else if once");
        } else if (done) {
            duck.swim("else if twice");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifTwiceElseIfElse(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
        } else if (canDive) {
            duck.swim("else if once");
        } else if (done) {
            duck.swim("else if twice");
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
            duck.swim("if");
        } else if (canDive) {
            duck.swim("else if once");
        }

        if (canSwim2) {
            duck.swim("2 if");
        } else if (canDive2) {
            duck.swim("2 else if once");
        }

        duck.swim("end");
        return duck.toString();
    }

    public String ifTwiceElseIfElseTwice(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done, final boolean canSwim2,
            final boolean canDive2, final boolean done2) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
        } else if (canDive) {
            duck.swim("else if once");
        } else if (done) {
            duck.swim("else if twice");
        } else {
            duck.swim("else");
        }

        if (canSwim2) {
            duck.swim("2 if");
        } else if (canDive2) {
            duck.swim("2 else if once");
        } else if (done2) {
            duck.swim("2 else if twice");
        } else {
            duck.swim("2 else");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifElseIfPlusIf(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
        } else if (canDive) {
            duck.swim("else if");
        }
        if (done) {
            duck.swim("plus if");
        } else {
            duck.swim("plus else");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifElseIfElsePlusIf(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
        } else if (canDive) {
            duck.swim("else if");
        } else {
            duck.swim("else");
        }
        if (done) {
            duck.swim("plus if");
        } else {
            duck.swim("plus else");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifElseIfPlusIf(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean canFlip, final boolean canFly,
            final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
        } else if (canDive) {
            duck.swim("else if");
            if (canFlip) {
                duck.swim("else if if");
            } else if (canFly) {
                duck.swim("else if else");
            }
        }
        if (done) {
            duck.swim("plus if");
        } else {
            duck.swim("plus else");
        }
        duck.swim("end");
        return duck.toString();
    }
}
