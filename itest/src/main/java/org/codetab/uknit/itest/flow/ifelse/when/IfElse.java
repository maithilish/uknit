package org.codetab.uknit.itest.flow.ifelse.when;

import org.codetab.uknit.itest.flow.ifelse.when.Model.Duck;

class IfElse {

    public String ifFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        String state = null;
        duck.dive(state);
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
        }
        duck.swim("end");
        return state;
    }

    public String ifElseFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        String state = null;
        duck.dive(state);
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
        } else {
            duck.swim("else canSwim");
            state = duck.fly("else canSwim");
            duck.dive(state);
        }
        duck.swim("end");
        return state;
    }

    public String ifNestIfFoo(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
            if (done) {
                duck.swim("if done nest");
                state = duck.fly("if done nest");
                duck.dive(state);
            } else {
                duck.swim("else done nest");
                state = duck.fly("else done nest");
                duck.dive(state);
            }
        } else {
            duck.swim("else canSwim");
            state = duck.fly("else canSwim");
            duck.dive(state);
        }
        duck.swim("end");
        return state;
    }

    public String ifIfFoo(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
        } else {
            duck.swim("else canSwim");
            state = duck.fly("else canSwim");
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

    public String ifNestIfIfFoo(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
            if (canDive) {
                duck.swim("if canDive nest");
                state = duck.fly("if canDive nest");
                duck.dive(state);
            } else {
                duck.swim("else canDive nest");
                state = duck.fly("else canDive nest");
                duck.dive(state);
            }
            if (done) {
                duck.swim("if done nest");
                state = duck.fly("if done nest");
                duck.dive(state);
            } else {
                duck.swim("else done nest");
                state = duck.fly("else done nest");
                duck.dive(state);
            }
        } else {
            duck.swim("else canSwim");
            state = duck.fly("else canSwim");
            duck.dive(state);
        }
        duck.swim("end");
        return state;
    }

    // don't assign method invoke - verify instead of when
    public String ifNoAssignFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
            duck.fly("if canSwim");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifElseNoAssignFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
            duck.fly("if canSwim");
        } else {
            duck.swim("else canSwim");
            duck.fly("else canSwim");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifNestIfNoAssignFoo(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
            duck.fly("if canSwim");
            if (done) {
                duck.swim("if done nest");
                duck.fly("if done nest");
            } else {
                duck.swim("else done nest");
                duck.fly("else done nest");
            }
        } else {
            duck.swim("else canSwim");
            duck.fly("else canSwim");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifIfNoAssignFoo(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
            duck.fly("if canSwim");
        } else {
            duck.swim("else canSwim");
            duck.fly("else canSwim");
        }
        if (done) {
            duck.swim("if done");
            duck.fly("if done");
        } else {
            duck.swim("else done");
            duck.fly("else done");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifNestIfIfNoAssignFoo(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
            duck.fly("if canSwim");
            if (canDive) {
                duck.swim("if canDive nest");
                duck.fly("if canDive nest");
            } else {
                duck.swim("else canDive nest");
                duck.fly("else canDive nest");
            }
            if (done) {
                duck.swim("if done nest");
                duck.fly("if done nest");
            } else {
                duck.swim("else done nest");
                duck.fly("else done nest");
            }
        } else {
            duck.swim("else canSwim");
            duck.fly("else canSwim");
        }
        duck.swim("end");
        return duck.toString();
    }
}
