package org.codetab.uknit.itest.flow.trycatch;

import org.codetab.uknit.itest.flow.trycatch.Model.Duck;

class TryNestIf {

    public String ifNestTryFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if");
            try {
                duck.swim("if try");
            } catch (IllegalStateException e) {
                duck.swim("if catch");
            } finally {
                duck.swim("if finally");
            }
        } else {
            duck.swim("else");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String tryNestIfFoo(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        try {
            duck.swim("try");
            if (canSwim) {
                duck.swim("try if");
                if (done) {
                    duck.swim("try if if");
                } else {
                    duck.swim("try if else");
                }
            } else {
                duck.swim("try else");
            }
        } catch (IllegalStateException e) {
            duck.swim("catch");
        } finally {
            duck.swim("finally");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String tryCatchNestIfFoo(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        try {
            duck.swim("try");
        } catch (IllegalStateException e) {
            duck.swim("catch");
            if (canSwim) {
                duck.swim("catch if");
                if (done) {
                    duck.swim("catch if if");
                } else {
                    duck.swim("catch if else");
                }
            } else {
                duck.swim("catch else");
            }
        } finally {
            duck.swim("finally");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String tryFinallyNestIfFoo(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        try {
            duck.swim("try");
        } catch (IllegalStateException e) {
            duck.swim("catch");
        } finally {
            duck.swim("finally");
            if (canSwim) {
                duck.swim("finally if");
                if (done) {
                    duck.swim("finally if if");
                } else {
                    duck.swim("finally if else");
                }
            } else {
                duck.swim("finally else");
            }
        }
        duck.swim("end");
        return duck.toString();
    }

}
