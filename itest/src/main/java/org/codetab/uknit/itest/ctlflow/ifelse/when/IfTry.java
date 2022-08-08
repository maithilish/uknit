package org.codetab.uknit.itest.ctlflow.ifelse.when;

import org.codetab.uknit.itest.model.Duck;

public class IfTry {

    public String ifTryFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if");
            state = duck.fly("if");
        } else {
            duck.swim("else");
            state = duck.fly("else");
        }
        try {
            duck.swim("if try");
            state = duck.fly("if try");
        } catch (IllegalStateException e) {
            duck.swim("if catch");
            state = duck.fly("if catch");
        } finally {
            duck.swim("if finally");
            state = duck.fly("if finally");
        }
        duck.swim("end");
        return state;
    }

    public String tryIfFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        String state = null;
        try {
            duck.swim("if try");
            state = duck.fly("if try");
        } catch (IllegalStateException e) {
            duck.swim("if catch");
            state = duck.fly("if catch");
        } finally {
            duck.swim("if finally");
            state = duck.fly("if finally");
        }
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

    // don't assign method invoke, should verify instead of when
    public String ifTryNoAssignFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
            duck.fly("if");
        } else {
            duck.swim("else");
            duck.fly("else");
        }
        try {
            duck.swim("if try");
            duck.fly("if try");
        } catch (IllegalStateException e) {
            duck.swim("if catch");
            duck.fly("if catch");
        } finally {
            duck.swim("if finally");
            duck.fly("if finally");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String tryIfNoAssignFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        try {
            duck.swim("if try");
            duck.fly("if try");
        } catch (IllegalStateException e) {
            duck.swim("if catch");
            duck.fly("if catch");
        } finally {
            duck.swim("if finally");
            duck.fly("if finally");
        }
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
}
