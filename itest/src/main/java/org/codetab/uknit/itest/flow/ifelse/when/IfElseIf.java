package org.codetab.uknit.itest.flow.ifelse.when;

import org.codetab.uknit.itest.flow.ifelse.when.Model.Duck;

class IfElseIf {

    public String ifElseIf(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
        } else if (done) {
            duck.swim("else if done");
            state = duck.fly("else if done");
            duck.dive(state);
        }
        duck.swim("end");
        return state;
    }

    public String ifElseIfElse(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
        } else if (done) {
            duck.swim("else if done");
            state = duck.fly("else if done");
            duck.dive(state);
        } else {
            duck.swim("else");
            state = duck.fly("else");
            duck.dive(state);
        }
        duck.swim("end");
        return state;
    }

    public String ifTwiceElseIf(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
        } else if (canDive) {
            duck.swim("else if canDive");
            state = duck.fly("else if canDive");
            duck.dive(state);
        } else if (done) {
            duck.swim("else if done");
            state = duck.fly("else if done");
            duck.dive(state);
        }
        duck.swim("end");
        return state;
    }

    public String ifTwiceElseIfElse(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
        } else if (canDive) {
            duck.swim("else if canDive");
            state = duck.fly("else if canDive");
            duck.dive(state);
        } else if (done) {
            duck.swim("else if done");
            state = duck.fly("else if done");
            duck.dive(state);
        } else {
            duck.swim("else");
            state = duck.fly("else");
            duck.dive(state);
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
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
        } else if (canDive) {
            duck.swim("else if canDive");
            state = duck.fly("else if canDive");
            duck.dive(state);
        }

        if (canSwim2) {
            duck.swim("if canSwim2");
            state = duck.fly("if canSwim2");
            duck.dive(state);
        } else if (canDive2) {
            duck.swim("else if canDive2");
            state = duck.fly("else if canDive2");
            duck.dive(state);
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
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
        } else if (canDive) {
            duck.swim("else if canDive");
            state = duck.fly("else if canDive");
            duck.dive(state);
        } else if (done) {
            duck.swim("else if done");
            state = duck.fly("else if done");
            duck.dive(state);
        } else {
            duck.swim("else");
            state = duck.fly("else");
            duck.dive(state);
        }

        if (canSwim2) {
            duck.swim("if canSwim2");
            state = duck.fly("if canSwim2");
            duck.dive(state);
        } else if (canDive2) {
            duck.swim("else if canDive2");
            state = duck.fly("else if canDive2");
            duck.dive(state);
        } else if (done2) {
            duck.swim("else if done2");
            state = duck.fly("else if done2");
            duck.dive(state);
        } else {
            duck.swim("else 2");
            state = duck.fly("else 2");
            duck.dive(state);
        }
        duck.swim("end");
        return state;
    }

    public String ifElseIfPlusIf(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
        } else if (canDive) {
            duck.swim("else if canDive");
            state = duck.fly("else if canDive");
            duck.dive(state);
        }
        if (done) {
            duck.swim("if done");
            state = duck.fly("if done");
            duck.dive(state);
        } else {
            duck.swim("else done");
            state = duck.fly("else done");
            duck.dive(state);
        }
        duck.swim("end");
        return state;
    }

    public String ifElseIfElsePlusIf(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
        } else if (canDive) {
            duck.swim("else if canDive");
            state = duck.fly("else if canDive");
            duck.dive(state);
        } else {
            duck.swim("else");
            state = duck.fly("else");
            duck.dive(state);
        }
        if (done) {
            duck.swim("if done");
            state = duck.fly("if done");
            duck.dive(state);
        } else {
            duck.swim("else done");
            state = duck.fly("else done");
            duck.dive(state);
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
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
        } else if (canDive) {
            duck.swim("else if canDive");
            state = duck.fly("else if canDive");
            duck.dive(state);
            if (canFlip) {
                duck.swim("else if canFlip nest");
                state = duck.fly("else if canFlip nest");
                duck.dive(state);
            } else if (canFly) {
                duck.swim("else if canFly nest");
                state = duck.fly("else if canFly nest");
                duck.dive(state);
            }
        }
        if (done) {
            duck.swim("if done");
            state = duck.fly("if done");
            duck.dive(state);
        } else {
            duck.swim("else done");
            state = duck.fly("else done");
            duck.dive(state);
        }
        duck.swim("end");
        return state;
    }
}
