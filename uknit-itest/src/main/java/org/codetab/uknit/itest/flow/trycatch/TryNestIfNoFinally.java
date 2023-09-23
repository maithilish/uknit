package org.codetab.uknit.itest.flow.trycatch;

import org.codetab.uknit.itest.flow.trycatch.Model.Duck;

class TryNestIfNoFinally {

    public String tryNestIfNoFinallyFoo(final Duck duck,
            final boolean canSwim) {
        duck.swim("start");
        try {
            duck.swim("try");
            if (canSwim) {
                duck.swim("try if");
            } else {
                duck.swim("try else");
            }
        } catch (IllegalStateException e) {
            duck.swim("catch");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String tryCatchNestIfNoFinallyFoo(final Duck duck,
            final boolean canSwim) {
        duck.swim("start");
        try {
            duck.swim("try");
        } catch (IllegalStateException e) {
            duck.swim("catch");
            if (canSwim) {
                duck.swim("catch if");
            } else {
                duck.swim("catch else");
            }
        }
        duck.swim("end");
        return duck.toString();
    }

    public String tryNestIfPlusIfNoFinallyFoo(final Duck duck,
            final boolean canSwim, final boolean done) {
        duck.swim("start");
        try {
            duck.swim("try");
            if (canSwim) {
                duck.swim("try if");
            } else {
                duck.swim("try else");
            }
        } catch (IllegalStateException e) {
            duck.swim("catch");
        }
        if (done) {
            duck.swim("if");
        } else {
            duck.swim("else");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String tryCatchNestIfPlusIfNoFinallyFoo(final Duck duck,
            final boolean canSwim, final boolean done) {
        duck.swim("start");
        try {
            duck.swim("try");
        } catch (IllegalStateException e) {
            duck.swim("catch");
            if (canSwim) {
                duck.swim("catch if");
            } else {
                duck.swim("catch else");
            }
        }
        if (done) {
            duck.swim("if");
        } else {
            duck.swim("else");
        }
        duck.swim("end");
        return duck.toString();
    }

}
