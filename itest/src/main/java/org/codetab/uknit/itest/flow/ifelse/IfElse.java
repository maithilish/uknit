package org.codetab.uknit.itest.flow.ifelse;

import org.codetab.uknit.itest.flow.ifelse.Model.Duck;

class IfElse {

    public String ifFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifElseFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
        } else {
            duck.swim("else");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifNestIfFoo(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
            if (done) {
                duck.swim("if if");
            } else {
                duck.swim("if else");
            }
        } else {
            duck.swim("else");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifIfFoo(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
        } else {
            duck.swim("else");
        }
        if (done) {
            duck.swim("if if");
        } else {
            duck.swim("if else");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifNestIfIfFoo(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
            if (canDive) {
                duck.swim("nest if");
            } else {
                duck.swim("nest else");
            }
            if (done) {
                duck.swim("nest if if");
            } else {
                duck.swim("nest if else");
            }
        } else {
            duck.swim("else");
        }
        duck.swim("end");
        return duck.toString();
    }
}
