package org.codetab.uknit.itest.ctlflow.ifelse.when;

import org.codetab.uknit.itest.model.Duck;

public class IfElse {

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

    public String ifElseFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        String state;
        if (canSwim) {
            duck.swim("if");
            state = duck.fly("if");
        } else {
            duck.swim("else");
            state = duck.fly("else");
        }
        duck.swim("end");
        return state;
    }

    public String ifNestIfFoo(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if");
            state = duck.fly("if");
            if (done) {
                duck.swim("if if");
                state = duck.fly("if if");
            } else {
                duck.swim("if else");
                state = duck.fly("if else");
            }
        } else {
            duck.swim("else");
            state = duck.fly("else");
        }
        duck.swim("end");
        return state;
    }

    public String ifIfFoo(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if");
            state = duck.fly("if");
        } else {
            duck.swim("else");
            state = duck.fly("else");
        }
        if (done) {
            duck.swim("if if");
            state = duck.fly("if if");
        } else {
            duck.swim("if else");
            state = duck.fly("if else");
        }
        duck.swim("end");
        return state;
    }

    public String ifNestIfIfFoo(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if");
            state = duck.fly("if");
            if (canDive) {
                duck.swim("nest if");
                state = duck.fly("nest if");
            } else {
                duck.swim("nest else");
                state = duck.fly("nest else");
            }
            if (done) {
                duck.swim("nest if if");
                state = duck.fly("nest if if");
            } else {
                duck.swim("nest if else");
                state = duck.fly("nest if else");
            }
        } else {
            duck.swim("else");
            state = duck.fly("else");
        }
        duck.swim("end");
        return state;
    }

    // don't assign method invoke - verify instead of when
    public String ifNoAssignFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
            duck.fly("if");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifElseNoAssignFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
            duck.fly("if");
        } else {
            duck.swim("else");
            duck.fly("else");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifNestIfNoAssignFoo(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
            duck.fly("if");
            if (done) {
                duck.swim("if if");
                duck.fly("if if");
            } else {
                duck.swim("if else");
                duck.fly("if else");
            }
        } else {
            duck.swim("else");
            duck.fly("else");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifIfNoAssignFoo(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
            duck.fly("if");
        } else {
            duck.swim("else");
            duck.fly("else");
        }
        if (done) {
            duck.swim("if if");
            duck.fly("if if");
        } else {
            duck.swim("if else");
            duck.fly("if else");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifNestIfIfNoAssignFoo(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
            duck.fly("if");
            if (canDive) {
                duck.swim("nest if");
                duck.fly("nest if");
            } else {
                duck.swim("nest else");
                duck.fly("nest else");
            }
            if (done) {
                duck.swim("nest if if");
                duck.fly("nest if if");
            } else {
                duck.swim("nest if else");
                duck.fly("nest if else");
            }
        } else {
            duck.swim("else");
            duck.fly("else");
        }
        duck.swim("end");
        return duck.toString();
    }
}
