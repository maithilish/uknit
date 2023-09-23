package org.codetab.uknit.itest.flow.ifelse;

import org.codetab.uknit.itest.flow.ifelse.Model.Duck;

class IfTry {

    public String ifTryFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
        } else {
            duck.swim("else canSwim");
        }
        try {
            duck.swim("if try");
        } catch (IllegalStateException e) {
            duck.swim("if catch");
        } finally {
            duck.swim("if finally");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String tryIfFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        try {
            duck.swim("if try");
        } catch (IllegalStateException e) {
            duck.swim("if catch");
        } finally {
            duck.swim("if finally");
        }
        if (canSwim) {
            duck.swim("if canSwim");
        } else {
            duck.swim("else canSwim");
        }
        duck.swim("end");
        return duck.toString();
    }
}
