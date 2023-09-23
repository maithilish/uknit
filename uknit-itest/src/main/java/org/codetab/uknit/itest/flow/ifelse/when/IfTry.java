package org.codetab.uknit.itest.flow.ifelse.when;

import org.codetab.uknit.itest.flow.ifelse.when.Model.Duck;

class IfTry {

    public String ifTryFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
        } else {
            duck.swim("else canSwim");
            state = duck.fly("else canSwim");
        }
        try {
            duck.swim("try");
            state = duck.fly("try");
        } catch (IllegalStateException e) {
            duck.swim("catch");
            state = duck.fly("catch");
        } finally {
            duck.swim("finally");
            state = duck.fly("finally");
        }
        duck.swim("end");
        return state;
    }

    public String tryIfFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        String state = null;
        try {
            duck.swim("try");
            state = duck.fly("try");
        } catch (IllegalStateException e) {
            duck.swim("catch");
            state = duck.fly("catch");
        } finally {
            duck.swim("finally");
            state = duck.fly("finally");
        }
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
        } else {
            duck.swim("else canSwim");
            state = duck.fly("else canSwim");
        }
        duck.swim("end");
        return state;
    }

    // don't assign method invoke, should verify instead of when
    public String ifTryNoAssignFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
            duck.fly("if canSwim");
        } else {
            duck.swim("else canSwim");
            duck.fly("else canSwim");
        }
        try {
            duck.swim("try");
            duck.fly("try");
        } catch (IllegalStateException e) {
            duck.swim("catch");
            duck.fly("catch");
        } finally {
            duck.swim("finally");
            duck.fly("finally");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String tryIfNoAssignFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        try {
            duck.swim("try");
            duck.fly("try");
        } catch (IllegalStateException e) {
            duck.swim("catch");
            duck.fly("catch");
        } finally {
            duck.swim("finally");
            duck.fly("finally");
        }
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
}
