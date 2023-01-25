package org.codetab.uknit.itest.flow.trycatch;

import org.codetab.uknit.itest.flow.trycatch.Model.Duck;

class TryNestIfNoCatch {

    public String tryNestIfNoCatchFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        try {
            duck.swim("try");
            if (canSwim) {
                duck.swim("try if");
            } else {
                duck.swim("try else");
            }
        } finally {
            duck.swim("finally");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String tryFinallyNestIfNoCatchFoo(final Duck duck,
            final boolean canSwim) {
        duck.swim("start");
        try {
            duck.swim("try");
        } finally {
            duck.swim("finally");
            if (canSwim) {
                duck.swim("finally if");
            } else {
                duck.swim("finally else");
            }
        }
        duck.swim("end");
        return duck.toString();
    }

    public String tryNestIfPlusIfNoCatchFoo(final Duck duck,
            final boolean canSwim, final boolean done) {
        duck.swim("start");
        try {
            duck.swim("try");
            if (canSwim) {
                duck.swim("try if");
            } else {
                duck.swim("try else");
            }
        } finally {
            duck.swim("finally");
        }
        if (done) {
            duck.swim("if");
        } else {
            duck.swim("else");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String tryFinallyNestIfPlusIfNoCatchFoo(final Duck duck,
            final boolean canSwim, final boolean done) {
        duck.swim("start");
        try {
            duck.swim("try");
        } finally {
            duck.swim("finally");
            if (canSwim) {
                duck.swim("finally if");
            } else {
                duck.swim("finally else");
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
