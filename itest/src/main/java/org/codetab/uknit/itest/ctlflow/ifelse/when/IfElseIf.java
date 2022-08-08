package org.codetab.uknit.itest.ctlflow.ifelse.when;

import org.codetab.uknit.itest.model.Duck;

public class IfElseIf {

    public String ifElseIf(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if");
            state = duck.fly("if");
        } else if (done) {
            duck.swim("else if");
            state = duck.fly("else if");
        }
        duck.swim("end");
        return state;
    }

    public String ifElseIfElse(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if");
            state = duck.fly("if");
        } else if (done) {
            duck.swim("else if");
            state = duck.fly("else if");
        } else {
            duck.swim("else");
            state = duck.fly("else");
        }
        duck.swim("end");
        return state;
    }

    public String ifTwiceElseIf(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if");
            state = duck.fly("if");
        } else if (canDive) {
            duck.swim("else if once");
            state = duck.fly("else if once");
        } else if (done) {
            duck.swim("else if twice");
            state = duck.fly("else if twice");
        }
        duck.swim("end");
        return state;
    }

    public String ifTwiceElseIfElse(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if");
            state = duck.fly("if");
        } else if (canDive) {
            duck.swim("else if once");
            state = duck.fly("else if once");
        } else if (done) {
            duck.swim("else if twice");
            state = duck.fly("else if twice");
        } else {
            duck.swim("else");
            state = duck.fly("else");
        }
        duck.swim("end");
        return state;
    }

    public String ifElseIfTwice(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean canSwim2,
            final boolean canDive2) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if");
            state = duck.fly("if");
        } else if (canDive) {
            duck.swim("else if once");
            state = duck.fly("else if once");
        }

        if (canSwim2) {
            duck.swim("2 if");
            state = duck.fly("2 if");
        } else if (canDive2) {
            duck.swim("2 else if once");
            state = duck.fly("2 else if once");
        }

        duck.swim("end");
        return state;
    }

    public String ifTwiceElseIfElseTwice(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done, final boolean canSwim2,
            final boolean canDive2, final boolean done2) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if");
            state = duck.fly("if");
        } else if (canDive) {
            duck.swim("else if once");
            state = duck.fly("else if once");
        } else if (done) {
            duck.swim("else if twice");
            state = duck.fly("else if twice");
        } else {
            duck.swim("else");
            state = duck.fly("else");
        }

        if (canSwim2) {
            duck.swim("2 if");
            state = duck.fly("2 if");
        } else if (canDive2) {
            duck.swim("2 else if once");
            state = duck.fly("2 else if once");
        } else if (done2) {
            duck.swim("2 else if twice");
            state = duck.fly("2 else if twice");
        } else {
            duck.swim("2 else");
            state = duck.fly("2 else");
        }
        duck.swim("end");
        return state;
    }

    public String ifElseIfPlusIf(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if");
            state = duck.fly("if");
        } else if (canDive) {
            duck.swim("else if");
            state = duck.fly("else if");
        }
        if (done) {
            duck.swim("plus if");
            state = duck.fly("plus if");
        } else {
            duck.swim("plus else");
            state = duck.fly("plus else");
        }
        duck.swim("end");
        return state;
    }

    public String ifElseIfElsePlusIf(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if");
            state = duck.fly("if");
        } else if (canDive) {
            duck.swim("else if");
            state = duck.fly("else if");
        } else {
            duck.swim("else");
            state = duck.fly("else");
        }
        if (done) {
            duck.swim("plus if");
            state = duck.fly("plus if");
        } else {
            duck.swim("plus else");
            state = duck.fly("plus else");
        }
        duck.swim("end");
        return state;
    }

    public String ifElseIfPlusIf(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean canFlip, final boolean canFly,
            final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if");
            state = duck.fly("if");
        } else if (canDive) {
            duck.swim("else if");
            state = duck.fly("else if");
            if (canFlip) {
                duck.swim("else if if");
                state = duck.fly("else if if");
            } else if (canFly) {
                duck.swim("else if else");
                state = duck.fly("else if else");
            }
        }
        if (done) {
            duck.swim("plus if");
            state = duck.fly("plus if");
        } else {
            duck.swim("plus else");
            state = duck.fly("plus else");
        }
        duck.swim("end");
        return state;
    }
}
